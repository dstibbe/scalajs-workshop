package nl.quintor.dstibbe.todos.scopes

import com.greencatsoft.angularjs.core.Scope
import nl.quintor.dstibbe.todos.model.Todo

import scala.scalajs.js

@js.native
trait TodoScope extends Scope {
  var todos: js.Array[Todo] = js.native
}
