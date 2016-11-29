package com.cstor.spark.sql

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkContext, SparkConf}

/**
  * Created on 2016/11/22
  *
  * @author feng.wei
  */
object SparkSQLExample {

    def main(args: Array[String]) {
        val sparkConf = new SparkConf().setAppName("SparkSQLExample")
                .setMaster("local[4]")
        val sc = new SparkContext(sparkConf)

        val sqlContext = new SQLContext(sc)

        //        val df = sqlContext.read.format("json").json("data/sql/people.json")
        val df = sqlContext.read.load("data/sql/users.parquet")
        df.select("name", "favorite_color").show()
//        df.show()


        //        df.groupBy("name").count().show()
        //        df.orderBy("name")
    }

}
