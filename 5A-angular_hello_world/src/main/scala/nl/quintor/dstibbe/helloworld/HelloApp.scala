package nl.quintor.dstibbe.helloworld

import com.greencatsoft.angularjs.{Angular, Config}
import nl.quintor.dstibbe.helloworld.controllers.HelloController

import scala.scalajs.js.JSApp


object HelloApp extends JSApp with Config {

  println("MainApp init")

  override def main() = {
    println("MainApp.main() method")
    val myModule = Angular.module("myHelloApp")

    myModule.controller[HelloController]
    myModule.config(this)
  }
}
