package gmt.planner.fixedPlanner

import gmt.planner.solver.Result.EnumResult
import gmt.planner.solver.SolverResult.SomeSolverResult

object FixedPlannerResult {

    sealed abstract class FixedPlannerResult(val result: EnumResult,
                                             val milliseconds: Long,
                                             val timeSteps: Int)

    case class NoneFixedPlannerResult(override val result: EnumResult,
                                      override val milliseconds: Long,
                                      override val timeSteps: Int)
        extends FixedPlannerResult(result,
            milliseconds,
            timeSteps)

    case class SomeFixedPlannerResult(override val result: EnumResult,
                                      override val milliseconds: Long,
                                      override val timeSteps: Int,
                                      solverResult: SomeSolverResult)
        extends FixedPlannerResult(result,
            milliseconds,
            timeSteps)

}

