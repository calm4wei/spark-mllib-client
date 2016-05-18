package com.cstor.spark.mllib.thirteen

import org.apache.spark.mllib.clustering.KMeans
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.{LinearRegressionWithSGD, LabeledPoint}
import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by Administrator on 2016/5/16.
 */
object IrisMeans {

  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local").setAppName("IrisMeans ")
    val sc = new SparkContext(conf)
    //创建环境变量实例
    val data = sc.textFile("d://data/iris/setosa_all_features.txt")
    val parsedData = data.map { s =>
      val dataArr = s.replaceAll(" +", " ").trim.split(" ")
      Vectors.dense(dataArr.map(_.toDouble))
    }.cache()

    val numClusters = 3
    //最大分类数
    val numIterations = 20
    //迭代次数
    val model = KMeans.train(parsedData, numClusters, numIterations) //训练模型
    model.clusterCenters.foreach(println) //打印中心点坐标
  }

}
