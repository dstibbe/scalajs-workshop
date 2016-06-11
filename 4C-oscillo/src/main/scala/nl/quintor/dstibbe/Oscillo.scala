package nl.quintor.dstibbe

import java.lang.Math._

import nl.quintor.dstibbe.domain.GlobalScopeStuff._
import nl.quintor.dstibbe.domain._

import scala.scalajs.js

/**
  * Created by David Stibbe <dstibbe@gmail.com>,
  *   based on ScalaJs Fiddle demo <http://www.scala-js-fiddle.com/>
  */

object Oscillo extends js.JSApp {
  def main(): Unit = {
    val canvas: Canvas = document.getElementById("targetCanvas")
    val context: Context = canvas.getContext("2d")

    val (h, w) = (canvas.height, canvas.width) //Int, Int
    var x = 0.0
    val graphs = Seq[(String, Double => Double)](
      ("red", sin),
      ("green", x => 2 - abs(x % 8 - 4)),
      ("gray", x => 3 * pow(sin(x / 12), 2) * sin(x))
    ).zipWithIndex
    window.setInterval(() => {
      x = (x + 1) % w
      if (x == 0) context.clearRect(0, 0, w, h) //Double, Double, Double, Double
      else for (((color, func), i) <- graphs) {
        val y = func(x / w * 75) * h / 40 + h / 3 * (i + 0.5)
        context.fillStyle = color //String
        context.fillRect(x, y, 3, 3) //Double, Double, Double, Double
      }
    }, 10)
  }
}

