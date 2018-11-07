package gmt.planner.language

import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import scala.collection.immutable

object Operations {

    @Deprecated
    def getAMOLog(v: Seq[Variable], newVariablesPrefix: String): Seq[Term]= {
        val expressions = ListBuffer.empty[Term]

        val nBits = (Math.log(v.length) / Math.log(2)).ceil.toInt

        var newVariables = List.empty[Variable]

        for(i <- 0 until nBits) {
            val newVariable = Variable(newVariablesPrefix + i, Type.Boolean)
            expressions.append(VariableDeclaration(newVariable))
            newVariables = newVariable :: newVariables
        }

        for (i <- v.indices) {
            val binaryString = toBinary(i, nBits)

            for (j <- 0 until binaryString.length) {
                var c: Term = newVariables(j)

                if (binaryString(j) == '0') {
                    c = Not(c)
                }

                expressions.append(ClauseDeclaration(Or(Not(v(i)), c)))
            }
        }

        expressions
    }

    @Deprecated
    private def toBinary(i: Int, digits: Int): String = {
        String.format("%" + digits + "s", i.toBinaryString).replace(' ', '0')
    }

    @Deprecated
    def addCounter(variables: Seq[Variable], k: Int, newVariablesPrefix: String, comparator: (Term, Term) => Term): (Term, Seq[VariableDeclaration]) = {
        val variableDeclarations = ListBuffer.empty[VariableDeclaration]
        val ands = ListBuffer.empty[Term]

        val newVariables = ListBuffer.empty[Variable]

        for ((v, i) <- variables.zipWithIndex) {
            val newV = Variable(newVariablesPrefix + i, Type.Integer)
            newVariables.append(newV)
            variableDeclarations.append(VariableDeclaration(newV))
            ands.append(Equal(v, Equal(newV, Integer(1))))
            ands.append(Equal(Not(v), Equal(newV, Integer(0))))
        }

        ands.append(comparator(newVariables.reduce(Add.FUNCTION), Integer(k)))

        (And(ands: _*), variableDeclarations)
    }

    @Deprecated
    def simplify(c: Term): Term = {
        c match {
            case And(c1) => c1
            case c @ And(_, _, _*) => c
            case Or(c1) => c1
            case c @ Or(_, _, _*) => c
        }
    }

    def multipleTermsApply(terms: immutable.Seq[Term], f: immutable.Seq[Term] => Term): Term = terms match {
        case immutable.Seq(a)=>
            a
        case immutable.Seq(_, _, _*) =>
            f(terms)
    }

    def quadraticAmo(v: Seq[Variable]): immutable.Seq[Term] = {
        val terms = ListBuffer.empty[Term]

        val vPerf = v.to[ArrayBuffer]

        for (i <- 0 until vPerf.length - 1) {
            for (j <- i until vPerf.length) {
                terms.append(ClauseDeclaration(!vPerf(i) || !vPerf(j)))
            }
        }

        terms.toList
    }

    def alo(v: Seq[Variable]): immutable.Seq[Term] = {
        List(ClauseDeclaration(Or(v: _*)): Term)
    }

    def eoWithQuatradicAmo(v: Seq[Variable]): immutable.Seq[Term] = {
        alo(v) ++ quadraticAmo(v)
    }
}