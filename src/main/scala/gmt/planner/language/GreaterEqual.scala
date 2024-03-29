package gmt.planner.language

object GreaterEqual extends TermOperation {
    def FUNCTION: (Term, Term) => Term = (a, b) => GreaterEqual(a, b)
}

case class GreaterEqual(a: Term, b: Term) extends Term {

    if (a.returnType != Type.Integer || b.returnType != Type.Integer) {
        throw InvalidTermException(this)
    }

    override def returnType: Type.EnumType = Type.Boolean
}
