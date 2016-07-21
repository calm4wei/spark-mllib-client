package com.cstor.spark.test

/**
  * Created on 2016/7/13
  *
  * @author feng.wei
  */
object Listy {

  def main(args: Array[String]) {
    val l = new Listy(1, 3, 4, 6)
    println(l.item)
    l.foreach(println)
    println(l(2))
  }

}

class Listy[A](items: A*) {

  val item: Option[A] = items.headOption

  val next: Option[Listy[A]] = {
    if (item.isDefined) Some(new Listy(items.tail: _*)) else None
  }

  def foreach(f: A => Unit): Unit = {
    for (i <- item; n <- next) {
      f(i)
      n.foreach(f)
    }
  }

  def apply(index: Int): Option[A] = {
    if (index < 1) item else next.flatMap(_.apply(index - 1))
  }

}
