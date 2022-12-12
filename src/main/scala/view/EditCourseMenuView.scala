package view

import model.SavedCourse.SavedCourse
import view.updates.{DefaultUpdate, ParameterlessViewUpdate, ViewUpdate}

object EditCourseMenuView extends DefaultUpdate:

  /** Update for the view containing the list of current curses */
  case class CourseListUpdate(override val updateParameter: Option[List[SavedCourse]]) extends ViewUpdate(updateParameter)

  /** Update for the view asking to select the course, used in Terminal View  */
  case object AskCourseSelectUpdate extends ParameterlessViewUpdate

  /** Update for the view containing the current Course, used in Terminal View  */
  case class CoursePrintUpdate(override val updateParameter: Option[SavedCourse]) extends ViewUpdate(updateParameter)

  /** Update for the view asking to edit the course, used in Terminal View  */
  case object AskCourseEditUpdate extends ParameterlessViewUpdate

  /** Update for the view for the feedback course edited */
  case object CourseEditedUpdate extends ParameterlessViewUpdate

  /** Update for the view for the feedback course deleted */
  case object CourseDeletedUpdate extends ParameterlessViewUpdate
