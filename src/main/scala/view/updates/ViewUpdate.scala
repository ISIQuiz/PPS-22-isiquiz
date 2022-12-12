package view.updates

/** Defines a type of update to perform on the graphical interface
 * @tparam T the type of the additional parameter associated with the update
 * @param updateParameter additional parameter needed to perform the update
 */
trait ViewUpdate[+T](val updateParameter: Option[T]):
  def get[T]: T =
    updateParameter.get.asInstanceOf[T]

/** Defines a view update that never needs additional parameters */
abstract class ParameterlessViewUpdate extends ViewUpdate(Option.empty)
