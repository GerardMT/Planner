package gmt.planner.planner

import gmt.planner.encoder.{Encoder, Encoding}
import gmt.planner.language._
import gmt.planner.planner.ClassicPlanner._
import gmt.planner.solver.value.Value

import scala.collection.immutable

object ClassicPlanner {

    trait VariableGenerator {
        def getVariables: immutable.Seq[Variable]
    }

    abstract class State(val number: Int) extends VariableGenerator

    abstract class Action[S <: State, A](val sT: S, val sTPlus: S) extends VariableGenerator {

        lazy val name: String = "S" + sT.number + "_S" + sTPlus.number  + "_A_"  + postName

        protected def postName: String

        val variable = Variable(name, Type.Boolean)

        def encode(): ActionEncoding

        def decode(assignments: immutable.Map[String, Value]): immutable.Seq[A]

        def getVariables: immutable.Seq[Variable] = List(variable)
    }

    case class ActionEncoding(pre: Term, eff: Term, terms: immutable.Seq[Term])

    case class TimeStep[S <: State, P](sT: S, sTPlus: S, actions: immutable.Seq[Action[S, P]])
}

abstract class ClassicPlanner[S <: State, I, A] extends Encoder[A]{

    private var _timeSteps: Option[immutable.Seq[TimeStep[S, A]]] = None

    def createState(number: Int): S

    def createActions(sT: S, sTPlus: S): immutable.Seq[Action[S, A]]

    def encodeInitialState(state: S): immutable.Seq[Term]

    def encodeTimeStep(timeStep: TimeStep[S, A]): immutable.Seq[Term]

    def goal(state: S): immutable.Seq[Term]

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

        for (timeStep <- timeSteps) {
            encoding.add(Operations.eoWithQuatradicAmmo(timeStep.actions.map(f => f.variable)): _*)
            encoding.add(encodeTimeStep(timeStep): _*)

            for (action <- timeStep.actions) {
                val ActionEncoding(pre, eff, terms) = action.encode()

                encoding.add(terms: _*)

                encoding.add(ClauseDeclaration(eff == action.variable))
                encoding.add(ClauseDeclaration(action.variable -> pre))
            }
        }

        encoding.add(goal(timeSteps.last.sTPlus): _*)

        _timeSteps = Some(timeSteps)
        encoding
    }

    override def decode(assignments: Map[String, Value]): immutable.Seq[A] = {
        val timeSteps = _timeSteps match {
            case Some(t) =>
                t
            case None =>
                throw new IllegalStateException()
        }

        timeSteps.flatMap(t => t.actions.flatMap(a => a.decode(assignments)))
    }
}