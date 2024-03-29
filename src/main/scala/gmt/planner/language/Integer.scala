package gmt.planner.language

import gmt.planner.language.Type.EnumType

import scala.language.implicitConversions

object Integer {

    implicit def ImplicitConstructor(value: Int): Integer = Integer(value)
}

case class Integer(value: Int) extends Term {

    override def returnType: EnumType = Type.Integer
}
