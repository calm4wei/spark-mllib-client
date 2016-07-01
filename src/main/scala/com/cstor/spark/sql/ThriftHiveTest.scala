package com.cstor.spark.sql

import java.sql.DriverManager

/**
 * Created on 2016/6/16
 * @author feng.wei
 */
object ThriftHiveTest {

  def main(args: Array[String]) {
    Class.forName("org.apache.hive.jdbc.HiveDriver")
    val t1 = System.currentTimeMillis()
    val conn = DriverManager.getConnection("jdbc:hive2://datacube151:10001/default", "spark", "")

    val pstat = conn.prepareStatement("select * from pokes order by bar desc")

    val rs = pstat.executeQuery()

    while (rs.next()) {

      println(rs.getString(1))
    }

    val t2 = System.currentTimeMillis()
    println("time = " + (t2 - t1))
    rs.close()

    pstat.close()

    conn.close()
  }

}
