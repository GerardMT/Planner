package gmt.planner.operation

import gmt.planner.operation.Type.EnumType

case class VariableDeclaration(a: Term) extends Term {

    if (!a.isInstanceOf[Variable]) {
        throw InvalidTermException(toString)
    }

    override def returnType: EnumType = Type.Nothing
}
