package nl.quintor.dstibbe.todos.controllers

import com.greencatsoft.angularjs.{Controller, injectable}
import nl.quintor.dstibbe.todos.facade.MdDialog
import nl.quintor.dstibbe.todos.model.Todo
import nl.quintor.dstibbe.todos.scopes.AddTodoScope
import slogging.LazyLogging

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport


@injectable("AddTodoCtrl")
class AddTodoController(val scope: AddTodoScope, dialog: MdDialog, todos: js.Array[Todo]) extends Controller[AddTodoScope] with LazyLogging {

  override def initialize() {
    super.initialize()
    logger.debug("[initialize] entering")
  }

  @JSExport
  def save() = {
    logger.debug("[save] entering")
   // save stuff
    close()
  }

  @JSExport
  def cancel() = {
    close()
  }

  def close() = {
    dialog.hide()
  }
}
