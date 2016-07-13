package com.cstor.spark.test

import java.util.Date

/**
  * Created on 2016/7/13
  *
  * @author feng.wei
  */

abstract class MediaFormat

class DvdMediaFormat extends MediaFormat

class BluRayMediaFormat extends MediaFormat

class USBMediaFormat extends MediaFormat

class CartridgeMediaFormat extends MediaFormat

abstract class VideoResolution(pixels: Int)

class HD extends VideoResolution(1280 * 720)

class FHD extends VideoResolution(1920 * 1080)

class QHD extends VideoResolution(2560 * 1440)

class UHD4K extends VideoResolution(3840 * 2160)


class Displayer(val brand: String, val model: String, val debut: java.util.Date, val wifiType: Option[String],
                val mediaFormat: List[MediaFormat], val resolution: VideoResolution) {


  override def toString = s"Displayer($brand, $model), max video resolution = ${resolution.getClass.getName}"
}

class DisplayerLibrary {
  def strToDate(s: String): Date = java.text.DateFormat.getInstance.parse(s)

  val chanduOne = new Displayer("Chandu", "One", strToDate("2/12/2007 0:00 AM"), Some("a/b"),
    List(new CartridgeMediaFormat), new HD)

  val aquaTopia = new Displayer("Aqua", "Topia", strToDate("5/2/2012 0:00 AM"), Some("a/b/g"),
    List(new DvdMediaFormat), new HD)

  val oonaSeven = new Displayer("Oona", "Seven", strToDate("3/3/2014 0:00 AM"), Some("b/g/n"),
    List(new BluRayMediaFormat, new DvdMediaFormat), new FHD)

  val leoChallenge = new Displayer("Leonardo", "Challenge", strToDate("12/12/2014 0:00 AM"), Some("g/n"),
    List(new USBMediaFormat), new UHD4K)
}

class Game(val name: String, val maker: String, val displayers: List[Displayer]) {
  def isSupported(displayer: Displayer) = displayers.contains(displayer)

  override def toString = s"Game($name, $maker)"
}

class GameShop {
  val consoles = new DisplayerLibrary()

  val games = List(
    new Game("Elevator Action", "Taito", List(consoles.chanduOne)),
    new Game("Mappy", "Namco", List(consoles.chanduOne, consoles.aquaTopia)),
    new Game("StreetFigher", "Capcom", List(consoles.oonaSeven, consoles.leoChallenge))
  )

  def consoleToGames: Map[Displayer, List[Game]] = {
    val consoleToGames1: List[(Displayer, Game)] = games flatMap (g => g.displayers.map(d => d -> g))
    val consoleToGames2: Map[Displayer, List[(Displayer, Game)]] = consoleToGames1 groupBy (_._1)
    val consoleToGames3: Map[Displayer, List[Game]] = consoleToGames2 mapValues (_ map (_._2))
    consoleToGames3
  }

  def reportGames = {
    games sortBy (g => s"${g.maker}_${g.name}") foreach {
      game =>
        val gameInfo = game.displayers.map(d => s"${d.brand}  ${d.model}").mkString(",")
        println(s"${game.name} by ${game.maker} for ${gameInfo}")
    }
  }

}
