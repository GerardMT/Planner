package gmt.planner.operation

object Implies extends TermOperation {
    def FUNCTION: (Term, Term) => Term = (a, b) => Implies(a, b)
}

case class Implies(a: Term, b: Term) extends Term {

    if (a.returnType != Type.Boolean || b.returnType != Type.Boolean) {
        throw InvalidTermException(toString)
    }

    override def returnType: Type.EnumType = Type.Boolean
}
