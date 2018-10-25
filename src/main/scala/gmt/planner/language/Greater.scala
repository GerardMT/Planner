package gmt.planner.language

object Greater extends TermOperation {
    def FUNCTION: (Term, Term) => Term = (a, b) => Greater(a, b)
}

case class Greater(a: Term, b: Term) extends Term {

    if (a.returnType != Type.Integer || b.returnType != Type.Integer ) {
        throw InvalidTermException(toString)
    }

    override def returnType: Type.EnumType = Type.Integer
}
