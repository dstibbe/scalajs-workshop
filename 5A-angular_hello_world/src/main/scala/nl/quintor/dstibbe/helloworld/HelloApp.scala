package nl.quintor.dstibbe.helloworld

import com.greencatsoft.angularjs.{Angular, Config}
import nl.quintor.dstibbe.helloworld.controllers.HelloController

import scala.scalajs.js.JSApp


object HelloApp extends JSApp with Config {

  println("[myHelloApp] initialize")

  override def main() = {
    println("[myHelloApp] enter main()")
    val myModule = Angular.module("myHelloApp")

    myModule.controller[HelloController]
    myModule.config(this)
  }
}
