package quintor.exercise2

import org.scalajs.dom.document

import scala.scalajs.js.JSApp

/**
  * Created by David Stibbe <dstibbe@gmail.com>
  */

object MainApp extends JSApp {
  override def main() = {
    val random = scala.util.Random.nextInt(1000)
    val msg = s"Hello World. Now for a random number: $random"

    document.getElementById("fillme").innerHTML = msg
    println("I fllled the div with hello world")
  }
}
