package nl.quintor.dstibbe.todos

import com.greencatsoft.angularjs.{Angular, Config}
import nl.quintor.dstibbe.todos.controllers.TodoController
import slogging.{ConsoleLoggerFactory, LazyLogging, LogLevel, LoggerConfig}

import scala.scalajs.js.JSApp


object TodoApp extends JSApp with Config with LazyLogging{

  logger.trace("[theTodoApp] init")

  LoggerConfig.factory = ConsoleLoggerFactory()
  LoggerConfig.level = LogLevel.TRACE

  override def main() = {
    logger.trace("[theTodoApp] enter main()")
    val myModule = Angular.module("theTodoApp")

    myModule.controller[TodoController]
    myModule.config(this)
  }
}
