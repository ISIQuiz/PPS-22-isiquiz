package controller.actions

trait BackAction:
  case object Back extends Action[Unit](Option.empty)