package com.cstor.spark.test.chapter09

/**
  * Created on 2016/7/21
  *
  * @author feng.wei
  */
object Multiplier {
    def apply(x: Int) = new Multiplier(x)

    def main(args: Array[String]) {
        val multi = Multiplier(3)
        println(multi.product(10))
    }
}

class Multiplier(val x: Int) {
    def product(y: Int) = x * y
}

