package com.cstor.hbase.client

import java.util
import java.util.UUID

import com.cstor.util.HBaseUtils
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.client.coprocessor.{AggregationClient, LongColumnInterpreter}
import org.apache.hadoop.hbase.filter.{CompareFilter, RowFilter, SubstringComparator}
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by feng.wei on 2016/5/25.
  */
object HbaseFakeData {


    private val conf = HBaseConfiguration.create()

    conf.set("hbase.zookeeper.quorum", "datacube151,datacube154")
    conf.set("zookeeper.znode.parent", "/hbase")

    def fakeData(num: Int, htable: HTable) {

        htable.setAutoFlushTo(false)
        val putList = new util.ArrayList[Put]()
        for (i <- 1 until (num)) {

            val put = new Put(Bytes.toBytes(i + ""))
            val value = UUID.randomUUID().toString.replaceAll("-", "")
            put.addColumn(Bytes.toBytes("f"), Bytes.toBytes(i + ""), Bytes.toBytes(value))
            putList.add(put)

        }
        htable.setAutoFlushTo(true)
        htable.put(putList)

    }

    def getData(hTable: HTable, rowkey: String, family: String, column: String): String = {
        val result = hTable.get(new Get(Bytes.toBytes(rowkey)))
        val value = Bytes.toString(result.getValue(Bytes.toBytes(family), Bytes.toBytes(column)))
        println("get the value is : " + value)
        value
    }

    def countData(tableName: String, cf: Array[Byte]): Long = {
        val aggregationClient = new AggregationClient(conf)
        val scan = new Scan()
        val rowCount = aggregationClient.rowCount(
            TableName.valueOf(tableName)
            , new LongColumnInterpreter()
            , scan)
        println("rowcount = " + rowCount)
        rowCount
    }

    def readHbaseBySpark() = {
        val sparkConf = new SparkConf().setMaster("local").setAppName("hs")
        val sc = new SparkContext(sparkConf)

        val hbaseConf = HBaseConfiguration.create()
        val tableName = "cstor:test01"
        hbaseConf.set(TableInputFormat.INPUT_TABLE, tableName)
        hbaseConf.set("hbase.zookeeper.quorum", "datacube205,datacube204,datacube203")
        hbaseConf.set("zookeeper.znode.parent", "/hbase")
        hbaseConf.set(TableInputFormat.INPUT_TABLE, tableName)

        val scan = new Scan()
        val rowFilter = new RowFilter(CompareFilter.CompareOp.EQUAL, new SubstringComparator("2"))
        scan.setFilter(rowFilter)

        HBaseUtils.setScan(hbaseConf, scan)

        val hbaseRDD = sc.newAPIHadoopRDD(hbaseConf
            , classOf[TableInputFormat]
            , classOf[ImmutableBytesWritable]
            , classOf[Result])

        println(tableName + " has number:" + hbaseRDD.count())
        hbaseRDD.foreach(println)
        println("*********tuple**********")

        //keyValue is a RDD[java.util.list[hbase.KeyValue]]
        val keyValue = hbaseRDD.map(x => x._2).map(_.list())

        println(tableName + " has number:" + keyValue.count())
        keyValue.foreach(println)
        println("*********list**********")

        //outPut is a RDD[String], in which each line represents a record in HBase
        val outData = HBaseUtils.format(keyValue, "rowkey=%s,columnFamily=%s,qualifier=%s,value=%s")

        println(tableName + " has number:" + outData.count())
        outData.foreach(println)
        println("================")

        sc.stop()
    }

    def main(args: Array[String]) {
        //    val tableName = "cstor:test01"
        //    val family = Bytes.toBytes("f")
        //    val htable = new HTable(conf, tableName)
        //    //    fakeData(100, htable)
        //    //    val value = getData(htable, "1", "f", "1")
        //    //    println("value=" + value)
        //    countData(tableName, family)
        //    htable.close()


        readHbaseBySpark
    }

}
