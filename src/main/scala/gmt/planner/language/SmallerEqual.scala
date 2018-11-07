package gmt.planner.language

object SmallerEqual extends TermOperation {
    def FUNCTION: (Term, Term) => Term = (a, b) => SmallerEqual(a, b)
}

case class SmallerEqual(a: Term, b: Term) extends Term {

    if (a.returnType != Type.Integer || b.returnType != Type.Integer) {
        throw InvalidTermException(this)
    }

    override def returnType: Type.EnumType = Type.Boolean
}
