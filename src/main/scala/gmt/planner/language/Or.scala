package gmt.planner.language

import scala.collection.immutable

object Or extends TermOperation {
    def FUNCTION: (Term, Term) => Term = (a, b) => Or(a, b)

    def FUNCTION_MULTIPLE: immutable.Seq[Term] => Term = many => Or(many: _*)
}

case class Or(many: Term*) extends Term {

    if (many.exists(f => f.returnType != Type.Boolean) || many.size < 2) {
        throw InvalidTermException(this)
    }

    override def returnType: Type.EnumType = Type.Boolean

    override def toString: String = "Or(" + many.head + many.tail.map(f => "," + f.toString).mkString + ")"
}
