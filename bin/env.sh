#!/usr/bin/env bash
# Don't edit this file unless you know exactly what you're doing.

export ROOT=$(dirname $(cd $(dirname $0); pwd))
export HADOOP_CONF_DIR=/etc/hadoop/conf
export CLASSPATH="$HADOOP_CONF_DIR:$CLASSPATH"


echo "ROOT=$ROOT"
echo "HADOOP_CONF_DIR=$HADOOP_CONF_DIR"
echo "CLASSPATH=$CLASSPATH"
