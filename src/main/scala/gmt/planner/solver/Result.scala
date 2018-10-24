package gmt.planner.solver

object Result {

    trait EnumResult

    case object Satisfactible extends EnumResult
    case object Unsatisfactible extends EnumResult
    case object Unknown extends EnumResult
}
