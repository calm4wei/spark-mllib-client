package com.cstor.spark.mllib.thirteen

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.stat.Statistics
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 均值与方差对比
 */
object IrisMeanAndVariance {
  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local").setAppName("irisMean") //设定名称
    val sc = new SparkContext(conf) //创建环境变量实例
    //    val data = sc.textFile("d://data/iris/all.txt").map(_.trim.toDouble).map(line => Vectors.dense(line)) //转成Vector格式
    val data = sc.textFile("d://data/iris/setosa_sepal_length.txt")
    val sepalData = data.map(m => Vectors.dense(m.trim.split(" ").map(_.toDouble))) //转成Vector格式
    val sepalDataSummary = Statistics.colStats(sepalData) //计算sepalData统计量
    println("(setosa中Sepal.Length的均值,方差) = " + "(" + sepalDataSummary.mean + " , " + sepalDataSummary.variance + ")")

    println("========================================================")
    val data2 = sc.textFile("d://data/iris/versicolor_sepal_length.txt")
    val sepalData2 = data2.map(m => Vectors.dense(m.trim.split(" ").map(_.toDouble))) //转成Vector格式
    val sepalDataSummary2 = Statistics.colStats(sepalData2) //计算peTalData统计量
    println("(versicolor中Sepal.Length的均值,Sepal.Length的方差) = " + "(" + sepalDataSummary2.mean + " , " + sepalDataSummary2.variance + ")")

    println("========================================================")
    val data3 = sc.textFile("d://data/iris/virginica_sepal_length.txt")
    val sepalData3 = data3.map(m => Vectors.dense(m.trim.split(" ").map(_.toDouble))) //转成Vector格式
    val sepalDataSummary3 = Statistics.colStats(sepalData3) //计算peTalData统计量
    println("(virginica中Sepal.Length的均值,Sepal.Length的方差) = " + "(" + sepalDataSummary3.mean + " , " + sepalDataSummary3.variance + ")")

    println("========================================================")
    val data4 = sc.textFile("d://data/iris/all.txt")
    val sepalData4 = data4.map(m => Vectors.dense(m.trim.split(" ").map(_.toDouble))) //转成Vector格式
    val sepalDataSummary4 = Statistics.colStats(sepalData4) //计算peTalData统计量
    println("(all中Sepal.Length的均值,Sepal.Length的方差) = " + "(" + sepalDataSummary4.mean + " , " + sepalDataSummary4.variance + ")")

    // (setosa中Sepal.Length的均值,方差) = ([5.006] , [0.12424897959183673])
    // (versicolor中Sepal.Length的均值,Sepal.Length的方差) = ([5.936000000000001] , [0.26643265306122454])
    // (virginica中Sepal.Length的均值,Sepal.Length的方差) = ([6.587999999999998] , [0.4043428571428571])
    // (all中Sepal.Length的均值,Sepal.Length的方差) = ([5.843333333333333] , [0.6856935123042518])

  }
}
