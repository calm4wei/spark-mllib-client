package com.cstor.http.client

import java.net.URLEncoder

/**
  * Created by FengWei on 2016/5/19.
  */
object HttpClientTest {

  // 参考 ： http://blog.csdn.net/steven2006/article/details/1931647
  //  http://www.cnblogs.com/HustJackyan/archive/2011/11/08/2240975.html
  def main(args: Array[String]) {
    val str = "%"
    val encoderStr = URLEncoder.encode(str,"utf8")
    println(encoderStr)
  }
}
