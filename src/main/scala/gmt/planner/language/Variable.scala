package gmt.planner.language

import gmt.planner.language.Type.EnumType

case class Variable(name: String, returnType: EnumType) extends Term
