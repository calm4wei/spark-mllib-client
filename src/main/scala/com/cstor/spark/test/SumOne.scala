package com.cstor.spark.test

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by FengWei on 2016/5/24.
  */
object SumOne {

  def main(args: Array[String]) {

    val sc = new SparkContext(new SparkConf().setMaster("local").setAppName("sumone"))
    val data = sc.textFile("e://data/iris/iris.data")
    //     val firstColumnSum = data.flatMap(line => line.trim.split(",")).map(m => m(0).toDouble).reduce((a,b) => a + b)
    val firstCol = data.map { line =>
      val arr = line.trim.split(",")
      arr(0).toDouble
    }
    val firstColumnSum = firstCol.reduce((a, b) => a + b)
    println(firstColumnSum.toDouble)
    println(firstCol.count())
  }

}
