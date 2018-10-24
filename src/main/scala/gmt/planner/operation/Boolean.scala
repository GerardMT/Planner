package gmt.planner.operation

case class Boolean(value: scala.Boolean) extends Term {

    override def returnType: Type.EnumType = Type.Boolean
}