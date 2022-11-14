package controller.actions

trait Action[T](val actionParameter: Option[T])

abstract class ParameterlessAction extends Action(Option.empty)