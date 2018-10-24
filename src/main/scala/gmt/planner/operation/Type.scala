package gmt.planner.operation

object Type {

    sealed trait EnumType

    case object Nothing extends EnumType
    case object Boolean extends EnumType
    case object Integer extends EnumType
}