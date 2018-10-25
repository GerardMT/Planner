package gmt.planner.language

import gmt.planner.language.Type.EnumType

case class Integer(value: Int) extends Term {

    override def returnType: EnumType = Type.Integer
}
