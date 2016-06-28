package nl.quintor.dstibbe.todos.services

import com.greencatsoft.angularjs.Service
import nl.quintor.dstibbe.todos.model.Todo

import scala.concurrent.Future

trait TodoService extends Service{
  def getTodos(): Seq[Todo]
}
