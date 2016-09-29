package com.cstor.spark.test.chapter08_depth_scala

import java.io.{FileReader, BufferedReader, File}

/**
  * Created on 2016/9/19
  *
  * @author feng.wei
  */
class FileLineTraversable(file: File) extends Traversable[String] {
    override def foreach[U](f: (String) => U): Unit = {
        val input = new BufferedReader(new FileReader(file))
        try {
            var line = input.readLine()
            while (line != null) {
                f(line)
                line = input.readLine()
            }
        } finally {
            input.close()
        }
    }


    override def toString = "{Lines of " + file.getAbsolutePath + "}"
}
