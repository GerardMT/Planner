package gmt.planner.operation

object Ite extends TermOperation {
    def FUNCTION: (Term, Term, Term) => Term = (a, b, c) => Ite(a, b, c)
}

case class Ite(a: Term, b: Term, c: Term) extends Term {

    if (a.returnType != b.returnType || a.returnType != c.returnType) {
        throw InvalidTermException(toString)
    }

    override def returnType: Type.EnumType = a.returnType
}
