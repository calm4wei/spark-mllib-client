package com.cstor.spark.test.chapter04_depth_scala

/**
  * Created on 2016/9/18
  *
  * @author feng.wei
  */
trait Property {

    val name: String

    override def toString: String = "Property(" + name + ")"

}

class X extends {val name = "HI"} with Property
