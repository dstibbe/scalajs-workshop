package nl.quintor.dstibbe.todos.controllers

import com.greencatsoft.angularjs.{Controller, inject, injectable}
import nl.quintor.dstibbe.todos.model.Todo
import nl.quintor.dstibbe.todos.scopes.TodoScope
import nl.quintor.dstibbe.todos.services.{TodoService, TodoServiceImpl}
import slogging.LazyLogging

import scala.scalajs.js
import scala.scalajs.js.JSConverters._

@injectable("TodoCtrl")
class TodoController(todoService: TodoServiceImpl) extends Controller[TodoScope] with LazyLogging {

  logger.trace("[TodoCtrl] init")

  @inject
  var scope: TodoScope = _


  override def initialize() {
    logger.trace("[TodoCtrl] enter initialize()")
    super.initialize()

    scope.todos = js.Array[Todo]()

    retrieveTodos()
  }

  def retrieveTodos(): Unit = {
    scope.todos = todoService.getTodos().toJSArray
  }
}
