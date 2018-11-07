package gmt.planner.language

object Not extends TermOperation {
    def FUNCTION: Term => Term = a => Not(a)
}

case class Not(a: Term) extends Term {

    if (a.returnType != Type.Boolean) {
        throw InvalidTermException(this)
    }

    override def returnType: Type.EnumType = Type.Boolean
}
