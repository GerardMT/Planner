package gmt.planner.encoder

import gmt.planner.language.{ClauseDeclaration, Comment, InvalidTermException, Operations, Term, Variable, VariableDeclaration}

import scala.collection.mutable.ListBuffer
import scala.collection.{immutable, mutable}

class Encoding {

    private val _variablesSet = mutable.Set.empty[String]

    private val _expressions = ListBuffer.empty[Term]

    def add(terms: Term*): Unit = {
        for (t <- terms) {
            t match {
                case VariableDeclaration(v) =>
                    val variableName = v.asInstanceOf[Variable].name

                    if (_variablesSet(variableName)) {
                        throw new IllegalStateException("Variable already exists: " + variableName)
                    } else {
                        _variablesSet.add(variableName)
                        _expressions.append(t)
                    }
                case t @ ClauseDeclaration(_) =>
                    _expressions.append(t)
                case t @ Comment(_) =>
                    _expressions.append(t)
                case _ =>
                    throw InvalidTermException(t);
            }
        }
    }

    @deprecated
    def addAll(e: immutable.Seq[Term]): Unit = {
        e.foreach(f => add(f))
    }

    def terms: immutable.Seq[Term]  = _expressions.toList
}
