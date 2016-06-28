package nl.quintor.dstibbe.test.todos


import nl.quintor.dstibbe.todos.controllers.TodoController
import nl.quintor.dstibbe.todos.model.Todo
import nl.quintor.dstibbe.todos.scopes.TodoScope
import nl.quintor.dstibbe.todos.services.TodoService
import utest._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.scalajs.js
import scala.scalajs.js.Array
import scala.scalajs.js.JSConverters._
import scala.scalajs.js.annotation.ScalaJSDefined

object TodoControllerTest extends TestSuite {

  def tests = TestSuite {

    'TodoControllerShouldReturnCorrecttodoList {
      var controller = new TodoController(
        new TodoService {
          override def getTodos(): Seq[Todo] = buildExpectedTodos
        }
      )
      controller.scope = (new TodoMockScope).asInstanceOf[TodoScope]
      controller.initialize()

      val todos: Array[Todo] = controller.scope.todos
      assert(buildExpectedTodos.toJSArray.deep == todos.deep)
    }
  }

  def buildExpectedTodos: Seq[Todo] = {
    Array(
      Todo("buy milk"),
      Todo("buy eggs"),
      Todo("repeat")
    )
  }

  @ScalaJSDefined
  class TodoMockScope extends js.Object {
    var todos = Array[Todo]().toJSArray
  }

}
