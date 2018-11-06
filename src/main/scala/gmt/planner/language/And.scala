package gmt.planner.language

object And extends TermOperation {
    def FUNCTION: (Term, Term) => Term = (a, b) => And(a, b)
}

case class And(many: Term*) extends Term {

    if (many.exists(f => f.returnType != Type.Boolean) || many.isEmpty) {
        throw InvalidTermException(toString)
    }

    override def returnType: Type.EnumType = Type.Boolean
}
