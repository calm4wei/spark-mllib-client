package com.cstor.spark.test

/**
  * Created on 2016/7/13
  *
  * @author feng.wei
  */
class ListyHelper {

}

abstract class Listy[A] {
  def foreach(f: A => Unit): Unit

  def apply(index: Int): Option[A]

  def headOption: Option[A] = apply(0)
}

class NonEmptyListy[A](val item: A, val next: Listy[A]) extends Listy[A] {
  override def foreach(f: A => Unit): Unit = {
    f(item)
    next.foreach(f)
  }

  override def apply(index: Int): Option[A] = {
    if (index < 1) Some(item) else next.apply(index - 1)
  }
}

class EmptyListy[A] extends Listy[A] {
  override def foreach(f: (A) => Unit): Unit = {}

  override def apply(index: Int): Option[A] = None
}
