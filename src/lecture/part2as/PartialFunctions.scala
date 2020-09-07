package lecture.part2as

object PartialFunctions extends App{

  val aFunction = (x: Int) => x+1 // Function1[Int, Int] === Int => Int

//  not to accept any other values tha 1 to 5
  val aFussyFunction = (x: Int) =>
    if (x ==1) 42
    else if (x == 2) 56
    else if (x == 5) 999
    else throw new FunctionNotApplicableException

  class FunctionNotApplicableException extends RuntimeException

  val aNicerFussyFunction = (x:Int) => x match {
    case 1 => 42
    case 2 => 56
    case 3 => 999
  }
//  {1,2,} => Int    ///partial function

  val aPartialFunction: PartialFunction[Int, Int] = {
    case 1 => 42
    case 2 => 56
    case 3 => 999
  } // partial function value

  println(aPartialFunction(2))
//  println(aPartialFunction(546))

//  partial functions utilities
  println(aPartialFunction.isDefinedAt(64))

//  can be lifted
  val lifted = aPartialFunction.lift // Int => Option[Int]
  println(lifted(2))
  println(lifted(95))

  val pfChain = aPartialFunction.orElse[Int, Int] {
    case 45 => 67
  }
  println(pfChain(2))
  println(pfChain(45))

//  PF extends normal functions

  val aTotalFunction: Int => Int = {
    case 1 => 99
  }

//  HOF accepts partial functions as well
  val aMappedList = List(1,2,3).map {
    case 1 => 42
    case 2 => 78
    case 3 => 1000
  }
  println(aMappedList)

  /*
  Note: PF can nly have ONE parameter type
   */

  /**
   * exercises
   *
   * 1 - construct a PF instance yourself
   * 2 - implement chatbot aas a PF
   *
   */
  val aManualFussyFUnction = new PartialFunction[Int, Int] {
      override def apply(x: Int) : Int = x match {
        case 1 => 42
        case 2 =>65
        case 5 => 999
      }

    override def isDefinedAt(x: Int): Boolean =
      x == 1 || x==2 || x==5
    }
  val response =  (x:String) => x match {
    case "Ahoj" => "Ahoj, co potrebujes"
    case "Nevim" => " tak si to rozmysli"
    case _ => "tenhle command neznam"
  }

  val chatbot: PartialFunction[String,String] = {
    case "Ahoj" => "Ahoj, co potrebujes"
    case "Nevim" => " tak si to rozmysli"
    case _ => "tenhle command neznam"
  }



//  scala.io.Source.stdin.getLines().foreach(line => println(response(line)))
  scala.io.Source.stdin.getLines().map(chatbot).foreach(println)
}
