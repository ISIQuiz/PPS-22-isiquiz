package view.updates

import view.updates.ParameterlessViewUpdate

trait DefaultUpdate:
  case object DefaultUpdate extends ParameterlessViewUpdate
