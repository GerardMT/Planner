package gmt.planner.fixedPlanner

import gmt.planner.encoder.Encoder
import gmt.planner.fixedPlanner.FixedPlannerResult.{FixedPlannerResult, NoneFixedPlannerResult, SomeFixedPlannerResult}
import gmt.planner.solver.Solver
import gmt.planner.solver.SolverResult.{NoneSolverResult, SomeSolverResult}
import gmt.planner.translator.Translator

class FixedPlanner[A](encoder: Encoder[A], translator: Translator, solver: Solver) {

    def solve(timeSteps: Int): FixedPlannerResult = {
        val startTime = System.currentTimeMillis()

        val encodingResult = encoder.encode(timeSteps)
        val solverResult = solver.solve(translator.translate(encodingResult))

        val endTime = System.currentTimeMillis()

        val time = endTime - startTime

        solverResult match {
            case some @ SomeSolverResult(result, _, _) =>
                SomeFixedPlannerResult(result, time, timeSteps, some)
            case NoneSolverResult(result, _) =>
                NoneFixedPlannerResult(result, time, timeSteps)
        }
    }
}