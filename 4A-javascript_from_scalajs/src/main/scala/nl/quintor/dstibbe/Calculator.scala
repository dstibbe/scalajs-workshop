package nl.quintor.dstibbe

import scala.scalajs.js.annotation.JSExport

/**
  * Created by David Stibbe <dstibbe@gmail.com>
  */

@JSExport
class Calculator() {

  @JSExport
  def calculate() = {
    println("[scalajs] enter calculate()")

    println("[scalajs] reading input value: " + Input.value)
    val result = Input.value * 5.0

    println("[scalajs] setting result value: " + Result.value)
    Result.value = result
  }
}

