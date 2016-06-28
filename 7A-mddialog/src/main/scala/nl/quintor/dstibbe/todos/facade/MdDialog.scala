package nl.quintor.dstibbe.todos.facade

import com.greencatsoft.angularjs.core.Promise
import com.greencatsoft.angularjs.injectable

import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined

/**
  * Created by dstibbe on 1-4-2016.
  */
@js.native
@injectable("$mdDialog")
trait MdDialog extends js.Object {
  def alert(): MdDialogPreset = js.native

  def hide(): Promise[String] = js.native

  def show(preset: MdDialogPreset): Promise[String] = js.native

  def show(options: MdDialogOptions): Promise[String] = js.native
}

@ScalaJSDefined
class MdDialogOptions extends js.Object {
  var controller: String = _
  var templateUrl: String = _
  var clickOutsideToClose: Boolean = _
  var fullscreen: Boolean = _
  var locals: js.Dictionary[Object] = _
}

@js.native
trait MdDialogPreset extends js.Object {
  /**
    * - $mdDialogPreset#title(string) - Sets the alert title.
    * - $mdDialogPreset#textContent(string) - Sets the alert message.
    * - $mdDialogPreset#htmlContent(string) - Sets the alert message as HTML. Requires ngSanitize
    * module to be loaded. HTML is not run through Angular's compiler.
    * - $mdDialogPreset#ok(string) - Sets the alert "Okay" button text.
    * - $mdDialogPreset#theme(string) - Sets the theme of the alert dialog.
    * - $mdDialogPreset#targetEvent(DOMClickEvent=) - A click's event object. When passed in as an option,
    * the location of the click will be used as the starting point for the opening animation
    * of the the dialog.
    *
    */

  def title(title: String): this.type = js.native

  def textContent(content: String): this.type = js.native

  def ok(msg: String): this.type = js.native
}


