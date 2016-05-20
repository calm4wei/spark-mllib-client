package com.cstor.spark.mllib.thirteen

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.{LinearRegressionWithSGD, LabeledPoint}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by Administrator on 2016/5/16.
 * 线性回归分析,
 * 并使用MLlib的均方误差(MSE)判断
 */
object IrisLinearRegression {

  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local").setAppName("irislinearregression")
    val sc = new SparkContext(conf)
    val data = sc.textFile("d://data/iris/sepal_length_wigth.txt")
    // 处理数据
    val parsedData = data.map { line =>
      val linea = line.replaceAll(" +", " ") // 多个空格替换为一个空格
    val parts = linea.trim.split(" ")
      //      for (part <- parts) {
      //        if (!part.isEmpty) {
      //
      //        }
      //
      //      }
      // println("parts[0,1] = " + "(" + parts(0).trim + "," + parts(1).trim + ")")
      LabeledPoint(parts(0).trim.toDouble, Vectors.dense(parts(1).trim.toDouble))
    }.cache()

    // 创建模型
    val numIterations = 100
    val model = LinearRegressionWithSGD.train(parsedData, numIterations)

    // numIterations = 10时， 回归公式为: y = [1.4555311265305944] * x + 0.0
    // numIterations = 100时，回归公式为: y = [1.454275314752946] * x + 0.0
    println("回归公式为: y = " + model.weights + " * x + " + model.intercept) //打印回归公式

    // 创建均方误差训练数据
    val valuesAndPres = parsedData.map { point => {
      val prediction = model.predict(point.features) // 创建数据
      (point.label, prediction)
    }
    }

    // 计算均方误差
    val MSE = valuesAndPres.map { case (v, p) => math.pow((v - p), 2) }.mean()
    // numIterations = 10时， 均方误差结果为：0.14003891091658002
    // numIterations = 100时，均方误差结果为：0.14001699145848823
    println("均方误差结果为：" + MSE)
  }

  /**
   * 计算均方误差
   * @param rdd
   */
  def mseAnalysis(rdd: RDD[LabeledPoint]) = {
    val valuesAndPreds = rdd.map { point => {
    }
    }
  }

}
