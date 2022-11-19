package controller.actions

/** Defines a user-executable action
 * @tparam T the type of the parameter associated with the action
 * @param actionParameter an optional parameter associated with the action
 */
trait Action[+T](val actionParameter: Option[T]):
  def get[T]: T =
    actionParameter.get.asInstanceOf[T]

/** Defines a user-executable action that never has any additional parameters */
abstract class ParameterlessAction extends Action(Option.empty)