package nl.quintor.dstibbe.todos.scopes

import com.greencatsoft.angularjs.core.Scope
import nl.quintor.dstibbe.todos.model.Todo

import scala.scalajs.js

@js.native
trait AddTodoScope extends Scope {
  var todo: Todo = js.native
}
