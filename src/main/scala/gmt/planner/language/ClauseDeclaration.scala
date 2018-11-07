package gmt.planner.language

case class ClauseDeclaration(a: Term) extends Term {

    if (a.returnType != Type.Boolean) {
        throw InvalidTermException(this)
    }

    override def returnType: Type.EnumType = Type.Nothing
}