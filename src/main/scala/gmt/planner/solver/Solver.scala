package gmt.planner.solver

import gmt.planner.solver.SolverResult.SolverResult

abstract class Solver {

    def solve(input: String): SolverResult
}