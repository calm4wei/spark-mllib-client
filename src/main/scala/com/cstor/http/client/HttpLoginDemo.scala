package com.cstor.http.client

import org.apache.commons.httpclient.cookie.CookiePolicy
import org.apache.commons.httpclient.params.HttpMethodParams
import org.apache.commons.httpclient.{NameValuePair, HttpClient}
import org.apache.commons.httpclient.methods.{PostMethod, GetMethod}
import org.apache.commons.httpclient.Cookie;

/**
 * Created by Administrator on 2016/5/19.
 */
object HttpLoginDemo {

  def main(args: Array[String]) {
    val client = new HttpClient()


    val tmpCookies = login(client)


    val get = new GetMethod("http://datacube152:8080/api/v1/clusters/cstor_test/?fields=metrics/memory[1463627048,1463630648,15]")
    get.setRequestHeader("cookie", "" + "AMBARISESSIONID=ypq9dzmk59h91k8sw9vzkk1ph")
    //    get.setRequestHeader("--user","admin:admin@cpro")
//    val params = new HttpMethodParams()
//    params.setParameter("fields", "metrics/memory/Buffer._avg[1463627048,1463630648,15],metrics/memory/Cache._avg[1463627048,1463630648,15],metrics/memory/Share._avg[1463627048,1463630648,15],metrics/memory/Swap._avg[1463627048,1463630648,15],metrics/memory/Total._avg[1463627048,1463630648,15],metrics/memory/Use._avg[1463627048,1463630648,15]&_=1463630628227")
//    get.setParams(params)

    client.executeMethod(get)
    println(get.getResponseBodyAsString)

  }

  def login(client: HttpClient): String = {
    val postMethod = new PostMethod("http://datacube152:8080/#/login")
    val data = Array(new NameValuePair("--username", "user"),
      new NameValuePair("--password=", "user"))
    postMethod.setRequestBody(data)
    client.getParams.setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY)
    client.executeMethod(postMethod)

    val cookies = client.getState.getCookies
    println(cookies.length)
    var tmpCookies = ""
    for (c <- cookies) {
      tmpCookies += (c.toString + ";")
    }

    tmpCookies
  }

}
