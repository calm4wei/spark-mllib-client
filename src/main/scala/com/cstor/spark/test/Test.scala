package com.cstor.spark.test

import java.io.File

import scala.collection.mutable.Buffer
import scala.io.Source
import scala.sys.process._

/**
  * Created by Administrator on 2016/5/12.
  */
class Test {

}

object Test {


  def main(args: Array[String]) {
    //    println("系统环境是：" + System.getenv())

    //    testFor()
    //    testWhile(10)
    //    testIf(-2)

    //    println("======= bash ========")
    //    testBashShell("ls -al ")

    //    println("======= regex ===========")
    //    testRegex("[0-9]+", "128 hh, yes 0")

    //    chapter6_01()
    //    println(chapter6_02_factor(20))
    //    println(chapter6_02_uniqueFactor(List(9, 10, 18)))
    //    println(chapter6_02_uniqueFactorByFor(List(9, 10, 18)))
    //    val chars = ('a' to 'f').toList
    //    println(chapter6_03_first(chars, 2))
    //    println(chapter6_03_first(chars, 2))
    //    println(chapter6_03_firstByFoldleft(chars, 10))
    //    println(chapter6_03_fisrtByTail(chars, 2))
    //    println(chapter6_04(List("aaa", "bb", "cccc", "qqq")))
    //    println(chapter6_04_fold(List("aaa", "bb", "cccc", "qqq")))
    //    println(chapter6_04_reduce(List("aaa", "bb", "cccc", "qqq")))
    //    val names: List[String] = List("aaqa", "bbq", "cqcccc", "qqaqq")
    //    println(chapter6_04_greatest[String](names, (x, y) => if (x.size > y.size) x else y))
    //    println(chapter6_05_reverse(names))
    //    println(chapter6_05_reverse_recursion(names))
    //    println(chapter6_06_partion(names))
    //    // http://api.openweathermap.org/data/2.5/weather?q=Nanjing&mode=json&APPID=7cac89b6cb49f7b41b3b49c20503c110
    //    val l = chapter6_07("http://api.openweathermap.org/data/2.5/weather?q=Nanjing&mode=xml&APPID=7cac89b6cb49f7b41b3b49c20503c110")
    //    println(l(0))
    //    println(l.size)


    //    val fina = chater7_01_a(5, Buffer(1, 1))
    //    println(fina)
    //    val fibonaccis = chater7_01_a_builder(5)
    //    println(fibonaccis)
    //    val report = chater7_01_c(1,1).take(100).toList.grouped(10).map(_.mkString(","))
    //    report.foreach(println(_))
    //    println(chater7_01_c(1, 1).take(8).toList(index + 1))1
    //    println(chater7_04("12", "3"))
    //    println(chater7_05(null))


    //    println(chapter3_01(""))
    //    println(chapter3_02(0))
    //    chapter3_04(100, 5)

    //    println(chatper4_07((12, 2)))
    //    println(chapter5_01((1,32,6), max))
    println(chapter5_03(5, chanster))
  }

  def chapter5_03(a: Int, chans: Int => Int): Int = {
    a * chans(a)
  }

  def chanster(x: Int) = x * 10

  def chapter5_01(t: (Int, Int, Int), cmp: (Int, Int) => Int): Int = {
    cmp(t._1, cmp(t._2, t._3))
  }

  def max(a: Int, b: Int): Int = if (a > b) a else b

  def chatper4_07[A, B](t: (A, B)) = {
    if (t._1.isInstanceOf[Int])
      t._1
  }

  def chapter3_05(n: Int) = {
    for (i <- 1 until n + 1) {
      i match {
        case x if (x % 15 == 0) => println("typesafe")
        case x if x % 5 == 0 => println("type")
        case x if x % 5 == 0 => println("safe")
        case x => println(x)
      }
    }
  }


  def chapter3_04(n: Int, interval: Int) = {
    for (i <- 1 until n + 1) {
      if (i % 5 == 0) println(i)
      else print(i + ",")
    }
  }

  def chapter3_02(d: Double) = if (d > 0) "greater" else if (d < 0) "less" else "same"

  def chapter3_01(s: String): String = {
    s match {
      case "" => "n/a"
      case _ => s
    }
  }


  def chater7_05(prop: String) = {
    util.Try(System.getProperty(prop)).toOption match {
      case Some(null) => s"无法获取 '$prop' 属性"
      case Some(a) => Some(a)
      case _ => s"输入属性不能为空"
    }
  }


  def chater7_04(a: String, b: String) = {
    (toDouble(a), toDouble(b)) match {
      case (Some(a1), Some(b1)) => Some(a1 * b1)
      case _ => None
    }

  }

  def toDouble(a: String) = util.Try(a.toDouble).toOption

  def chater7_03(path: String) = {
    val files = new File(path).listFiles().map(_.getName).filterNot(_ startsWith ".")
    val fileLookup = files.groupBy(_.head.toLower).toList.sortBy(_._1)
    for {(c, l) <- fileLookup} {
      println(s"'$c' has ${l.size} files")
    }

  }

  def chater7_02(path: String) = {
    val files = new File(path).listFiles()
    //    val fileAfter = files.filter(!_.getName.startsWith(".")).map(_.getName).mkString(",")
    val fileAfter = files.filterNot(_.getName startsWith ".").map(_.getName).mkString(";")
    fileAfter foreach print
  }

  def chater7_01_a(x: Int, fina: Buffer[Int]): List[Int] = {
    if (fina.size >= x) {
      println("recursive is over " + x)
    }
    else {
      fina += (fina(fina.size - 2) + fina(fina.size - 1))
      chater7_01_a(x, fina)
    }

    fina.toList
  }


  def chater7_01_a_builder(x: Int): List[Int] = {
    val b = List.newBuilder[Int]
    b ++= List(1, 1)
    b.result()
    while (b.result().size < x) {
      b += (b.result()(b.result().size - 2) + b.result()(b.result().size - 1))
    }
    b.result()
  }

  def chater7_01_c(a: Long, b: Long): Stream[Long] = {

    Stream.cons(a, chater7_01_c(b, a + b))
  }

  def chater7_01_c_nextFib(i: Int): Option[Long] = {
    val start = chater7_01_c(1, 1)
    val preceeding = start.takeWhile(_ <= i).toList
    if (preceeding.last == i)
      Some(preceeding.takeRight(2).sum)
    else
      None
  }

  /**
    * 从 OpenWeatherMap API 读取天气预报
    *
    * @param url
    * @return
    */
  def chapter6_07(url: String): List[String] = {
    Source.fromURL(url).getLines().toList
  }

  def chapter6_06_partion(strs: List[String]): (List[String], List[String]) = {
    strs.partition(s => s == s.reverse)
  }

  def chapter6_05_reverse[A](src: List[A], dest: List[A] = Nil): List[A] = {
    if (src == Nil) dest else src.reverse
  }

  def chapter6_05_reverse_recursion[A](src: List[A], dest: List[A] = Nil): List[A] = {
    if (src == Nil) dest else chapter6_05_reverse_recursion(src.tail, src.head :: dest)
  }

  def chapter6_04(names: List[String]): String = {
    names.sortBy(0 - _.size).head
  }

  def chapter6_04_fold(names: List[String]): String = {
    names.fold("")((a, b) => if (a.size > b.size) a else b)
  }

  def chapter6_04_reduce(names: List[String]): String = {
    names.reduce((a, b) => if (a.size > b.size) a else b)
  }

  def chapter6_04_greatest[A](names: List[A], max: (A, A) => A): A = {
    names.reduce((a, b) => max(a, b))
  }

  def chapter6_03_first[A](items: List[A], count: Int): List[A] = {
    items take count
  }

  def chapter6_03_firstByFoldleft[A](items: List[A], count: Int): List[A] = {
    items.foldLeft[List[A]](Nil) { (a: List[A], i: A) =>
      if (a.size >= count) a else i :: a
    }.reverse
  }

  def chapter6_03_fisrtByTail[A](items: List[A], count: Int): List[A] = {
    if (count > 0 && items.tail != Nil)
      items.head :: chapter6_03_fisrtByTail(items.tail, count - 1)
    else Nil
  }

  def chapter6_02_factor(num: Int) = {
    2 to (num / 2) filter (num % _ == 0)
  }

  def chapter6_02_uniqueFactor(l: Seq[Int]) = l flatten chapter6_02_factor

  def chapter6_02_uniqueFactorByFlatmap(l: Seq[Int]) = l flatMap chapter6_02_factor

  def chapter6_02_uniqueFactorByFor(l: Seq[Int]) = {
    for (e <- l)
      yield chapter6_02_factor(e)

  }

  def chapter6_01(): Unit = {

    for (i <- 0L to 9L; j = i * 2 + 1) {
      print(j + " ")
    }
    println()

    // for循环创建前20个Long奇数的列表
    val oddsFromFor = {
      for (i <- 0L to 9L; j = i * 2 + 1)
        yield j
    }
    println(oddsFromFor)
    oddsFromFor.foreach(odd => print(odd + " "))
    println()

    val oddsFromFilter = {
      0L to 20L filter (_ % 2 != 0)
    }
    println(oddsFromFilter)

    val oddsFromMap = {
      0L to 9L map (_ * 2 + 1)
    }
    println("oddsFromMap : " + oddsFromMap)

  }

  def testRegex(regex: String, str: String): Unit = {
    val pattern = regex.r
    for (s <- pattern.findAllIn(str)) {
      println("match " + pattern + " is : " + s)
    }
    //    match [0-9]+ is : 1128
    //    match [0-9]+ is : 0

    println("match " + pattern + " firstly is : " + pattern.findFirstIn(str))
    //    match [0-9]+ firstly is : Some(128)

    val numItempattern = """([0-9]+) ([a-z]+)""".r
    val numItempattern(num, item) = "20 wangwu"
    for (numItempattern(num, item) <- numItempattern.findAllIn("20 wangwu, 30 lisi, eeee xxx")) {
      println(num + "," + item)
    }
  }

  def testBashShell(command: String): Unit = {
    command !
  }

  def testFor(): Unit = {
    val common = Array(1, 2, 3, 4)
    // 遍历数组
    for (i <- 0 until common.length)
      println(i + " : " + common(i))

    println("=======================")
    // 每两个元素一跳
    for (i <- 0 until(common.length, 2))
      println(i + " : " + common(i))

    println("=======================")
    // 倒序遍历
    for (i <- (0 until (common.length)).reverse)
      println(i + " : " + common(i))

    println("=======================")
    // 不需要数组下标，直接访问数组
    for (elem <- common)
      println(elem)

    println("=================")
    for (i <- 0 until (10)) {
      print(i + " ")
    }

  }

  def testWhile(n: Int): Unit = {
    var m = n
    while (m > 0) {
      println(m)
      m -= 1
    }
  }

  def testIf(n: Int): Unit = {
    val s = if (n > 0) 1 else -1 // 可以将if/else的值赋给变量
    println(s)

    val x = if (n > 0) "postive" else -1 // 两个分支类型的公共超类为Any
    println(x)

    val u = if (n > 0) 1 else () // 每个表达式都应该有某种值 if ( n > 0) 1 等价于 if ( n > 0) 1 else ()
    println(u)
  }

}
