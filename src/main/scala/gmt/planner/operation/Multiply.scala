package gmt.planner.operation

object Multiply extends TermOperation {
    def FUNCTION: (Term, Term) => Term = (a, b) => Multiply(a, b)
}

case class Multiply(a: Term, b: Term) extends Term {

    if (a.returnType != Type.Integer || b.returnType != Type.Integer) {
        throw InvalidTermException(toString)
    }

    override def returnType: Type.EnumType = Type.Integer
}
