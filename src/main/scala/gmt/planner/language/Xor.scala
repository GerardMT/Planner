package gmt.planner.language

object Xor extends TermOperation {
    def FUNCTION: (Term, Term) => Term = (a, b) => Xor(a, b)
}

case class Xor(a: Term, b: Term) extends Term {

    if (a.returnType != b.returnType) {
        throw InvalidTermException(this)
    }

    override def returnType: Type.EnumType = Type.Boolean
}