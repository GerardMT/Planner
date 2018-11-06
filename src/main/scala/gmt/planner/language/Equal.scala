package gmt.planner.language

import gmt.planner.language.Type.EnumType

object Equal extends TermOperation {
    def FUNCTION: (Term, Term) => Term = (a, b) => Equal(a, b)
}

case class Equal(a: Term, b: Term) extends Term {

    if (a.returnType != b.returnType) {
        throw InvalidTermException(toString)
    }

    override def returnType: EnumType = Type.Boolean
}
