package com.cstor.spark.hbase

import com.cstor.util.HBaseUtils
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.filter.{CompareFilter, RowFilter, SubstringComparator}
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.spark.{Logging, SparkConf, SparkContext}

/**
  * Created on 2016/8/25
  *
  * @author feng.wei
  */
object HBaseInSpark extends Logging{

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

        HBaseUtils.setScan(hbaseConf, scan)

        val hbaseRDD = sc.newAPIHadoopRDD(hbaseConf
            , classOf[TableInputFormat]
            , classOf[ImmutableBytesWritable]
            , classOf[Result])

        //keyValue is a RDD[java.util.list[hbase.KeyValue]]
        val keyValue = hbaseRDD.map(x => x._2).map(_.list)

        //outPut is a RDD[String], in which each line represents a record in HBase
        val outData = HBaseUtils.format(keyValue, "rowkey=%s,columnFamily=%s,qualifier=%s,value=%s")

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
