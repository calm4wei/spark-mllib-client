package com.cstor.spark.mllib.tow

import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by Administrator on 2016/5/10.
 */
object WordCount {

  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local").setAppName("wordCount")
    val sc = new SparkContext(conf)
    val data = sc.textFile("d://data/wc.txt")
    data.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _).collect().foreach(println)
  }

}
