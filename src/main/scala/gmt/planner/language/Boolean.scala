package gmt.planner.language

import scala.language.implicitConversions

object Boolean {

    implicit def ImplicitConstructor(value: scala.Boolean): Boolean = Boolean(value)
}

case class Boolean(value: scala.Boolean) extends Term {

    override def returnType: Type.EnumType = Type.Boolean
}