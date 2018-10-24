package gmt.planner.solver

import gmt.planner.solver.Result.EnumResult

object SolverResult {

    trait SolverResult

    case class NoneSolverResult(result: EnumResult,
                                milliseconds: Long)
        extends SolverResult

    case class SomeSolverResult(override val result: EnumResult,
                                override val milliseconds: Long,
                                assignments: Seq[Assignment])
        extends NoneSolverResult(result, milliseconds)
}



