package com.cstor.spark.batch

import org.apache.hadoop.fs.{Path, FileSystem}
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.filter.{CompareFilter, RowFilter, SubstringComparator}
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.protobuf.ProtobufUtil
import org.apache.hadoop.hbase.util.{Base64, Bytes}
import org.apache.hadoop.hbase.{CellUtil, HBaseConfiguration}
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.JavaConverters._

/**
  * Created on 2016/8/25
  *
  * @author feng.wei
  */
object HBaseInSpark {

    def main(args: Array[String]) {

        val sparkConf = new SparkConf().setAppName("operation HBase in spark")
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

        val proto = ProtobufUtil.toScan(scan)
        val scanStr = Base64.encodeBytes(proto.toByteArray())
        hbaseConf.set(TableInputFormat.SCAN, scanStr)

        val hbaseRDD = sc.newAPIHadoopRDD(hbaseConf
            , classOf[TableInputFormat]
            , classOf[ImmutableBytesWritable]
            , classOf[Result])

        //keyValue is a RDD[java.util.list[hbase.KeyValue]]
        val keyValue = hbaseRDD.map(x => x._2).map(_.list)

        //outPut is a RDD[String], in which each line represents a record in HBase
        val outData = keyValue.flatMap(x => x.asScala.map(cell =>
            "rowkey=%s,columnFamily=%s,qualifier=%s,value=%s".format(
                Bytes.toStringBinary(cell.getRow),
                Bytes.toStringBinary(CellUtil.cloneFamily(cell)),
                Bytes.toStringBinary(CellUtil.cloneQualifier(cell)),
                //                cell.getTimestamp.toString,
                //                Type.codeToType(cell.getTypeByte),
                Bytes.toStringBinary(CellUtil.cloneValue(cell))
            )
        )
        )

        println(tableName + " has number:" + outData.count())
        outData.foreach(println)
        println("================")

        val outputstr = "/user/cstor/output/spark/hbase/test"
        val outPut = new Path(outputstr)
        val fs = FileSystem.get(new org.apache.hadoop.conf.Configuration())
        if (fs.exists(outPut)) {
            // 重命名hdfs上的目录名称
            fs.rename(outPut, new Path(outputstr + ".bak." + System.currentTimeMillis()))
            // fs.delete(outPut, true)
        }
        outData.saveAsTextFile(outputstr)
        sc.stop()

    }

}
