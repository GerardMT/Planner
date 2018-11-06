package gmt.planner.language

object Or extends TermOperation {
    def FUNCTION: (Term, Term) => Term = (a, b) => Or(a, b)
}

case class Or(many: Term*) extends Term {

    if (many.exists(f => f.returnType != Type.Boolean) || many.isEmpty) {
        throw InvalidTermException(toString)
    }

    override def returnType: Type.EnumType = Type.Boolean
}
