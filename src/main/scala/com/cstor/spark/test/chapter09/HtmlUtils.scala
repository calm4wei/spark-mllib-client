package com.cstor.spark.test.chapter09

/**
  * Created on 2016/7/21
  *
  * @author feng.wei
  */
object HtmlUtils {

    def removeMarkup(input: String) = {
        input.replaceAll("""</?\w[^>]*>""","").replaceAll("<.*>","")
    }

}
