package view

import model.SavedCourse.SavedCourse
import view.updates.{DefaultUpdate, ParameterlessViewUpdate, ViewUpdate}

object AddCourseMenuView extends DefaultUpdate:

  case object AskCourseUpdate extends ParameterlessViewUpdate

  case class CoursePrintUpdate(override val updateParameter: Option[SavedCourse]) extends ViewUpdate(updateParameter)
  
  case object CourseAddedUpdate extends ParameterlessViewUpdate