package com.cstor.spark.mllib

import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.tree.DecisionTree
import org.apache.spark.mllib.tree.model.DecisionTreeModel
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by Administrator on 2016/5/18.
 */
object DecisionTreeClassificationExample {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("DecisionTreeClassificationExample")
    val sc = new SparkContext(conf)

    // $example on$
    // Load and parse the data file.
    val data = MLUtils.loadLibSVMFile(sc, "data/mllib/sample_libsvm_data.txt")
    // Split the data into training and test sets (30% held out for testing)
    val splits = data.randomSplit(Array(0.7, 0.3))
    val (trainingData, testData) = (splits(0), splits(1))

    // Train a DecisionTree model.
    //  Empty categoricalFeaturesInfo indicates all features are continuous.
    val numClasses = 2
    val categoricalFeaturesInfo = Map[Int, Int]()
    val maxDepth = 5
    val maxBins = 32

    val modelGini = giniDecisionTree(trainingData, testData, numClasses, "gini", maxDepth, maxBins)
    //    Test Error = 0.03333333333333333
    //    Learned classification tree model:
    //      DecisionTreeModel classifier of depth 2 with 5 nodes
    //      If (feature 406 <= 20.0)
    //    If (feature 100 <= 165.0)
    //    Predict: 0.0
    //    Else (feature 100 > 165.0)
    //    Predict: 1.0
    //    Else (feature 406 > 20.0)
    //    Predict: 1.0

    val modelVariance = varianceDecisionTree(trainingData, testData, numClasses, "variance", maxDepth, maxBins)
    //    Test Mean Squared Error = 0.03333333333333333
    //    Learned regression tree model:
    //      DecisionTreeModel regressor of depth 2 with 5 nodes
    //      If (feature 406 <= 20.0)
    //    If (feature 100 <= 165.0)
    //    Predict: 0.0
    //    Else (feature 100 > 165.0)
    //    Predict: 1.0
    //    Else (feature 406 > 20.0)
    //    Predict: 1.0

    modelGini.save(sc, "target/tmp/myDecisionTreeClassificationModel")
    modelVariance.save(sc, "target/tmp/myDecisionTreeRegressionModel")

    // val sameModel = DecisionTreeModel.load(sc, "target/tmp/myDecisionTreeClassificationModel")

  }


  /**
   * 使用基尼不纯度作为不纯度算法
   * @param trainingData
   * @param 去
   * @param numClasses
   * @param impurity
   * @param maxDepth
   * @param maxBins
   * @return
   */
  def giniDecisionTree(trainingData: RDD[LabeledPoint], testData: RDD[LabeledPoint],
                       numClasses: Int, impurity: String, maxDepth: Int, maxBins: Int): DecisionTreeModel = {

    val categoricalFeaturesInfo = Map[Int, Int]()
    // 使用基尼不纯度作为不纯度算法
    val model = DecisionTree.trainClassifier(trainingData, numClasses, categoricalFeaturesInfo,
      impurity, maxDepth, maxBins)

    // 在测试实例上计算
    val labelAndPreds = testData.map { point =>
      val prediction = model.predict(point.features)
      (point.label, prediction)
    }

    val testErr = labelAndPreds.filter(r => r._1 != r._2).count().toDouble / testData.count()
    println("Test Error = " + testErr)
    println("Learned classification tree model:\n" + model.toDebugString)

    model
  }

  /**
   * 使用方差作为不纯度算法
   * @param trainingData
   * @param testData
   * @param numClasses
   * @param impurity
   * @param maxDepth
   * @param maxBins
   * @return
   */
  def varianceDecisionTree(trainingData: RDD[LabeledPoint], testData: RDD[LabeledPoint],
                           numClasses: Int, impurity: String, maxDepth: Int, maxBins: Int): DecisionTreeModel = {
    val categoricalFeaturesInfo = Map[Int, Int]()
    val model = DecisionTree.trainRegressor(trainingData, categoricalFeaturesInfo, impurity,
      maxDepth, maxBins)

    // 在测试实例上计算
    val labelAndPreds = testData.map { point =>
      val prediction = model.predict(point.features)
      (point.label, prediction)
    }

    val testMSE = labelAndPreds.map { case (v, p) => math.pow((v - p), 2) }.mean()
    println("Test Mean Squared Error = " + testMSE)
    println("Learned regression tree model:\n" + model.toDebugString)
    model
  }

}
