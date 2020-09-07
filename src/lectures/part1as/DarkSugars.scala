package lectures.part1as

import scala.util.Try

object DarkSugars extends App{

//  syntax sugar #1: method with single params
  def singleArgMethod(arg: Int): String = s"$arg little ducks..."

  val description = singleArgMethod{
    42
  }


val aTryInstance = Try{ //java try {..}
  throw new RuntimeException
}

  List(1,2,3).map{ x=>
    x+1
  }

//  syntax sugert #2: single abstract method
  trait Action {
    def act(x:Int): Int
  }

  val anInstance: Action = new Action {
    override def act(x: Int): Int = x+1
  }

  val aFunkyInstance: Action = (x:Int) => x+1

//  example: Runnables
  val aThread = new Thread(new Runnable {
    override def run(): Unit = println("hello scala")
  })

  val aSweeterThread = new Thread(() => println("sweet scala"))

  abstract class AnAbstractType {
    def implemented: Int = 23
    def f(a:Int): Unit
 }

  val anAbstractInstance: AnAbstractType = (a:Int) => println("sweet")

//  syntax sugar #3: the :: and #:: method are special

  val prependedList = 2:: List(3,4)
//  scala spec: last char decides associativity of method
  1::2::3::List(4,5)

  class MyStream[T]{
    def -->:(value: T): MyStream[T] = this
  }
  val myStream = 1 -->: 2 -->:3 -->:new MyStream[Int]

//  syntax sugar #4: multi word method naming
  class TeenGirl(name: String){
  def `and then said`(gossip:String) = println(s"$name said $gossip")
}
  val lilly = new TeenGirl("Lilly")
  lilly `and then said` "Scala is so sweet"

//  syntax sugar #5: infix types
  class Composite[A,B]
//  val composite: Composite[Int, String]
  val composite: Int Composite String = ???

  class -->[A,B]
  val toward: Int --> String = ???

//  syntax sugar #6: update() is very special much like apply
  val anArray = Array(1,2,3)
  anArray(2) = 7 //rewritten to anArray.update(2, 7)
//  used in mutable collections
//  remember apply and update

//  syntax sugar #7: setters for mutable containers
  class Mutable{
    private var internalMember: Int = 0 // private OO encapsulation
  def member = internalMember // "getter"
  def member_=(value: Int): Unit =
    internalMember = value // "setter"
  }

  val aMutableContainer = new Mutable
  aMutableContainer.member = 42 //rewritten as aMutableContainer.member_=(42)

}

