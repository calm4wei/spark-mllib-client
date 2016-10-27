package com.cstor.util

import com.cstor.common.GlobalHConnection
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.protobuf.ProtobufUtil
import org.apache.hadoop.hbase.util.{Base64, Bytes}
import org.apache.hadoop.hbase.{CellUtil, KeyValue}
import org.apache.spark.rdd.RDD

import scala.collection.JavaConverters._

/**
  * Created on 2016/8/26
  *
  * @author feng.wei
  */
object HBaseUtils {

    /**
      * set scan using high spark api in org.apache.hadoop.conf.Configuration
      *
      * @param hbaseConf
      * @param scan
      * @return
      */
    def setScan(hbaseConf: Configuration, scan: Scan): Configuration = {
        val proto = ProtobufUtil.toScan(scan)
        val scanStr = Base64.encodeBytes(proto.toByteArray())
        hbaseConf.set(TableInputFormat.SCAN, scanStr)
        hbaseConf
    }

    /**
      * convert RDD[java.util.List[KeyValue] ] to the assigned format
      *
      * @param keyValue
      * @param format
      * @return if format="rowkey=%s,columnFamily=%s,qualifier=%s,value=%s"
      *         then rowkey=user2,columnFamily=f,qualifier=age,value=20
      */
    def format(keyValue: RDD[java.util.List[KeyValue]], format: String): RDD[String] = {
        //outPut is a RDD[String], in which each line represents a record in HBase
        keyValue.flatMap(x =>
            x.asScala.map(cell =>
                format.format(
                    Bytes.toStringBinary(cell.getRow),
                    Bytes.toStringBinary(CellUtil.cloneFamily(cell)),
                    Bytes.toStringBinary(CellUtil.cloneQualifier(cell)),
                    //                cell.getTimestamp.toString,
                    //                Type.codeToType(cell.getTypeByte),
                    Bytes.toStringBinary(CellUtil.cloneValue(cell))
                )
            )
        )
    }

    /**
      * write to HBase
      *
      * @param p , contains rowkey / ColumnFamily / ColumnQuqlier / value, for example: ((rowkey, ColumnFamily, ColumnQualier), value)
      * @param hTable
      */
    def put2HBase(p: Iterator[((String, String, String), String)], hTable: String): Unit = {
        // Connect to HTable
        //        val hConn = HConnectionManager.createConnection(HBaseConfiguration.create)
        val resultHTable = GlobalHConnection().getTable(hTable)
        resultHTable.setAutoFlushTo(false)

        // Write results to HTable
        p.foreach { pair =>
            val (hRow, hFamily, hQualifier) = pair._1
            resultHTable.put(
                new Put(hRow.getBytes).add(hFamily.getBytes, hQualifier.getBytes, Bytes.toBytes(pair._2)))
        }

        // Close HTable
        resultHTable.close()
    }

}
