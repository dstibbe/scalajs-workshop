package nl.quintor.dstibbe.todos

import com.greencatsoft.angularjs.{Angular, Config}
import nl.quintor.dstibbe.todos.controllers.{AddTodoController, TodoController}
import nl.quintor.dstibbe.todos.services.TodoServiceFactory
import slogging.{ConsoleLoggerFactory, LazyLogging, LogLevel, LoggerConfig}

import scala.scalajs.js.JSApp


object TodoApp extends JSApp with Config with LazyLogging{
  println("STARTED")
  logger.trace("[theTodoApp] init")

  LoggerConfig.factory = ConsoleLoggerFactory()
  LoggerConfig.level = LogLevel.TRACE

  override def main() = {
    logger.trace("[theTodoApp] enter main()")
    val myModule = Angular.module("theTodoApp", Seq("ngMaterial"))

    myModule.controller[TodoController]
    myModule.controller[AddTodoController]
    myModule.factory[TodoServiceFactory]
    myModule.config(this)
  }
}
