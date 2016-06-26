package nl.quintor.dstibbe.helloworld.scopes

import com.greencatsoft.angularjs.core.Scope

import scala.scalajs.js

@js.native
trait HelloScope extends Scope {
  var directMessage: String = js.native
  var submittingMessage: String = js.native
  var submittedMessage: String = js.native
}
