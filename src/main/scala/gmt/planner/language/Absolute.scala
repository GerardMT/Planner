package gmt.planner.language

object Absolute extends TermOperation {

    override def FUNCTION: Term => Term = a => Absolute(a)
}

case class Absolute(a: Term) extends Term {

    if (a.returnType != Type.Integer) {
        throw InvalidTermException(toString)
    }

    override def returnType: Type.EnumType = Type.Integer
}
