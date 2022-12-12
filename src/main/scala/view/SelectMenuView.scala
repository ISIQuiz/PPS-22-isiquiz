package view

import view.updates.{DefaultUpdate, ParameterlessViewUpdate, ViewUpdate}

object SelectMenuView extends DefaultUpdate:
  case class CourseUpdate[T](override val updateParameter: Option[T]) extends ViewUpdate(updateParameter)
  /** Update for the view when a selected is unplayable in that game mode */
  case object CourseUnplayableUpdate extends ParameterlessViewUpdate