package nl.quintor.dstibbe.helloworld.controllers

import com.greencatsoft.angularjs.{Controller, inject, injectable}
import nl.quintor.dstibbe.helloworld.scopes.HelloScope

import scala.scalajs.js.annotation.JSExport

@injectable("HelloCtrl")
class HelloController extends Controller[HelloScope] {


  println("[HelloCtrl] initialize")

  @inject
  var scope: HelloScope = _


  override def initialize() {
    println("[HelloCtrl] enter initialize()")
    super.initialize()

    scope.submittedMessage = "Nothing submitted"
  }


  @JSExport
  def submit() = {
    // submit me
  }


}
