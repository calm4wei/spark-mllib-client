package com.cstor.spark.streaming

import org.apache.spark.streaming.{Minutes, Seconds, StreamingContext}
import org.apache.spark.{SparkConf}
import org.apache.spark.streaming.kafka._

/**
 * Created on 2016/5/31
 * @author feng.wei
 */
object SparkStreamingTest {

  def main(args: Array[String]) {

    val Array(zkQuorum, group, topics, numThreads) =
      Array("datacube151:2181,datacube154:2181", "test-consumer-group", "test2", "1")

    //    val sparkContext = new SparkContext(new SparkConf().setAppName("spark-streaming-test"))
    val sparkConf = new SparkConf().setAppName("spark-streaming-test")
    val ssc = new StreamingContext(sparkConf, Seconds(2))
    ssc.checkpoint("checkpoint")
    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap

    val lines = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap).map(_._2)
    val words = lines.flatMap(_.split(" "))
    val wordCounts = words.map(x => (x, 1L))
      .reduceByKeyAndWindow(_ + _, _ - _, Minutes(1), Seconds(2), 2)
    wordCounts.print()

    ssc.start()
    ssc.awaitTermination()

  }

}
