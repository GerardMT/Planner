package gmt.planner.planner

import gmt.planner.encoder.Encoder
import gmt.planner.fixedPlanner.FixedPlanner
import gmt.planner.fixedPlanner.FixedPlannerResult.{FixedPlannerResult, NoneFixedPlannerResult, SomeFixedPlannerResult}
import gmt.planner.planner.Planner.UpdateListener
import gmt.planner.planner.PlannerResult.{NonePlannerResult, PlannerResult, SomePlannerResult}
import gmt.planner.solver.{Result, Solver}
import gmt.planner.translator.Translator

import scala.collection.mutable.ListBuffer

object Planner {

    trait UpdateListener {

        def updated(fixedPlannerResult: FixedPlannerResult)
    }
}

class Planner[A](val plannerOptions: PlannerOptions, encoder: Encoder[A], translator: Translator, solver: Solver) {

    var updateListener: Option[UpdateListener] = None

    def solve(): PlannerResult = {
        val lowerBound = plannerOptions.lowerBound match {
            case Some(n) =>
                n
            case None =>
                encoder.lowerBound()
        }

        val upperBound = plannerOptions.upperBound match {
            case Some(n) =>
                n
            case None =>
                encoder.upperBound()
        }

        val fixedPlanner = new FixedPlanner(encoder, translator, solver)

        val startTime = System.currentTimeMillis()

        var timeStep = lowerBound
        val results = ListBuffer.empty[FixedPlannerResult]

        var loop = true

        while (loop && timeStep <= upperBound) {
            val result = fixedPlanner.solve(timeStep)
            results.append(result)

            loop = result.result == Result.Unsatisfactible

            updateListener match {
                case Some(c) => c.updated(result)
                case None =>
            }

            timeStep += 1
        }

        val endTime = System.currentTimeMillis()

        val time = endTime - startTime
        val resultsList = results.toList

        results.last match {
            case some @ SomeFixedPlannerResult(result, _, _, _) =>
                SomePlannerResult(result, time, resultsList, some)
            case NoneFixedPlannerResult(result, _, _) =>
                NonePlannerResult(result, time, resultsList)
        }
    }
}