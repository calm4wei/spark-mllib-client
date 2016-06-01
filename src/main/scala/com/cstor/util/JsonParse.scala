package com.cstor.util

import com.fasterxml.jackson.core.JsonParseException
import org.json4s.jackson.JsonMethods._

import scala.collection.mutable


/**
 * Created on 2016/5/26
 * @author feng.wei
 */
object JsonParse {

  /**
   * Convert a one-level JSON String to a mutable.Map.
   *
   * @param j JSON String
   * @return if j is a valid JSON String, return a mutable.Map[String, String]. Otherwise, return
   *         an empty Map.
   */
  def toMutableMap(j: String): mutable.Map[String, String] = {
    try {
      val map = parse(j).values.asInstanceOf[Map[String, AnyRef]].mapValues(Some(_).mkString.trim)
        .withDefaultValue("UNKNOWN")
      mutable.HashMap(map.toSeq: _*)
    } catch {
      case ex: JsonParseException => mutable.Map.empty
    }
  }

  /**
   * Convert a one-level JSON String to a Map.
   *
   * @param j JSON String
   * @return if j is a valid JSON String, return a Map[String, String]. Otherwise, return an
   *         empty Map.
   */
  def toMap(j: String): Map[String, String] = {
    try {
      parse(j).values.asInstanceOf[Map[String, AnyRef]].mapValues(Some(_).mkString.trim)
        .withDefaultValue("UNKNOWN")
    } catch {
      case ex: JsonParseException => Map.empty
    }
  }
}
