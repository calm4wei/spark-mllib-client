package com.cstor.spark.sql

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created on 2016/8/9
  *
  * @author feng.wei
  */
object SparkSQLAds {


    def main(args: Array[String]) {
        val sql = "select * from test.ad_users where ip = '192.168.1.4'"
//        val spark = SparkSession.builder().appName("Spark SQL Example").enableHiveSupport().getOrCreate()

        val sc = new SparkContext(new SparkConf().setAppName("Spark SQL EX"))
        val spark = new SQLContext(sc)
        spark.read.load()
        val t1 = System.currentTimeMillis()
        val df = spark.sql(sql)
        val t2 = System.currentTimeMillis()
        println(sql + " consumer time=" + (t2 - t1))
        df foreach (d => print(d))

    }

}
