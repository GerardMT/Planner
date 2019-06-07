package gmt.planner.planner

import gmt.planner.encoder.{Encoder, Encoding}
import gmt.planner.fixedPlanner.FixedPlannerResult
import gmt.planner.language._
import gmt.planner.planner.ClassicPlanner._
import gmt.planner.solver.value.{Value, ValueBoolean}

import scala.collection.immutable

object ClassicPlanner {

    trait VariableGenerator {
        def getVariables: immutable.Seq[Variable]
    }

    abstract class State(val number: Int) extends VariableGenerator {

        def decode(assignments: Map[String, Value], updatesCallback:ClassicPlannerUpdatesCallback): Unit = {}
    }

    abstract class Action[S <: State, A](val sT: S, val sTPlus: S) extends VariableGenerator {

        lazy val name: String = "S" + sT.number + "_S" + sTPlus.number  + "_A_"  + postName

        protected def postName: String

        val variable = Variable(name, Type.Boolean)

        def encode(): ActionEncoding = {

            val pre = Operations.multipleTermsApply(preconditions(), And.FUNCTION_MULTIPLE)
            val eff = Operations.multipleTermsApply(effects(), And.FUNCTION_MULTIPLE)

            ActionEncoding(pre, eff, terms())
        }

        protected def terms(): immutable.Seq[Term] = Nil

        protected def preconditions(): immutable.Seq[Term]

        protected def effects(): immutable.Seq[Term]

        def decode(assignments: immutable.Map[String, Value], updatesCallback:ClassicPlannerUpdatesCallback): immutable.Seq[A]

        def getVariables: immutable.Seq[Variable] = List(variable)
    }

    case class ActionEncoding(pre: Term, eff: Term, terms: immutable.Seq[Term])

    case class TimeStep[S <: State, P](sT: S, sTPlus: S, actions: immutable.Seq[Action[S, P]])

    class ClassicPlannerUpdatesCallback {
        def plannerUpdate: Option[FixedPlannerResult.FixedPlannerResult => _] = None
        def stateDecoded: Option[(_ <: State, Map[String, Value]) => _] = None
    }
}

abstract class ClassicPlanner[S <: State, I, A](val updatesCallback: ClassicPlannerUpdatesCallback) extends Encoder[A]{

    private var _timeSteps: Option[immutable.Seq[TimeStep[S, A]]] = None

    private var _states: Option[immutable.Seq[State]] = None

    def createState(number: Int): S

    def createActions(sT: S, sTPlus: S): immutable.Seq[Action[S, A]]

    def encodeInitialState(state: S): immutable.Seq[Term]

    def encodeOtherStates(state: S): immutable.Seq[Term] = Nil

    def encodeAllStates(state: S): immutable.Seq[Term] = Nil

    def encodeTimeStep(timeStep: TimeStep[S, A]): immutable.Seq[Term] = Nil

    def encodeGoal(state: S): immutable.Seq[Term]

    override def encode(nTimeSteps: Int): Encoding = {
        val encoding = new Encoding()

        val states = (0 to nTimeSteps).map(f => createState(f)).toList
        val timeSteps = states.zip(states.tail).map(s => TimeStep(s._1, s._2, createActions(s._1, s._2)))

        for (v <- states.flatMap(f => f.getVariables)) {
            encoding.add(VariableDeclaration(v))
        }

        for (v <- timeSteps.flatMap(t => t.actions.flatMap(a => a.getVariables))) {
            encoding.add(VariableDeclaration(v))
        }

        encoding.add(encodeInitialState(timeSteps.head.sT): _*)
        for (s <- states.tail) {
            encoding.add(encodeOtherStates(s): _*)
        }
        for (s <- states) {
            encoding.add(encodeAllStates(s): _*)
        }

        for (timeStep <- timeSteps) {
            timeStep.actions match {
                case immutable.Seq(_, _, _*) =>
                    encoding.add(Operations.eoWithQuatradicAmo(timeStep.actions.map(f => f.variable)): _*)
                case immutable.Seq(a) =>
                    encoding.add(ClauseDeclaration(a.variable))
            }

            encoding.add(encodeTimeStep(timeStep): _*)

            for (action <- timeStep.actions) {
                val ActionEncoding(pre, eff, terms) = action.encode()

                encoding.add(terms: _*)

                encoding.add(ClauseDeclaration(eff == action.variable))
                encoding.add(ClauseDeclaration(action.variable ==> pre))
            }
        }

        encoding.add(encodeGoal(timeSteps.last.sTPlus): _*)

        _timeSteps = Some(timeSteps)
        _states = Some(states)
        encoding
    }

    override def decode(assignments: Map[String, Value]): immutable.Seq[A] = {
        val timeSteps = _timeSteps match {
            case Some(t) => t
            case None => throw new IllegalStateException()
        }

        val states = _states match {
            case Some(t) => t
            case None => throw new IllegalStateException()
        }

        states.foreach(f => f.decode(assignments, updatesCallback))
        timeSteps.flatMap(t => t.actions.find(f => assignments(f.variable.name).asInstanceOf[ValueBoolean].v).get.decode(assignments, updatesCallback))
    }
}
