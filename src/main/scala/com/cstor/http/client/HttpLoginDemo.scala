package com.cstor.http.client

import java.net.URLEncoder

import org.apache.commons.httpclient.cookie.CookiePolicy
import org.apache.commons.httpclient.params.HttpMethodParams
import org.apache.commons.httpclient.{NameValuePair, HttpClient}
import org.apache.commons.httpclient.methods.{PostMethod, GetMethod}
import org.apache.commons.httpclient.Cookie
import org.apache.commons.codec.binary.Base64

/**
 * Created by Administrator on 2016/5/19.
 */
object HttpLoginDemo {

  def main(args: Array[String]) {
    val client = new HttpClient()

    val tmpCookies = login(client)

    val separatorRightChar = "["
    val separatorLeftChar = "]"
    val encoderRightChar = URLEncoder.encode(separatorRightChar, "utf8")
    val encoderLeftChar = URLEncoder.encode(separatorLeftChar, "utf8")
    //    val url = "http://datacube152:8080/api/v1/clusters/cstor_test/?fields=metrics/memory/Buffer._avg" + encoderRightChar + "1463627048,1463630648,15" + encoderLeftChar
    val url = "http://192.168.6.152:8080/api/v1/clusters/cstor_test/hosts/datacube154?fields=metrics/memory&_=1463727173818"
    val get = new GetMethod(url)
    get.setRequestHeader("cookie", "AMBARISESSIONID=1i6nb17yc0dqu171zxmhbdw7zg;")
    // AMBARISESSIONID=1srhifby00vl55uz1ormm3roi
    //    get.setRequestHeader("--user","admin:admin@cpro")
    //    val params = new HttpMethodParams()
    //    params.setParameter("fields", "metrics/memory/Buffer._avg[1463627048,1463630648,15],metrics/memory/Cache._avg[1463627048,1463630648,15],metrics/memory/Share._avg[1463627048,1463630648,15],metrics/memory/Swap._avg[1463627048,1463630648,15],metrics/memory/Total._avg[1463627048,1463630648,15],metrics/memory/Use._avg[1463627048,1463630648,15]&_=1463630628227")
    //    get.setParams(params)

    client.executeMethod(get)
    println(get.getResponseBodyAsString)

  }

  def login(client: HttpClient): String = {
    val user = "user"
    val password = "user"
    val authorisation = user + ":" + password
    val encodedAuthorisation = Base64.encodeBase64String(authorisation.getBytes())

    val postMethod = new PostMethod("http://datacube152:8080/api/v1/clusters/cstor_test/services/HDFS/components/NAMENODE?fields=metrics/dfs/namenode/LiveNodes")
    val data = Array(new NameValuePair("Authorization: Basic", authorisation))
    postMethod.setRequestBody(data)
    client.getParams.setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY)
    client.executeMethod(postMethod)

    val cookies = client.getState.getCookies
    println(cookies.length)
    var tmpCookies = ""
    for (c <- cookies) {
      println("cookie=" + c)
      tmpCookies += (c.toString + ";")
    }
    println("tmpCookies=" + tmpCookies)
    println(postMethod.getResponseBodyAsString)
    println("====================")
    tmpCookies
  }

}
