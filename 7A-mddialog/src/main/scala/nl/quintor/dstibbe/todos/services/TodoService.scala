package nl.quintor.dstibbe.todos.services

import nl.quintor.dstibbe.todos.model.Todo

import scala.concurrent.Future

trait TodoService {
  def getTodos(): Seq[Todo]
}
