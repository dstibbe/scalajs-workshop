package nl.quintor.dstibbe.helloworld.controllers

import com.greencatsoft.angularjs.{Controller, inject, injectable}
import nl.quintor.dstibbe.helloworld.scopes.HelloScope

import scala.scalajs.js.annotation.JSExport

@injectable("HelloCtrl")
class HelloController extends Controller[HelloScope] {


  println("HelloCtrl initialize")

  @inject
  var scope: HelloScope = _


  override def initialize() {
    super.initialize()
    println("HelloCtrl.initialize() method")


    scope.submittedMessage = "Nothing submitted"
  }


  @JSExport
  def submit() = {
    println("HelloCtrl.submit() method")

    scope.submittedMessage = scope.submittingMessage
  }


}
