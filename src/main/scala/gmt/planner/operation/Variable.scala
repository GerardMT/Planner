package gmt.planner.operation

import gmt.planner.operation.Type.EnumType

case class Variable(name: String, returnType: EnumType) extends Term
