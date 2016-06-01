package com.cstor.spark.test

import sys.process._

/**
 * Created by Administrator on 2016/5/12.
 */
class Test {

}

object Test {

  def main(args: Array[String]) {
    println("系统环境是：" + System.getenv())

    //    testFor()
    //    testWhile(10)
    testIf(-2)

    println("======= bash ========")
    testBashShell("ls -al ")

    println("======= regex ===========")
    testRegex("[0-9]+", "128 hh, yes 0")
  }

  def testRegex(regex: String, str: String): Unit = {
    val pattern = regex.r
    for (s <- pattern.findAllIn(str)) {
      println("match " + pattern + " is : " + s)
    }
    //    match [0-9]+ is : 1128
    //    match [0-9]+ is : 0

    println("match " + pattern + " firstly is : " + pattern.findFirstIn(str))
    //    match [0-9]+ firstly is : Some(128)

    val numItempattern = "([0-9]+) ([a-z]+)".r
    val numItempattern(num,item) = "20 wangwu"
    for (numItempattern(num,item) <- numItempattern.findAllIn("20 wangwu, 30 lisi, eeee xxx")){
      println(num + "," + item)
    }
  }

  def testBashShell(command: String): Unit = {
    command !
  }

  def testFor(): Unit = {
    val common = Array(1, 2, 3, 4)
    // 遍历数组
    for (i <- 0 until common.length)
      println(i + " : " + common(i))

    println("=======================")
    // 每两个元素一跳
    for (i <- 0 until(common.length, 2))
      println(i + " : " + common(i))

    println("=======================")
    // 倒序遍历
    for (i <- (0 until (common.length)).reverse)
      println(i + " : " + common(i))

    println("=======================")
    // 不需要数组下标，直接访问数组
    for (elem <- common)
      println(elem)
  }

  def testWhile(n: Int): Unit = {
    var m = n
    while (m > 0) {
      println(m)
      m -= 1
    }
  }

  def testIf(n: Int): Unit = {
    val s = if (n > 0) 1 else -1 // 可以将if/else的值赋给变量
    println(s)

    val x = if (n > 0) "postive" else -1 // 两个分支类型的公共超类为Any
    println(x)

    val u = if (n > 0) 1 else () // 每个表达式都应该有某种值 if ( n > 0) 1 等价于 if ( n > 0) 1 else ()
    println(u)
  }

}
