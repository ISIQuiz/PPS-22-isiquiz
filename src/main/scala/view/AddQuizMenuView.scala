package view

import model.Quiz.Quiz
import model.SavedCourse.SavedCourse
import view.updates.{DefaultUpdate, ParameterlessViewUpdate, ViewUpdate}

object AddQuizMenuView extends DefaultUpdate:

  /** Update for the view containing the list of current curses  */
  case class CourseListUpdate(override val updateParameter: Option[List[SavedCourse]]) extends ViewUpdate(updateParameter)

  /** Update for the view asking to insert the course, used in Terminal View  */
  case object AskCourseSelectUpdate extends ParameterlessViewUpdate

  /** Update for the view containing the current Quiz  */
  case class QuizPrintUpdate(override val updateParameter: Option[Quiz]) extends ViewUpdate(updateParameter)

  /** Update for the view asking to insert the quiz, used in Terminal View  */
  case object AskQuizUpdate extends ParameterlessViewUpdate

  /** Update for the view for the feedback quiz inserted */
  case object QuizAddedUpdate extends ParameterlessViewUpdate
