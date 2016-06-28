package nl.quintor.dstibbe.todos.controllers

import com.greencatsoft.angularjs.{Controller, inject, injectable}
import nl.quintor.dstibbe.todos.facade.{MdDialog, MdDialogOptions}
import nl.quintor.dstibbe.todos.model.Todo
import nl.quintor.dstibbe.todos.scopes.TodoScope
import nl.quintor.dstibbe.todos.services.{TodoService, TodoServiceImpl}
import slogging.LazyLogging

import scala.scalajs.js
import scala.scalajs.js.JSConverters._
import scala.scalajs.js.annotation.JSExport

@injectable("TodoCtrl")
class TodoController(todoService: TodoServiceImpl) extends Controller[TodoScope] with LazyLogging {

  logger.trace("[TodoCtrl] init")

  @inject
  var scope: TodoScope = _

  @inject
  var dialog: MdDialog = _


  override def initialize() {
    logger.trace("[TodoCtrl] enter initialize()")
    super.initialize()

    scope.todos = js.Array[Todo]()

    retrieveTodos()
  }

  @JSExport
  def showDialog() = {
    logger.debug("[showDialog] entering")

    val confirm = new MdDialogOptions

    confirm.controller = "AddTodoCtrl"
    confirm.templateUrl = "add-todo.html"
    confirm.clickOutsideToClose = true
    confirm.fullscreen = false
    confirm.locals = Map[String,Object]("todos" -> scope.todos).toJSDictionary

    dialog.show(confirm)
  }

  def retrieveTodos(): Unit = {
    scope.todos = todoService.getTodos().toJSArray
  }
}
