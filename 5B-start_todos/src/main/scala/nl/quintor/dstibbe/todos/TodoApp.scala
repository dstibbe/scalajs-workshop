package nl.quintor.dstibbe.todos

import com.greencatsoft.angularjs.{Angular, Config}
import nl.quintor.dstibbe.todos.controllers.TodoController

import scala.scalajs.js.JSApp


object TodoApp extends JSApp with Config {

  println("[theTodoApp] initialize")

  override def main() = {
    println("[theTodoApp] enter main()")
    val myModule = Angular.module("theTodoApp")

    myModule.controller[TodoController]
    myModule.config(this)
  }
}
