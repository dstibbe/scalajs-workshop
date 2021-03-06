package nl.quintor.dstibbe.todos.controllers

import com.greencatsoft.angularjs.{Controller, inject, injectable}
import nl.quintor.dstibbe.todos.model.Todo
import nl.quintor.dstibbe.todos.scopes.TodoScope

import scala.scalajs.js
import scala.scalajs.js.JSConverters._

@injectable("TodoCtrl")
class TodoController extends Controller[TodoScope] {


  println("[TodoCtrl] initialize")

  @inject
  var scope: TodoScope = _


  override def initialize() {
    println("[TodoCtrl] enter initialize()")
    super.initialize()
  }
}
