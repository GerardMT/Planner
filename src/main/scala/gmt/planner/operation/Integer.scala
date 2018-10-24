package gmt.planner.operation

import gmt.planner.operation.Type.EnumType

case class Integer(value: Int) extends Term {

    override def returnType: EnumType = Type.Integer
}
