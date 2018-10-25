package gmt.planner.solver

import gmt.planner.solver.Result.EnumResult

object SolverResult {

    sealed abstract class SolverResult(val result: EnumResult,
                                       val milliseconds: Long)

    case class NoneSolverResult(override val result: EnumResult,
                                override val milliseconds: Long)
        extends SolverResult(result,
            milliseconds)

    case class SomeSolverResult(override val result: EnumResult,
                                override val milliseconds: Long,
                                assignments: Seq[Assignment])
        extends SolverResult(result,
            milliseconds)

}



