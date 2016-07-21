package com.cstor.spark.test.chapter09

import scala.util.{Random, Try}

/**
  * Created on 2016/7/21
  *
  * @author feng.wei
  */
trait SafeStringUtils {
    // Returns a trimmed version of the string wrapped in an Option,
    // or None if the trimmed string is empty.
    def trimToNone(s: String): Option[String] = {
        Option(s) map (_.trim) filterNot (_.isEmpty)
    }

    def parseToInt(s: String): Option[Int] = {
        trimToNone(s) flatMap { x => Try(x.toInt).toOption }
    }

    def parseyToLong(s: String): Option[Long] = {
        trimToNone(s) flatMap { x => Try(x.toLong).toOption }
    }

    def generateStr(size: Int): String = {
        val chars: Seq[Char] = ('A' to 'Z') ++ ('a' to 'z')
        1 to size map { _ => Random nextInt chars.size } map chars mkString ""
    }

}

object SafeStringUtils extends SafeStringUtils
