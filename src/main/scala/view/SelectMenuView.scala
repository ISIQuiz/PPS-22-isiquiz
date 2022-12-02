package view

import view.updates.{DefaultUpdate, ViewUpdate}

object SelectMenuView extends DefaultUpdate:
  case class CourseUpdate[T](override val updateParameter: Option[T]) extends ViewUpdate(updateParameter)