package com.cstor.http.client

import java.io.{InputStream, InputStreamReader, BufferedReader}

import ch.ethz.ssh2.Connection

/**
  * Created by FengWei on 2016/5/21.
  */
object RunShell {

  def main(args: Array[String]) {

    val conn = new Connection("192.168.107.202")
    conn.connect()

    if (conn.authenticateWithPassword("root", "root")) {
      val session = conn.openSession()
      session.execCommand("/root/monitor.sh")

      val br = new BufferedReader(new InputStreamReader(session.getStdout))

      // TODO scala判断null
      var line = Option("")
      while ((line = Option(br.readLine())) != "None") {
          println(line)
      }

    }

    conn.close()

  }

  def execLinuxCommand(shPath: String): Unit = {
    val shPath = "/root/monitor.sh"
    val ps = Runtime.getRuntime.exec(shPath)

    //    ps.waitFor()

    val br = new BufferedReader(new InputStreamReader(ps.getInputStream))

    var line = ""
    line = br.readLine()
    while ((line) != null) {
      println(line)
    }
  }


  /**
    * 解析流获取字符串信息
    *
    * @param in      输入流对象
    * @param charset 字符集
    * @return
    */
  def processStdout(in: InputStream, charset: String): String = {


    ""

  }


}
