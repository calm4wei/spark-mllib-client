package com.cstor.http.client

import java.io.{InputStreamReader, BufferedReader}
import java.net.{URL, HttpURLConnection}

import org.apache.commons.codec.binary.Base64

/**
 * Created by feng.wei on 2016/5/20.
 */
object HttpURLConnectionTest {

  def main(args: Array[String]) {

    val user = "admin"
    val password = "admin@cpro"
    val authorisation = user + ":" + password
    val encodedAuthorisation = Base64.encodeBase64String(authorisation.getBytes())

    val realURl = new URL("http://192.168.6.152:8080/api/v1/clusters/cstor_test/hosts/datacube154?fields=metrics/memory&_=1463727173818")
    val connection = realURl.openConnection()
    connection.setRequestProperty("Authorization", "Basic " + encodedAuthorisation)
    connection.setConnectTimeout(30000)
    connection.setReadTimeout(120000)

    connection.connect()
    //    val headerFields = connection.getHeaderFields
    //    val iter = headerFields.entrySet().iterator()
    //    while (iter.hasNext){
    //      val entry = iter.next()
    //      println(entry.getKey + ", " + entry.getValue)
    //    }

    val in = new BufferedReader(new InputStreamReader(
      connection.getInputStream()))

    //    var line = ""
    //    var result = ""
    //    while ((line = in.readLine()) != null) {
    //      result += line
    //    }
    //    println(result)

    in.close()

  }

}
