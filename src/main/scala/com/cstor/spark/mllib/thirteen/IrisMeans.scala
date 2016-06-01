package com.cstor.spark.mllib.thirteen

import org.apache.spark.mllib.clustering.KMeans
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by Administrator on 2016/5/16.
 *
 * 使用分类和聚类算法分析
 *
 * 使用Kmeans算法进行聚类分析
 */
object IrisMeans {

  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local").setAppName("IrisMeans ")
    val sc = new SparkContext(conf)

    val data = MLUtils.loadLibSVMFile(sc, "d://data/iris/iris.data")

    //    val data = sc.textFile("d://data/iris/setosa_all_features.txt")
    //    .dense(s.split(' ').map(_.toDouble))
    val parsedData = data.map { point =>
      Vectors.dense(point.label)
    }
      .cache()

    val numClusters = 3
    //最大分类数
    val numIterations = 20
    //    迭代次数
    val model = KMeans.train(parsedData, numClusters, numIterations) //训练模型
    model.clusterCenters.foreach(println) //打印中心点坐标
  }

}
