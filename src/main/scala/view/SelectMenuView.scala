package view

import view.updates.{DefaultUpdate, ParameterlessViewUpdate, ViewUpdate}

object SelectMenuView extends DefaultUpdate:
  case class CourseUpdate[T](override val updateParameter: Option[T]) extends ViewUpdate(updateParameter)