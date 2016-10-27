package com.cstor.spark.hbase

import com.cstor.util.HBaseUtils
import org.apache.spark.rdd.RDD
import org.apache.spark.{Logging, SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer

/**
  * Created on 2016/10/27
  *
  * @author feng.wei
  */
object Hdfs2HBase extends Logging {

    val earthTable = "cstor:earth"

    def main(args: Array[String]) {

        val sparkConf = new SparkConf().setAppName("HDFS2HBase example").setMaster("local")
        val sc = new SparkContext(sparkConf)

        val data = sc.textFile("D:\\data\\earth.txt")

        val cData = cleanupData(data)

        write2HBase(cData)

    }

    /**
      * clean up data
      *
      * @param rdd
      * @return
      */
    def cleanupData(rdd: RDD[String]): RDD[Array[String]] = {
        rdd.map(r => r.split(" ")).filter {
            r => {
                r.length >= 4
            }
        }
    }

    def format(p: Iterator[Array[String]]): Iterator[((String, String, String), String)] = p.flatMap {
        item =>
            val list = ListBuffer.empty[((String, String, String), String)]

            val row = item.head

            val f = "dt"
            val value = item.last
            list += ((row, "f", "dt") -> value)
            list
    }

    def write2HBase(rdd: RDD[Array[String]]): Unit = {
        val pairs = rdd.mapPartitions(format).cache()
        pairs.foreachPartition(

            HBaseUtils.put2HBase(_, earthTable)
            //            pair => pair.foreach(
            //                p => {
            //                    val (row, hFamily, hQualifier) = p._1
            //                    val hConn = HConnectionManager.createConnection(HBaseConfiguration.create)
            //                    val table = hConn.getTable("")
            //                    table.setAutoFlush(false)
            //                    table.put(
            //                        new Put(row.getBytes).add(hFamily.getBytes, hQualifier.getBytes, p._2.getBytes)
            //                    )
            //
            //                }
            //            )
        )
    }


}
