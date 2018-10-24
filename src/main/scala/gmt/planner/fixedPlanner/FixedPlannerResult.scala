package gmt.planner.fixedPlanner

import gmt.planner.solver.Result.EnumResult
import gmt.planner.solver.SolverResult

case class FixedPlannerResult(result: EnumResult, milliseconds: Long, timeSteps: Int, solverResult: SolverResult)
