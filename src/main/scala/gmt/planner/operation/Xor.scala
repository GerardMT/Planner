package gmt.planner.operation

object Xor extends TermOperation {
    def FUNCTION: (Term, Term) => Term = (a, b) => Xor(a, b)
}

case class Xor(a: Term, b: Term) extends Term {

    if (a.returnType != Type.Boolean || b.returnType != Type.Boolean) {
        throw InvalidTermException(toString)
    }

    override def returnType: Type.EnumType = Type.Boolean
}