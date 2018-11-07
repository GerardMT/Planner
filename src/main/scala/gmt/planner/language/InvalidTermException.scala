package gmt.planner.language

case class InvalidTermException(term: Term) extends Exception(term.toString)
