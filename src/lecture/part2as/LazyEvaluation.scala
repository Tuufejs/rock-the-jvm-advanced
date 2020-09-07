package lecture.part2as

object LazyEvaluation extends App {

//  lazy delays the evaluatios of values - fails only when called
  lazy val x: Int = {
    println("hello")
    42
  }
  println(x)
  println(x)
//  stores values, not re eval again
}
