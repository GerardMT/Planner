package gmt.planner.operation

object GreaterEqual extends TermOperation {
    def FUNCTION: (Term, Term) => Term = (a, b) => GreaterEqual(a, b)
}

case class GreaterEqual(a: Term, b: Term) extends Term {

    if (a.returnType != Type.Integer || b.returnType != Type.Integer) {
        throw InvalidTermException(toString)
    }

    override def returnType: Type.EnumType = Type.Boolean
}
