package gmt.planner.encoder

import gmt.planner.solver.Assignment

/**
  *
  * @tparam A encoding data
  * @tparam B decoding result
  */
trait Encoder[A, B] {

    def encode(nTimeSteps: Int): EncoderResult[A]

    def decode(assignments: Seq[Assignment], encodingData: A): B

    def lowerBound(): Int

    def upperBound(): Int
}
