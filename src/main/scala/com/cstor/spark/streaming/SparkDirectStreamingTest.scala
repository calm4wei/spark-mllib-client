package com.cstor.spark.streaming

import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * Created on 2016/6/1
 * @author feng.wei
 */
object SparkDirectStreamingTest {

  def main(args: Array[String]) {

    val sparkConf = new SparkConf().setAppName("spark-direct-streaming-test")
    val ssc = new StreamingContext(sparkConf, Seconds(3))

    val Array(brokers, topics) = Array("datacube154:6667,datacube151:6667", "test2")
    val topicsSet = topics.split(",").toSet
    val kafkaParams = Map[String,String]("metadata.broker.list" -> brokers)
    val messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topicsSet)

    // Get the lines, split them into words, count the words and print
    val lines = messages.map(_._2)
    val words = lines.flatMap(_.split(" "))
    val wordCounts = words.map(x => (x, 1L)).reduceByKey(_ + _)
    wordCounts.print()

    // Start the computation
    ssc.start()
    ssc.awaitTermination()

  }

}
