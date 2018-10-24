package gmt.planner.operation

object Module extends TermOperation {
    def FUNCTION: (Term, Term) => Term = (a, b) => Module(a, b)
}

case class Module(a: Term, b: Term) extends Term {

    if (a.returnType != Type.Integer || b.returnType != Type.Integer) {
        throw InvalidTermException(toString)
    }

    override def returnType: Type.EnumType = Type.Integer
}
