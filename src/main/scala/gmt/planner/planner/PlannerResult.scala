package gmt.planner.planner

import gmt.planner.fixedPlanner.FixedPlannerResult
import gmt.planner.solver.Result.EnumResult

import scala.collection.immutable

object PlannerResult {

    trait PlannerResult

    case class NonePlannerResult(result: EnumResult,
                                 milliseconds: Long,
                                 fixedPlannerResults: immutable.Seq[FixedPlannerResult])
        extends PlannerResult

    case class SomePlannerResult[A](override val result: EnumResult,
                                    override val milliseconds: Long,
                                    override val fixedPlannerResults: immutable.Seq[FixedPlannerResult])
        extends NonePlannerResult(result,
            milliseconds,
            fixedPlannerResults)
}

