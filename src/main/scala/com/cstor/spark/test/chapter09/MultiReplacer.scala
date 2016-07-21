package com.cstor.spark.test.chapter09

import java.io.{PrintWriter, File}
import scala.io.Source

/**
  * An application that can replace text inside existing files.
  * Usage: MultiReplacer <search pattern> <replacement text> file1 [file2...]
  *
  * Created on 2016/7/21
  *
  * @author feng.wei
  */
object MultiReplacer {

    def replaceInFile(search: String, replace: String, file: File): Unit = {
        val text = read(file).toString
        createBackupFile(text, file)

        val updated = text.replaceAll(search, replace)
        write(updated, file)
    }

    def replaceInFileNames(search: String, replace: String, files: List[String]): Unit = {
        val validFiles: List[File] = files map (new File(_)) filter (_.exists())

        validFiles foreach { f =>
            replaceInFile(search, replace, f)
        }
    }

    def read(file: File) = Source.fromFile(file).getLines().mkString("\n")

    def createBackupFile(s: String, file: File): Unit = {
        val dir = new File(file.getAbsoluteFile.getParent)

        var backupFile = new File(dir, s"${file.getName}.bak")
        while (backupFile.exists()) {
            backupFile = new File(dir, s"${file.getName}_${System.currentTimeMillis()}.bak")
        }
        write(s, backupFile)
    }

    def write(s: String, file: File): Unit = {
        val writer = new PrintWriter(file)
        writer.write(s)
        writer.close()
    }

    def main(args: Array[String]) {
        args.toList match {
            case search :: replace :: files if files.nonEmpty =>
                replaceInFileNames(search, replace, files)
            case _ =>
                println("Usage: MultiReplacer <search pattern> <replacement text> file1 [file2...]")
        }
    }

}
