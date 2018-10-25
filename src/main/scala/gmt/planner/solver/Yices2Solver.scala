package gmt.planner.solver

import java.io._
import java.nio.charset.StandardCharsets

import gmt.planner.solver.SolverResult.{NoneSolverResult, SolverResult, SomeSolverResult}
import gmt.planner.solver.value.{ValueBoolean, ValueInteger}

import scala.collection.mutable.ListBuffer
import scala.sys.process._

class Yices2Solver(solverBinaryPath: String) extends Solver {

    override def solve(input: String): SolverResult = {

        val inputStream: InputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8.name))
        val outputStream = new ByteArrayOutputStream()

        val startTime = System.currentTimeMillis()

        (solverBinaryPath #< inputStream #> outputStream).run().exitValue()

        val endTime = System.currentTimeMillis()

        val lines = outputStream.toString.split("\n").toList

        val result = lines.head match {
            case "sat" =>
                Result.Satisfactible
            case "unsat" =>
                Result.Unsatisfactible
            case _ =>
                Result.Unknown
        }

        val time = endTime - startTime

        if (result != Result.Unknown) {
            val assignments = ListBuffer.empty[Assignment]

            for (l <- lines.tail) {
                if (l.startsWith("(=")) {
                    val s = l.drop(3).dropRight(1).split(' ')
                    val v = s(1) match {
                        case "true" =>
                            ValueBoolean(true)
                        case "false" =>
                            ValueBoolean(false)
                        case _ =>
                            if (s.length == 3) {
                                ValueInteger(-s(2).dropRight(1).toInt)
                            } else {
                                ValueInteger(s(1).toInt)
                            }
                    }
                    assignments.append(Assignment(s(0), v))
                }
            }

            SomeSolverResult(result, time, assignments)
        } else {
            NoneSolverResult(result, time)
        }
    }
}
