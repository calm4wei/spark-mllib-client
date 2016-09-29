package com.cstor.spark.test.chapter08_depth_scala

import scala.collection.LinearSeq

/**
  * Created on 2016/9/19
  *
  * @author feng.wei
  */
sealed trait BinaryTree[+A]

object BinaryTree {
    def traverse[A, U](t: BinaryTree[A])(f: A => U): Unit = {
        def traversetHelper(current: BinaryTree[A],
                            next: LinearSeq[BinaryTree[A]]): Unit = {
            current match {
                case Branch(value, lhs, rhs) =>
                    f(value)
                    traversetHelper(lhs, rhs +: next)
                case Leaf(value) if !next.isEmpty =>
                    f(value)
                    traversetHelper(next.head, next.tail)
                case NilTree if !next.isEmpty =>
                    traversetHelper(next.head, next.tail)
                case NilTree => ()
            }
            traversetHelper(t, LinearSeq())
        }
    }
}

case object NilTree extends BinaryTree[Nothing]

case class Branch[+A](value: A,
                      lhs: BinaryTree[A],
                      rhs: BinaryTree[A]) extends BinaryTree[A]

case class Leaf[+A](value: A) extends BinaryTree[A]

