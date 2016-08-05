package com.cstor.spark.batch

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by FengWei on 2016/5/24.
  */
object SumOne {

  def main(args: Array[String]) {

    val sc = new SparkContext(new SparkConf().setAppName("sumone"))
    // hdfs://datacube154:8020/user/cstor/iris.data
    println("args(0) = " + args(0))
    val data = sc.textFile("hdfs://datacube154:8020/user/cstor/" + args(0))
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
