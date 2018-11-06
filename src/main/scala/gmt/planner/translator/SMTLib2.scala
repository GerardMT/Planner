package gmt.planner.translator

import gmt.planner.encoder.Encoding
import gmt.planner.language._

object SMTLib2 {

    abstract class Logic(val name: String)

    object QF_LIA extends Logic("QF_LIA")

}

class SMTLib2(logic: SMTLib2.Logic) extends Translator {

    override def translate(p: Encoding): String = {
        "(set-option :produce-models true)\n" +
            "(set-logic " + logic.name + ")\n" +
            p.expressions.map(f => translateTerm(f) + "\n").mkString +
            "(check-sat)\n" +
            "(get-model)\n" +
            "(exit)"
    }

    private def translateTerm(t: Term): String = {
        t match {
            case Variable(name, _) =>
                name
            case Boolean(value) =>
                if (value) {
                    "true"
                } else {
                    "false"
                }
            case Not(a) =>
                "(not " + translateTerm(a) + ")"
            case Implies(a, b) =>
                "(=> " + translateTerm(a) + " " + translateTerm(b) + ")"
            case And() | And(_) =>
                throw new IllegalStateException
            case And(a, b@_*) =>
                "(and " + translateTerm(a) + " " + b.map(f => translateTerm(f)).mkString(" ") + ")"
            case Or() | Or(_) =>
                throw new IllegalStateException
            case Or(a, b@_*) =>
                "(or " + translateTerm(a) + " " + b.map(f => translateTerm(f)).mkString(" ") + ")"
            case Xor(a, b) =>
                "(xor " + translateTerm(a) + " " + translateTerm(a) + ")"
            case Equal(a, b) =>
                "(= " + translateTerm(a) + " " + translateTerm(b) + ")"
            case Distinct(a, b) =>
                "(distinct " + translateTerm(a) + " " + translateTerm(b) + ")"
            case Ite(a, b, c) =>
                "(ite " + translateTerm(a) + translateTerm(b) + translateTerm(c) + ")"
            case Integer(value) =>
                value.toString
            case Negate(a) =>
                "(- " + translateTerm(a) + ")"
            case Sub(a, b) =>
                "(- " + translateTerm(a) + " " + translateTerm(b) + ")"
            case Add(a, b) =>
                "(+ " + translateTerm(a) + " " + translateTerm(b) + ")"
            case Multiply(a, b) =>
                "(* " + translateTerm(a) + " " + translateTerm(b) + ")"
            case Divide(a, b) =>
                "(div " + translateTerm(a) + " " + translateTerm(b) + ")"
            case Module(a, b) =>
                "(mod " + translateTerm(a) + " " + translateTerm(b) + ")"
            case Absolute(a) =>
                "(abs " + translateTerm(a) + ")"
            case SmallerEqual(a, b) =>
                "(<= " + translateTerm(a) + " " + translateTerm(b) + ")"
            case Smaller(c1, c2) =>
                "(< " + translateTerm(c1) + " " + translateTerm(c2) + ")"
            case GreaterEqual(a, b) =>
                "(>= " + translateTerm(a) + " " + translateTerm(b) + ")"
            case Greater(c1, c2) =>
                "(> " + translateTerm(c1) + " " + translateTerm(c2) + ")"
            case Ite(c1, c2, c3) =>
                "(ite " + translateTerm(c1) + " " + translateTerm(c2) + " " + translateTerm(c3) + ")"
            case Comment(s) =>
                "; " + s
            case VariableDeclaration(v) =>
                val s = v match {
                    case Variable(name, Type.Boolean) =>
                        name + " Bool"
                    case Variable(name, Type.Integer) =>
                        name + " Int"
                }

                "(declare-const " + s + ")"
            case ClauseDeclaration(c) =>
                "(assert " + translateTerm(c) + ")"
        }
    }
}