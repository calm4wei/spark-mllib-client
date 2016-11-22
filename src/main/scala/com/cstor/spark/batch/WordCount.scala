package com.cstor.spark.batch

import org.apache.spark.{SparkConf, SparkContext}
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

        val path = "D:\\data\\wc*"
        val dateTime = DateTime.now().getMillis
        val jobName = "WordCount"
        val sparkConf = new SparkConf().setAppName(jobName)
                .setMaster("local")
        val sc = new SparkContext(sparkConf)

        // 可以读入多个文件，通过正则进行匹配
        val data = sc.textFile(path, 2)

        val paris = {
            data.flatMap(l => l.replaceAll("\\.|,|\"", " ").trim.split(" ")).filter(w => !w.trim.equals("")).map(w => (w, 1))
        }

        //        val reParis = paris.repartition(4)
        val count = paris.reduceByKey((a, b) => a + b).sortByKey()
        count foreach {
            print
        }
        // TODO 如果目录存在则删除
        //        val outpath = "output/" + jobName + "-" + dateTime
        //        val fs = FileSystem.get(new org.apache.hadoop.conf.Configuration())
        //        if (fs.exists(new Path(outpath))) {
        //            fs.delete((new Path(outpath)), true)
        //        }
        //        count.saveAsTextFile(outpath)

    }
}
