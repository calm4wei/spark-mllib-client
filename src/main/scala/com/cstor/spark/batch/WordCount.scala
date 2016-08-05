package com.cstor.spark.batch

import org.apache.spark.{SparkContext, SparkConf}
import org.joda.time.DateTime

/**
  * Created on 2016/7/25
  *
  * @author feng.wei
  */
class WordCount {

}

object WordCount {

    def main(args: Array[String]) {

        val path = args(0)
        val dateTime = DateTime.now().getMillis
        val jobName = "WordCount"
        val sparkConf = new SparkConf().setAppName(jobName)
        val sc = new SparkContext(sparkConf)

        val data = sc.textFile(path, 2)

        val paris = {
            data.flatMap(l => l.replaceAll("\\.|,|\"", " ").trim.split(" ")).filter(w => !w.trim.equals("")).map(w => (w, 1))
        }

        //        val reParis = paris.repartition(4)
        val count = paris.reduceByKey((a, b) => a + b).sortByKey()
        // TODO 如果目录存在则删除
        count.saveAsTextFile("output/" + jobName + "-" + dateTime)

    }
}
