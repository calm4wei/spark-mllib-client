package com.cstor.spark.sql

import java.io.File

import com.google.common.io._
import com.google.common.io._
import org.apache.hadoop.hive.ql.exec.spark.session.SparkSession
import org.apache.spark.SparkConf
import org.apache.spark.sql.Row
import java.io.File

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql._

/**
 * Created on 2016/6/16
 * @author feng.wei
 */
object HiveFromSpark {

  case class Record(key: Int, value: String)

  // Copy kv1.txt file from classpath to temporary directory
  val kv1Stream = HiveFromSpark.getClass.getResourceAsStream("/kv1.txt")
  val kv1File = File.createTempFile("kv1", "txt")
  kv1File.deleteOnExit()
  ByteStreams.copy(kv1Stream, Files.newOutputStreamSupplier(kv1File))

  def main(args: Array[String]) {
    val sparkConf = new SparkConf().setMaster("local").setAppName("HiveFromSpark")

    val sc = new SparkContext(sparkConf)
    val sqlContext = new org.apache.spark.sql.SQLContext(sc)

    val df = sqlContext.read.json("examples/src/main/resources/people.json")

  }
}

// scalastyle:on println
