package com.cstor.spark.batch

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{Path, FileSystem}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created on 2016/12/6
  *
  * @author feng.wei
  */
object Test {

    def main(args: Array[String]) {
        val sc = new SparkContext(new SparkConf()
                .setAppName("")
                .setMaster("local")
        )
        //得到文件系统
        val fs: FileSystem = FileSystem.get(new Configuration)
        //目标路径
        val dstPath = new Path("/out")
        val out = fs.create(dstPath)
        out.write((10).toByte)
    }

}
