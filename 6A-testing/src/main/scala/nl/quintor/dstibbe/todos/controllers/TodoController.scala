package nl.quintor.dstibbe.todos.controllers

import com.greencatsoft.angularjs.{Controller, inject, injectable}
import nl.quintor.dstibbe.todos.model.Todo
import nl.quintor.dstibbe.todos.scopes.TodoScope
import slogging.{LazyLogging, Logger}

import scala.scalajs.js
import scala.scalajs.js.JSConverters._

@injectable("TodoCtrl")
class TodoController extends Controller[TodoScope] with LazyLogging {

  logger.trace("[TodoCtrl] init")

  @inject
  var scope: TodoScope = _


  override def initialize() {
    logger.trace("[TodoCtrl] enter initialize()")
    super.initialize()

    scope.todos = initializeTodos()
  }


  def initializeTodos(): js.Array[Todo] = {
    Array(
      Todo("buy milk"),
      Todo("buy eggs"),
      Todo("repeat")
    ).toJSArray
  }
}
