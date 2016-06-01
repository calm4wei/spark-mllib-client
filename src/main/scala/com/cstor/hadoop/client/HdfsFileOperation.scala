package com.cstor.hadoop.client

import java.io.{PrintWriter, BufferedReader, InputStreamReader}
import java.net.URI

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{Path, FileSystem}
import org.apache.hadoop.hbase.util.Bytes

import scala.io.Source

/**
 * Created on 2016/5/26
 * @author feng.wei
 */
object HdfsFileOperation {


  def readFromHdfs(filePath: Path, hdfs: FileSystem): Unit = {
    //    val fileList = hdfs.getFileStatus(new Path(dfsPath + "/user/cstor/"))
    val fileList = hdfs.listStatus(filePath)
    println(fileList.length)

    for (f <- fileList) {
      println(f.getPath.getName + "," + f.getOwner + "," + f.isDirectory)
    }

    println("================================")
    val inputStream = hdfs.open(new Path((filePath + "/test.log")))
    val inputReader = new BufferedReader(new InputStreamReader(inputStream))
    println(inputReader.readLine())

    inputStream.close()
  }

  def putFileToHdfs(srcPath: Path, delPath: Path, fileSystem: FileSystem): Unit = {
    fileSystem.copyFromLocalFile(srcPath, delPath)
  }

  def main(args: Array[String]) {
    val conf = new Configuration()
    val dfsPath = "hdfs://datacube154:8020"
    conf.set("fs.default", dfsPath)
//    conf.set("dfs.permissions", "false")
    // System.setProperty("HADOOP_USER_NAME", "cstor")
//    System.setProperty("HADOOP_PROXY_USER", "cstor")
    println("HADOOP_USER_NAME=" + System.getenv("HADOOP_USER_NAME") + " ,HADOOP_PROXY_USER=" + System.getProperty("HADOOP_PROXY_USER"))

    val fileSystem = FileSystem.get(URI.create(dfsPath), conf)
//    fileSystem.mkdirs(new Path(dfsPath + "/user/cstor/newdir"))
    // readFromHdfs(new Path(dfsPath + "/user/cstor"), fileSystem)

    putFileToHdfs(new Path("d://data/wc2.txt"), new Path(dfsPath + "/user/cstor/newdir"), fileSystem)

    fileSystem.close()


  }

}
