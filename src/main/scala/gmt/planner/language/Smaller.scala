package gmt.planner.language

object Smaller extends TermOperation {
    def FUNCTION: (Term, Term) => Term = (a, b) => Smaller(a, b)
}

case class Smaller(a: Term, b: Term) extends Term {

    if (a.returnType != Type.Integer || b.returnType != Type.Integer ) {
        throw InvalidTermException(toString)
    }

    override def returnType: Type.EnumType = Type.Integer
}
