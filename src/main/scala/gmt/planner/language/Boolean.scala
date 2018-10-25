package gmt.planner.language

case class Boolean(value: scala.Boolean) extends Term {

    override def returnType: Type.EnumType = Type.Boolean
}