package gmt.planner.planner

import gmt.planner.fixedPlanner.FixedPlannerResult.{FixedPlannerResult, SomeFixedPlannerResult}
import gmt.planner.solver.Result.EnumResult

import scala.collection.immutable

object PlannerResult {

    sealed abstract class PlannerResult(val result: EnumResult,
                                        val milliseconds: Long,
                                        val fixedPlannerResults: immutable.Seq[FixedPlannerResult])

    case class NonePlannerResult(override val result: EnumResult,
                                 override val milliseconds: Long,
                                 override val fixedPlannerResults: immutable.Seq[FixedPlannerResult])
        extends PlannerResult(result,
            milliseconds,
            fixedPlannerResults)

    case class SomePlannerResult(override val result: EnumResult,
                                 override val milliseconds: Long,
                                 override val fixedPlannerResults: immutable.Seq[FixedPlannerResult],
                                 bestFixedPlannerResult: SomeFixedPlannerResult)
        extends PlannerResult(result,
            milliseconds,
            fixedPlannerResults)

}