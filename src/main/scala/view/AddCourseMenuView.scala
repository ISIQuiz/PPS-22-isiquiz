package view

import model.SavedCourse
import view.updates.{DefaultUpdate, ParameterlessViewUpdate, ViewUpdate}

object AddCourseMenuView extends DefaultUpdate:

  case class CoursePrint(override val updateParameter: Option[SavedCourse]) extends ViewUpdate(updateParameter)

  case object AskCoursePrint extends ParameterlessViewUpdate