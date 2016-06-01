package com.cstor.spark.test

import org.apache.spark.mllib.clustering.KMeans
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by Administrator on 2016/5/19.
 */
object MlLibTest {

  def main(args: Array[String]) {

    val conf = new SparkConf().setAppName("MlLibTest")
    val sc = new SparkContext(conf)

    // $example on$
    // Load and parse the data
    val data = sc.textFile("hdfs://datacube154:8020/user/cstor/mllib/" + args(0))
    val parsedData = data.map(s => Vectors.dense(s.split(' ').map(_.toDouble))).cache()

    // Cluster the data into two classes using KMeans
    val numClusters = 2
    val numIterations = 20
    val clusters = KMeans.train(parsedData, numClusters, numIterations)

    // Evaluate clustering by computing Within Set Sum of Squared Errors
    val WSSSE = clusters.computeCost(parsedData)
    println("Within Set Sum of Squared Errors = " + WSSSE)
    // Within Set Sum of Squared Errors = 0.11999999999994547

    // Save and load model
    //    clusters.save(sc, "target/org/apache/spark/KMeansExample/KMeansModel")
    //    val sameModel = KMeansModel.load(sc, "target/org/apache/spark/KMeansExample/KMeansModel")
    // $example off$

    sc.stop()
  }

}
