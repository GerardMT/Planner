package gmt.planner.language

import gmt.planner.language.Type.EnumType

case class Comment(s: String) extends Term {

    override def returnType: EnumType = Type.Nothing
}
