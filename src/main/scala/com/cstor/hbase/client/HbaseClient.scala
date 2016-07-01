package com.cstor.hbase.client

import org.apache.hadoop.hbase.thrift.generated.Hbase
import org.apache.thrift.protocol.{TBinaryProtocol, TProtocol}
import org.apache.thrift.transport.TSocket

;

/**
  * Created on 2016/6/30
  *
  * @author feng.wei
  */
object HbaseClient {

  def main(args: Array[String]) {
    val transport = new TSocket("datacube151", 16000)
//        if (secure) {
    //          Map < String, String > saslProperties = new HashMap < String, String >();
    //          saslProperties.put(Sasl.QOP, "auth-conf,auth-int,auth");
    //
    //          /**
    //            * The Thrift server the DemoClient is trying to connect to
    //            * must have a matching principal, and support authentication.
    //            *
    //            * The HBase cluster must be secure, allow proxy user.
    //            */
    //          transport = new TSaslClientTransport("GSSAPI", null,
    //            "hbase", // Thrift server user name, should be an authorized proxy user.
    //            host, // Thrift server domain
    //            saslProperties, null, transport);
    //        }

    transport.open()

    val protocol = new TBinaryProtocol(transport, true, true)
    val client = new Hbase.Client(protocol)
    val tableNames = client.getTableNames()

    println("sum tables is: " + tableNames.size())
    transport.close()
  }

}
