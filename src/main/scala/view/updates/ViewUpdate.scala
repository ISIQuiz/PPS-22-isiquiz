package view.updates

trait ViewUpdate[T](val updateParameter: Option[T])

abstract class ParameterlessViewUpdate extends ViewUpdate(Option.empty)