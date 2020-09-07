package lectures.part1as

import scala.annotation.tailrec

object Recap extends App{

  val aCondition: Boolean = false
  val aConditionedval = if (aCondition) 42 else 65
//  instructions vs. expression / in scala, we use expressions


//  compiler infers type for us
  val aCodeBlock = {
    if (aCondition) 56
    54  // value of the last expression is 54
  }

// Unit / for side effects / void in other languages
  val theUnit = println("hello scala")

//  functions
  def aFunction(x: Int): Int = x + 1

//  tailrec
  @tailrec
  def factorial(n:Int, accumulator: Int):Int =
    if (n<=0) accumulator
    else factorial(n-1,n*accumulator)

//  OOP
  class Animal
  class Dog extends Animal
  val aDog: Animal = new Dog  // subtyping polymorphism

  trait Carnivore {
    def eat(a: Animal): Unit
  }

  class Crocodile extends Animal with Carnivore {
    override def eat(a: Animal): Unit = println("crunch")
  }

//  method notation
  val aCroc = new Crocodile
  aCroc.eat(aDog)
  aCroc eat aDog

//  anonymous classes
  val aCarnivore = new Carnivore {
    override def eat(a: Animal): Unit = println("roar")
  }

//  generics
  abstract class MyList[+A] // variance and variance problems in this course
//  singletons and companions
  object MyList

//  case class
  case class Person(name: String, age: Int)

//  exceptions and try catch finally
//  val throwsExceptions = throw new RuntimeException // Nothing
  val aPotentialFailure = try{
    throw new RuntimeException
  } catch {
    case e: Exception => "I caught an exceptions"
  } finally{
    println("some logs")
  }

//  packaging and imports

//  functional programming
  val incrementer = new Function1[Int, Int] {
    override def apply(v1: Int): Int = v1 + 1
  }

  incrementer(1)
  val anonymousIncrementer = (x: Int) => x+1
  List(1,2,3).map(anonymousIncrementer)
//  map, flatmap, filter
  val pairs = for {
    num <- List(1,2,3)
    char <- List('a','b','c')
  } yield num + "-" + char

//  scala collections: Seq, Arrays, Lists, Vectors, Maps, Tuples
  val aMap = Map(
    "Daniel" -> 789,
    "Jess" -> 555
  )

//  collections: options, try
  val anOption = Some(2)

//  pattern matching
  val x = 2
  val order = x match {
    case 1 => "first"
    case 2 => "second"
    case 3 => "third"
    case _ => x+"th"
  }

  val bob = Person("Bob", 22)
  val greeting = bob match {
    case Person(n, _) => s"hi, my name is $n"
  }

//  all the patterns

 }
