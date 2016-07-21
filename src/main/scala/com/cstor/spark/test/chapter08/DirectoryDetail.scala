package com.cstor.spark.test

import java.io.{File, FilenameFilter}

/**
  * Created on 2016/7/21
  *
  * @author feng.wei
  */
class DirectoryDetail(path: String, f: String => Boolean) {

  lazy val file: File = new File(path)

  lazy val filter = new FilenameFilter {
    override def accept(dir: File, name: String): Boolean = f(name)
  }

  def list: List[String] = file.list(
    filter
  ).toList

}

object DirectoryDetail {

  def isFile(f: String): Boolean = {
    f.endsWith("txt")
  }

  def main(args: Array[String]) {
    val fileList = new DirectoryDetail("D:\\data", isFile).list
    fileList.foreach(println(_))
  }
}
