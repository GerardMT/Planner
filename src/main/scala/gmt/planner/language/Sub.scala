package gmt.planner.language

object Sub extends TermOperation {
    def FUNCTION: (Term, Term) => Term = (a, b) => Sub(a, b)
}

case class Sub(a: Term, b: Term) extends Term {

    if (a.returnType != Type.Integer || b.returnType != Type.Integer) {
        throw InvalidTermException(toString)
    }

    override def returnType: Type.EnumType = Type.Integer
}
