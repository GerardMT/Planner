package gmt.planner.fixedPlanner

import gmt.planner.encoder.Encoder
import gmt.planner.solver.Solver
import gmt.planner.translator.Translator

class FixedPlanner[A, B](encoder: Encoder[A, B], translator: Translator, solver: Solver) {

    def solve(timeSteps: Int): FixedPlannerResult[A] = {
        val startTime = System.currentTimeMillis()

        val encodingResult = encoder.encode(timeSteps)
        val solverResult = solver.solve(translator.translate(encodingResult.encoding))

        val endTime = System.currentTimeMillis()

        FixedPlannerResult(solverResult.result, endTime - startTime, timeSteps, solverResult)
    }
}