package gmt.planner.language

import gmt.planner.language.Type.EnumType

abstract class Term {

    def unary_!(): Term = Not.FUNCTION(this)

    def ==>(that: Term): Term = Implies.FUNCTION(this, that) // Careful, higher precedence than && and ||

    def &&(that: Term): Term = And.FUNCTION(this, that)

    def ||(that: Term): Term = Or.FUNCTION(this, that)

    def ^(that: Term): Term = Xor.FUNCTION(this, that)

    def ==(that: Term): Term = Equal.FUNCTION(this, that) // Careful, higher precedence than && and ||

    def !=(that: Term): Term = Distinct.FUNCTION(this, that) // Careful, higher precedence than && and ||

    // ite


    def unary_-(): Term = Negate.FUNCTION(this)

    def -(that: Term): Term = Sub.FUNCTION(this, that)

    def +(that: Term): Term = Add.FUNCTION(this, that)

    def *(that: Term): Term = Multiply.FUNCTION(this, that)

    def /(that: Term): Term = Divide.FUNCTION(this, that)

    def %(that: Term): Term = Module.FUNCTION(this, that)

    def unary_abs(that: Term): Term = Absolute.FUNCTION(this)

    def <=(that: Term): Term = SmallerEqual.FUNCTION(this, that)

    def <(that: Term): Term = Smaller.FUNCTION(this, that)

    def >=(that: Term): Term = GreaterEqual.FUNCTION(this, that)

    def >(that: Term): Term = Greater.FUNCTION(this, that)


    def returnType: EnumType
}