package com.cstor.spark.sql

import org.apache.spark.sql.{SaveMode, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created on 2016/8/9
  *
  * @author feng.wei
  */
object SparkSQLAds {


    def main(args: Array[String]) {
        // val sql = "select * from test.ad_users where ip = '192.168.1.4'"

        val sc = new SparkContext(new SparkConf()
                .setMaster("local")
                .setAppName("Spark SQL EX"))

        val sqlContext = new SQLContext(sc)
        val df = sqlContext.read.load("D:\\work\\workspace\\openSource\\spark\\examples\\src\\main\\resources\\users.parquet")
        // df foreach (d => print(d))
        df.write.mode(SaveMode.Append).save("saveusers.parquet")

    }

}
