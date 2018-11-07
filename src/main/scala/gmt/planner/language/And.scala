package gmt.planner.language

import scala.collection.immutable

object And extends TermOperation {
    def FUNCTION: (Term, Term) => Term = (a, b) => And(a, b)

    def FUNCTION_MULTIPLE: immutable.Seq[Term] => Term = many => And(many: _*)
}

case class And(many: Term*) extends Term {

    if (many.exists(f => f.returnType != Type.Boolean) || many.size < 2) {
        throw InvalidTermException(this)
    }

    override def returnType: Type.EnumType = Type.Boolean

    override def toString: String = "And(" + many.head + many.tail.map(f => "," + f.toString).mkString + ")"
}
