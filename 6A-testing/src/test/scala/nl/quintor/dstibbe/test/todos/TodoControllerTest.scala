package nl.quintor.dstibbe.test.todos


import nl.quintor.dstibbe.todos.controllers.TodoController
import nl.quintor.dstibbe.todos.model.Todo
import nl.quintor.dstibbe.todos.scopes.TodoScope
import utest._

import scala.scalajs.js
import scala.scalajs.js.Array
import scala.scalajs.js.JSConverters._
import scala.scalajs.js.annotation.ScalaJSDefined

object TodoControllerTest extends TestSuite {

  def tests = TestSuite{

    'TodoControllerShouldReturnCorrecttodoList{
      var controller = new TodoController
      controller.scope = (new TodoMockScope).asInstanceOf[TodoScope]
      controller.initialize()

      val expectedTodos = Array(
        Todo("buy milk"),
        Todo("buy eggs"),
        Todo("repeat")
      ).toJSArray

      val todos: Array[Todo] = controller.scope.todos
      assert(expectedTodos.deep == todos.deep)
    }
  }

  @ScalaJSDefined
  class TodoMockScope extends js.Object  {
    var todos = Array[Todo]().toJSArray
  }

}
