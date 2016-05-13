package com.cstor.spark.test

/**
 * Created by Administrator on 2016/5/12.
 */
class Test {

}

object Test {

  def main(args: Array[String]) {
    println("系统环境是：" + System.getenv())

    val common = Array(1,2,3,4)
    // 遍历数组
    for (i <- 0 until common.length)
      println(i + " : " + common(i))

    println("=======================")
    // 每两个元素一跳
    for (i <- 0 until(common.length,2))
      println(i + " : " + common(i))

    println("=======================")
    // 倒序遍历
    for (i <- (0 until(common.length)).reverse)
      println(i + " : " + common(i))

    println("=======================")
    // 不需要数组下标，直接访问数组
    for (elem <- common)
      println(elem)
  }
}
