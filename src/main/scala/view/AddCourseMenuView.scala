package view

import model.SavedCourse.SavedCourse
import view.updates.{DefaultUpdate, ParameterlessViewUpdate, ViewUpdate}

object AddCourseMenuView extends DefaultUpdate:

  /** Update for the view containing the current Course */
  case class CoursePrintUpdate(override val updateParameter: Option[SavedCourse]) extends ViewUpdate(updateParameter)

  /** Update for the view asking to insert the course, used in Terminal View */
  case object AskCourseUpdate extends ParameterlessViewUpdate

  /** Update for the view for the feedback course inserted */
  case object CourseAddedUpdate extends ParameterlessViewUpdate
