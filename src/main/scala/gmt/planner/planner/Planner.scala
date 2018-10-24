package gmt.planner.planner

import gmt.planner.encoder.Encoder
import gmt.planner.fixedPlanner.FixedPlanner
import gmt.planner.solver.{Result, Solver}
import gmt.planner.translator.Translator

class Planner[A, B](val plannerOptions: PlannerOptions) {

    def solve(encoder: Encoder[A, B], translator: Translator, solver: Solver): PlannerResult[A] = {
        val startTime = System.currentTimeMillis()

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

        val fixedPlanner = new FixedPlanner(encoder, translator, solver).

        var timeStep = lowerBound
        var loop = true

        while (loop) {
            val result = fixedPlanner.solve(timeStep)

            loop = result.result == Result.Satisfactible || result.result == Result.Unknown

            timeStep += 1
        }

        PlannerResult()
    }
}