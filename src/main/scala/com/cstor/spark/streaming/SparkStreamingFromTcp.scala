package com.cstor.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.log4j.Level

/**
  * Created on 2016/5/31
  *
  * @author feng.wei
  */
object SparkStreamingFromTcp {

    def main(args: Array[String]) {

        LoggerLevels.setStreamingLogLevels(Level.WARN)

        val sparkConf = new SparkConf().setAppName("spark-streaming-test")
                .setMaster("local[2]")
        val ssc = new StreamingContext(sparkConf, Seconds(1))

        val lines = ssc.socketTextStream("192.168.7.171", 9999)
        val words = lines.flatMap(_.split(" "))
        val wordCount = words.map(word => (word, 1)).reduceByKey(_ + _)
        wordCount.print

        ssc.start()
        ssc.awaitTermination()

    }

}
