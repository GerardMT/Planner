package gmt.planner.operation

object Divide extends TermOperation {
    def FUNCTION: (Term, Term) => Term = (a, b) => Divide(a, b)
}

case class Divide(a: Term, b: Term) extends Term {

    if (a.returnType != Type.Integer || b.returnType != Type.Integer) {
        throw InvalidTermException(toString)
    }

    override def returnType: Type.EnumType = Type.Integer
}
