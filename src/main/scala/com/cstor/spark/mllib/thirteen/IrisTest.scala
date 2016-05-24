package com.cstor.spark.mllib.thirteen

import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by feng.wei on 2016/5/24.
 */
object IrisTest {

  def main(args: Array[String]) {

    val conf = new SparkConf().setMaster("local").setAppName("irisTest")
    val sc = new SparkContext(conf)


    val data = sc.textFile("d://data/iris/iris.data")
    val rowData = data.map(line =>
      line.split(",")
    )

    rowData.map { line =>
      val arr = line.array
      println(arr(0))
    }



  }

}
