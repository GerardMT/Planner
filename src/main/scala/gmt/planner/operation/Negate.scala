package gmt.planner.operation

object Negate extends TermOperation {
    def FUNCTION: Term => Term = a => Negate(a)
}

case class Negate(a: Term) extends Term {

    if (a.returnType != Type.Integer) {
        throw InvalidTermException(toString)
    }

    override def returnType: Type.EnumType = Type.Integer
}
