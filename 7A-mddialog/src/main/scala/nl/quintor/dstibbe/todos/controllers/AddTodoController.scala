package nl.quintor.dstibbe.todos.controllers

import com.greencatsoft.angularjs.{Controller, injectable}
import nl.quintor.dstibbe.todos.facade.MdDialog
import nl.quintor.dstibbe.todos.model.Todo
import nl.quintor.dstibbe.todos.scopes.AddTodoScope
import nl.quintor.dstibbe.todos.services.TodoServiceImpl
import slogging.LazyLogging

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport


@injectable("AddTodoCtrl")
class AddTodoController(val scope: AddTodoScope, todoService: TodoServiceImpl, dialog: MdDialog, todos: js.Array[Todo]) extends Controller[AddTodoScope] with LazyLogging {

  override def initialize() {
    logger.debug("[initialize] entering")
    super.initialize()

  }

  @JSExport
  def save() = {

  }

  @JSExport
  def cancel() = {
    close()
  }

  def close() = {
    dialog.hide()
  }
}
