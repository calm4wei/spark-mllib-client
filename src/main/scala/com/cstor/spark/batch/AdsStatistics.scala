package com.cstor.spark.batch

import org.apache.hadoop.fs.{Path, FileSystem}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created on 2016/8/11
  *
  * @author feng.wei
  */
class AdsStatistics(sc: SparkContext, inputPath: String, jobName: String) {

    def apply = {

        val data = sc.textFile(inputPath)
        val result = data.map { d =>
            val arr = d.split("\t")
            (arr(0) + ", " + arr(2), 1)
        }.reduceByKey((a, b) => a + b).sortByKey()

        val output = "output/" + jobName
        val hadoopConf = sc.hadoopConfiguration
        val fileSystem = FileSystem.get(hadoopConf)
        val outputPath = new Path(output)
        if (fileSystem.exists(outputPath)) {
            fileSystem.delete(outputPath, true)
        }
        //        result.saveAsTextFile(output)
        result.coalesce(2, false).saveAsTextFile(output)
    }

}

object AdsStatistics {
    def main(args: Array[String]) {
        val input = args(0)
        val jobName = AdsStatistics.getClass.getName
        val sparkConf = new SparkConf().setAppName(jobName)
        val sc = new SparkContext(sparkConf)
        val ads = new AdsStatistics(sc, input, jobName)
        ads.apply
    }

}
