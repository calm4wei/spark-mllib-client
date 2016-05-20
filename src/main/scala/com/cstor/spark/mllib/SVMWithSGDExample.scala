package com.cstor.spark.mllib

import org.apache.spark.mllib.classification.{SVMModel, SVMWithSGD}
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics
import org.apache.spark.mllib.optimization.L1Updater
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by Administrator on 2016/5/18.
 * 线性支持向量机，默认情况下使用L2正规化训练，
 * MLlib也支持L1正规化，在这种情况下，问题就变成了线性问题
 */
object SVMWithSGDExample {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("SVMWithSGDExample")
    val sc = new SparkContext(conf)

    // $example on$
    // Load training data in LIBSVM format.
    val data = MLUtils.loadLibSVMFile(sc, "data/mllib/sample_libsvm_data.txt")

    // Split data into training (60%) and test (40%).
    val splits = data.randomSplit(Array(0.6, 0.4), seed = 11L)
    val training = splits(0).cache()
    val test = splits(1)

    // Run training algorithm to build the model 运行训练算法构建模型
    val numIterations = 100
    val model = L1Tran(training, numIterations)

    // Clear the default threshold. 清除默认阈值
    model.clearThreshold()

    // Compute raw scores on the test set. 在测试数据上计算原始分数
    val scoreAndLabels = test.map { point =>
      val score = model.predict(point.features)
      (score, point.label)
    }

    // Get evaluation metrics. 获取评估指标
    val metrics = new BinaryClassificationMetrics(scoreAndLabels)
    val auROC = metrics.areaUnderROC()

    // L2  Area under ROC = 1.0
    // L1  Area under ROC = 1.0
    println("Area under ROC = " + auROC)

    // Save and load model
    model.save(sc, "target/tmp/scalaSVMWithSGDModel")
    val sameModel = SVMModel.load(sc, "target/tmp/scalaSVMWithSGDModel")
    // $example off$

    sc.stop()
  }


  /**
   * SVMWithSGD.train 默认执行L2正规化
   * @param training
   * @param numIterations
   * @return
   */
  def L2Tran(training: RDD[LabeledPoint], numIterations: Int): SVMModel = {
    SVMWithSGD.train(training, numIterations)
  }

  /**
   * 设置正规化参数为1.0来执行L1正规化
   * @param training
   * @return
   */
  def L1Tran(training: RDD[LabeledPoint], numIterations: Int): SVMModel = {
    val svmAlg = new SVMWithSGD()
    svmAlg.optimizer.
      setNumIterations(numIterations).
      setRegParam(0.1).
      setUpdater(new L1Updater)

    svmAlg.run(training)
  }
}
