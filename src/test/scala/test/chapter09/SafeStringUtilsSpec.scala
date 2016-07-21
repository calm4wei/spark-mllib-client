package test.chapter09

import org.scalatest._
import com.cstor.spark.test.chapter09.SafeStringUtils

/**
  * Created on 2016/7/21
  *
  * @author feng.wei
  */
class SafeStringUtilsSpec extends FlatSpec with ShouldMatchers {


    "The SafeString Util trimToNone" should "rim non-empty strings" in {
        SafeStringUtils.trimToNone(" hi there ") should equal(Some("hi there"))
    }

    it should "deal with null safely" in {
        SafeStringUtils.trimToNone(null) should be(None)
    }

    it should "trim empty strings to None" in {
        SafeStringUtils.trimToNone("") should be(None)
        SafeStringUtils.trimToNone(" ") should be(None)
        SafeStringUtils.trimToNone("           ") should be(None) // tabs and spaces
    }

    "The SafeString Util parseToInt" should "parse the string to int" in {
        SafeStringUtils.parseToInt("123") should equal(Some(123))
        SafeStringUtils.parseToInt("  123  ") should equal(Some(123))
    }

    it should "parse the illegal string nothing exception" in {
        SafeStringUtils.parseToInt("illeage123") should be(None)
        SafeStringUtils.parseToInt("") should be(None)
    }

    "The SafeString Util parseToLong" should "parse string to long" in {
        SafeStringUtils.parseyToLong("123") should equal(Some(123))
        SafeStringUtils.parseyToLong("   123") should equal(Some(123))
    }

    it should "parse illegal string nothing exception" in {
        SafeStringUtils.parseyToLong("long123") should be(None)
        SafeStringUtils.parseyToLong("") should be(None)
        SafeStringUtils.parseyToLong(null) should be(None)
    }

    "The SafeString Util generateStr" should "generate the str contains A-Za-z only" in {
        SafeStringUtils.generateStr(2).replaceAll("[a-zA-Z]", "") should equal("")
    }

    it should "be sufficiently random" in {
        val src = SafeStringUtils.generateStr(100).toList.sorted
        val dest = SafeStringUtils.generateStr(100).toList.sorted
        src should not equal dest
    }

    it should "handle invalid input" in {
        SafeStringUtils.generateStr(-1) should equal("")
    }

}
