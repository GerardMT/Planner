package gmt.planner.encoder

import gmt.planner.solver.Assignment

trait Encoder[A, B] {

    def encode(timeSteps: Int): EncoderResult[B]

    def decode(assignments: Seq[Assignment], encodingData: B): A

    def lowerBound(): Int

    def upperBound(): Int
}
