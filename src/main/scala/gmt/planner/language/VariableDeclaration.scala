package gmt.planner.language

import gmt.planner.language.Type.EnumType

case class VariableDeclaration(a: Term) extends Term {

    if (!a.isInstanceOf[Variable]) {
        throw InvalidTermException(toString)
    }

    override def returnType: EnumType = Type.Nothing
}
