package com.cstor.spark.mllib.thirteen

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.stat.Statistics
import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by Administrator on 2016/5/13.
 * (曼哈顿距离，欧几里德距离)
 */
object IrisNormL {

  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local").setAppName("irisNormL")
    val sc = new SparkContext(conf)

    val data5 = sc.textFile("d://data/iris/setosa_sepal_length.txt")
    val data = sc.textFile("d://data/iris/setosa_sepal_length.txt").map(m => Vectors.dense(m.trim.toDouble))
    val summary = Statistics.colStats(data)
    println("(setosa中Sepal的哈密顿距离，欧几里德距离) = " + "(" + summary.normL1 + " , " + summary.normL2 + ")")

    val data2 = sc.textFile("d://data/iris/versicolor_sepal_length.txt").map(m => Vectors.dense(m.trim.toDouble))
    val summary2 = Statistics.colStats(data2)
    println("(versicolor中Sepal的哈密顿距离，欧几里德距离) = " + "(" + summary2.normL1 + " , " + summary2.normL2 + ")")

    val data3 = sc.textFile("d://data/iris/virginica_sepal_length.txt").map(m => Vectors.dense(m.trim.toDouble))
    val summary3 = Statistics.colStats(data3)
    println("(virginica中Sepal的哈密顿距离，欧几里德距离) = " + "(" + summary3.normL1 + " , " + summary3.normL2 + ")")


    // (setosa中Sepal的哈密顿距离，欧几里德距离) = ([250.29999999999998] , [35.48365821050586])
    // (versicolor中Sepal的哈密顿距离，欧几里德距离) = ([296.8] , [42.12908733879718])
    // (virginica中Sepal的哈密顿距离，欧几里德距离) = ([329.3999999999999] , [46.79636738038542])

  }

}
