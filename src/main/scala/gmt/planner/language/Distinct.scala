package gmt.planner.language

import gmt.planner.language.Type.EnumType

object Distinct extends TermOperation {
    def FUNCTION: (Term, Term) => Term = (a, b) => Distinct(a, b)
}

case class Distinct(a: Term, b: Term) extends Term {

    if (a.returnType != b.returnType) {
        throw InvalidTermException(toString)
    }

    override def returnType: EnumType = a.returnType
}
