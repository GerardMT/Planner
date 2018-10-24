package gmt.planner.operation

case class ClauseDeclaration(a: Term) extends Term {

    if (a.returnType != Type.Boolean) {
        throw InvalidTermException(toString)
    }

    override def returnType: Type.EnumType = Type.Nothing
}