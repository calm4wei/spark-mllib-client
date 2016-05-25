#!/bin/sh

export HADOOP_CONF_DIR=/etc/hadoop/conf 
export CLASSPATH="$HADOOP_CONF_DIR:$CLASSPATH"

spark-submit \
  --driver-class-path $CLASSPATH \
  --class com.cstor.spark.test.SumOne \
  --master yarn-cluster \
  --executor-memory 10G \
  --num-executors 20 \
  /home/cstor/spark-1.0-SNAPSHOT.jar \
  iris.data
