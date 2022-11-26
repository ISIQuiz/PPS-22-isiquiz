package view

import view.updates.{DefaultUpdate, ParameterlessViewUpdate, ViewUpdate}

object SelectMenuView:
  case object DefaultUpdate extends ParameterlessViewUpdate
  case class CourseUpdate[T](override val updateParameter: Option[T]) extends ViewUpdate(updateParameter)