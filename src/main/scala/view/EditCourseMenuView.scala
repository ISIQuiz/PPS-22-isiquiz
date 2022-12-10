package view

import model.SavedCourse.SavedCourse
import view.updates.{DefaultUpdate, ParameterlessViewUpdate, ViewUpdate}

object EditCourseMenuView extends DefaultUpdate:

  case class CourseUpdate(override val updateParameter: Option[List[SavedCourse]]) extends ViewUpdate(updateParameter)

  case object AskCourseUpdate extends ParameterlessViewUpdate
  
  case object AskCourseEditUpdate extends ParameterlessViewUpdate

  case class CoursePrintUpdate(override val updateParameter: Option[SavedCourse]) extends ViewUpdate(updateParameter)
  
  case object CourseEditedUpdate extends ParameterlessViewUpdate