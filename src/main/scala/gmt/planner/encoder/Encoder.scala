package gmt.planner.encoder

import gmt.planner.solver.value.Value

import scala.collection.immutable

trait Encoder[A] {

    def encode(nTimeSteps: Int): Encoding

    def decode(assignments: immutable.Map[String, Value]): immutable.Seq[A]

    def lowerBound(): Int

    def upperBound(): Int
}
