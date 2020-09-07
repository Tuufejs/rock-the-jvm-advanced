package exercises

import scala.annotation.tailrec
import scala.runtime.Nothing$

trait MySet[A] extends (A  => Boolean) {

  /*
  EXERCISE - implement a functional set
   */
  def apply(elem:A): Boolean = contains(elem)

  def contains(elem:A): Boolean
  def +(elem: A): MySet[A]
  def ++(anotherSet: MySet[A]): MySet[A]

  def map[B](f: A =>B) : MySet[B]
  def flatMap[B](f:A => MySet[B]): MySet[B]
  def filter(predicate: A => Boolean): MySet[A]
  def foreach(f: A => Unit): Unit

  /*
  EXERCISE
   * removing an element
   * intersection with another set
   * difference with another set
   */

  def -(elem: A): MySet[A]
  def --(anotherSet: MySet[A]): MySet[A] // difference
  def &(anotherSet: MySet[A]): MySet[A]

  def unary_! : MySet[A]
}

class EmptySet[A] extends MySet[A]{
  def contains(elem:A): Boolean = false
  def +(elem: A): MySet[A] = new NonEmptySet[A](elem, this)
  def ++(anotherSet: MySet[A]): MySet[A] = anotherSet // union

  def map[B](f: A =>B) : MySet[B] = new EmptySet[B]
  def flatMap[B](f:A => MySet[B]): MySet[B] = new EmptySet[B]
  def filter(predicate: A => Boolean): MySet[A] = this
  def foreach(f: A => Unit): Unit = ()

  //  exercise 2
  def -(elem: A): MySet[A] = this
  def --(anotherSet: MySet[A]): MySet[A] = this
  def &(anotherSet: MySet[A]): MySet[A]  = this

//  exercise 3
  def unary_! : MySet[A] = new PropertyBasedSet[A] (_ => true)

//  class AllInclusive[A] extends MySet[A]{
//
//    override def contains(elem: A): Boolean = true
//
//    override def +(elem: A): MySet[A] = this
//    override def ++(anotherSet: MySet[A]): MySet[A] = this
//
//    override def map[B](f: A => B): MySet[B] = ???
//    override def flatMap[B](f: A => MySet[B]): MySet[B] = ???
//
//    override def filter(predicate: A => Boolean): MySet[A] = ???
//    override def foreach(f: A => Unit): Unit = ???
//
//    override def -(elem: A): MySet[A] = ???
//
//    override def --(anotherSet: MySet[A]): MySet[A] = filter(!anotherSet)
//    override def &(anotherSet: MySet[A]): MySet[A] = filter(anotherSet)
//
//    override def unary_! : MySet[A] = new EmptySet[A]
//
//  }
}
// {x in A | property(x)}
class PropertyBasedSet[A](property: A => Boolean) extends MySet[A]{

  def contains(elem:A): Boolean = property(elem)
  def +(elem: A): MySet[A] =
    new PropertyBasedSet[A](x => property(x)|| x == elem)
  def ++(anotherSet: MySet[A]): MySet[A] =
    new PropertyBasedSet[A](x => property(x) || anotherSet(x))

  def map[B](f: A =>B) : MySet[B] = politelyFail
  def flatMap[B](f:A => MySet[B]): MySet[B] = politelyFail

  def foreach(f: A => Unit): Unit = politelyFail
  def filter(predicate: A => Boolean): MySet[A] = new PropertyBasedSet[A](x => property(x) && predicate(x))

  def -(elem: A): MySet[A] = filter(x => x != elem)
  def --(anotherSet: MySet[A]): MySet[A] = filter(!anotherSet)
  def &(anotherSet: MySet[A]): MySet[A] = filter(anotherSet)

  def unary_! : MySet[A] = new PropertyBasedSet[A](x => !property(x))

  def politelyFail = throw new IllegalArgumentException ("really deep rabbit hole ")
}


class NonEmptySet[A] (head:A, tail:MySet[A]) extends MySet[A]{
  def contains(elem:A): Boolean =
    elem == head || tail.contains(elem)

  def +(elem: A): MySet[A] =
    if (this contains elem) this
    else new NonEmptySet[A](elem, this)

  def ++(anotherSet: MySet[A]): MySet[A] =
    tail ++ anotherSet + head

  def map[B](f: A =>B) : MySet[B] = (tail map f) + f(head)
  def flatMap[B](f: A => MySet[B]): MySet[B] = (tail flatMap f) ++ f(head)
  def filter(predicate: A => Boolean): MySet[A] ={
    val filteredTail = tail filter predicate
    if (predicate(head)) filteredTail + head
    else filteredTail
  }
  def foreach(f: A => Unit): Unit = {
    f(head)
    tail foreach f
  }

//  part 2
  def -(elem: A): MySet[A] =
    if (head == elem) tail
    else tail - elem + head

  def --(anotherSet: MySet[A]): MySet[A]= filter(!anotherSet)
  def &(anotherSet: MySet[A]): MySet[A] = filter(anotherSet)

//  new operator
  def unary_! : MySet[A] = new PropertyBasedSet[A] (x => !this.contains(x))
  /*
  val s = MySet(1,2,3) = buildSet(seq(1,2,3), [])
  = buildSet(seq(2,3), [] + 1
   = buildSet(seq(3), [1]+2
   = buildSet(seq(), [1,2]+3
   =[1,2,3]
   */

}
  object MySet {


    def apply[A](values: A*): MySet[A] = {
      @tailrec
      def buildSet(valSeq: Seq[A], acc: MySet[A]): MySet[A] =
        if (valSeq.isEmpty) acc
        else buildSet(valSeq.tail, acc + valSeq.head)

      buildSet(values.toSeq, new EmptySet[A])
    }

  }


object MySetPlayground extends App {
  val s = MySet(1,2,3,4)
//  s + 3 ++ MySet(-1,-2) foreach println
//  s + 3 ++ MySet(-1,-2) map ( x => x*10) foreach println
//  s + 5 ++ MySet(-1,-2) + 3 flatMap ( x => MySet(x,x*10)) filter (_ % 2 == 0) foreach println

  val negative = !s // all natural not equal to s
  println(negative(2))
  println(negative(10))

  val negativeEven = negative.filter(_ % 2 ==0)
  println(negativeEven(5))
}
