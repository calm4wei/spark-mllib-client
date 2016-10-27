package com.cstor.common

import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.{HConnection, HConnectionManager}
import org.apache.spark.Logging

/**
  * Created on 2016/10/27
  *
  * @author feng.wei
  */
object GlobalHConnection extends Serializable with Logging {
    sys.addShutdownHook(close())

    /**
      * The HBase connection
      */
    private lazy val hConn = {
        val hbaseConf = HBaseConfiguration.create()
        hbaseConf.set("hbase.zookeeper.quorum", "cstor02")
        hbaseConf.set("zookeeper.znode.parent", "/hbase")
        HConnectionManager.createConnection(hbaseConf)
    }

    def apply(): HConnection = hConn

    /**
      * Close HBase connection
      */
    def close(): Unit = if (!hConn.isClosed) {
        logInfo("Closing the global HBase connection")
        hConn.close()
        logInfo("The global HBase connection closed")
    }

}
