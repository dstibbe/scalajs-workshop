package nl.quintor.dstibbe.todos.services

import com.greencatsoft.angularjs.core.HttpService
import com.greencatsoft.angularjs.{Factory, injectable}
import nl.quintor.dstibbe.todos.model.Todo

/**
  * Created by dstibbe on 28-6-2016.
  */
@injectable("TodoService")
class TodoServiceImpl(http: HttpService) extends TodoService {
  override def getTodos(): Seq[Todo] = {
    return Seq(
      Todo("milk"),
      Todo("get richt"),
      Todo("do stuff")
    )
  }
}

@injectable("TodoService")
class TodoServiceFactory(http: HttpService) extends Factory[TodoService] {
  override def apply() = new TodoServiceImpl(http)
}
