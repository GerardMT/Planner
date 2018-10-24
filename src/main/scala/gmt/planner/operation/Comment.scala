package gmt.planner.operation

import gmt.planner.operation.Type.EnumType

case class Comment(s: String) extends Term {

    override def returnType: EnumType = Type.Nothing
}
