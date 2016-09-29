package com.cstor.spark.test.chapter04_depth_scala


/**
  * Created on 2016/9/18
  *
  * @author feng.wei
  */

trait Logger {
    def log(category: String, msg: String) : Unit = {
        println(category + ": " +msg)
    }
}

trait DataAccess {

    def query(in : String) : String= {
        5.toString
    }
}

class LoggedDataAccess extends DataAccess with Logger{
    override def query(in: String) : String= {
        log("QUERY", in)
        super.query(in)
    }
}
