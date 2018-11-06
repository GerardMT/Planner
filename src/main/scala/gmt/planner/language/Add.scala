package gmt.planner.language

object Add extends TermOperation {
    def FUNCTION: (Term, Term) => Term = (a, b) => Add(a, b)
}

case class Add(a: Term, b: Term) extends Term {

    if (a.returnType != Type.Integer || b.returnType != Type.Integer) {
        throw InvalidTermException(toString)
    }

    override def returnType: Type.EnumType = Type.Integer
}