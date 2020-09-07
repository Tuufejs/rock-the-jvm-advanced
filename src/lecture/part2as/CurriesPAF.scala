package lecture.part2as

object CurriesPAF extends App {

//  curried functions
  val superAdder: Int => Int => Int =
    x => y => x+y
  val add3 = superAdder(3)
  println(add3(5))
  println(superAdder(3)(5)) //curried function

//  METHOD!
  def curriedAdder(x:Int)(y:Int): Int = x + y //curried method

  val add4: Int => Int = curriedAdder(4)

//  lifting = ETA-EXPANSION

//  functions != methods (JVM limitation)
  def inc(x: Int) = x + 1
  List(1,2,3).map(inc) // compiler rewrites as List(1,2,3).map(x => inc(x))

//partial function applications
  val add5 = curriedAdder(5) _ // tell compiler to transform into Int => int function

//  EXERCISE
  val simpleAddFunction = (x: Int, y: Int) => x+y
  def simpleAddMethod(x: Int, y: Int) = x + y
  def curriedAddMethod(x: Int)(y: Int) = x+y

//  add7: Int => Int = y => 7 + y
//  as many different implementations of add7 using the above

  val add71 = simpleAddMethod(7,_)
  println(add71(1))

  val add73 : Int => Int = simpleAddFunction(7,_)
  println(add73(3))
  val add77 = simpleAddFunction.curried(7)
  val add74 = (x: Int) => simpleAddFunction(x,7)

  val add75 = (x: Int) => simpleAddMethod(x,7)
  val add78 = (x: Int) => simpleAddMethod(7,_:Int)

  val add76 = (x:Int) => curriedAdder(7)(x)
  println(add76(6))
  val add72 = curriedAdder(7)_
  println(add72(2))
  val add79 = curriedAdder(7)(_)

//  underscores are powerful
  def concatenator( a: String, b: String, c: String) = a +b + c
  val insertName = concatenator("Hello, I'm ", _:String, ", how are you") // (x: String) => concatenator(hello, x, howareyou)
  println(insertName("vrata"))

  val fillInTheBlanks = concatenator("Hello, ", _:String, _:String)

//  EXERCISES
  /*
  * process a list of numbers and return their string representations with different formats
  *   use the %4.2f with curried formatter
   */
  def curriedFormatter(s: String)(number: Double): String = s.format(number)
  val numbers = List(Math.PI, Math.E, 1, 9.8, 1.3e-12)

  val simpleformat = curriedFormatter("%4.2f")_
  val seriousFormat = curriedFormatter("%8.6f")_
  val preciseFormat = curriedFormatter("%14.12f")_

  println(numbers.map(preciseFormat))
  /*
  * difference between
  *   functions vs. methods
  *   parameters by-name vs. 0-lambda
   */
  def byName(n: => Int) = n + 1
  def byFunction(f: () => Int) = f() + 1

  def method: Int = 42
  def parrentMethod(): Int = 42

  /*
  calling byName and byFunction
  - Int
  - Method
  - parrentMethod
  - lambda
  - PAF
   */


}
