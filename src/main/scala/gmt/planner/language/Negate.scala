package gmt.planner.language

object Negate extends TermOperation {
    def FUNCTION: Term => Term = a => Negate(a)
}

case class Negate(a: Term) extends Term {

    if (a.returnType != Type.Integer) {
        throw InvalidTermException(this)
    }

    override def returnType: Type.EnumType = Type.Integer
}
