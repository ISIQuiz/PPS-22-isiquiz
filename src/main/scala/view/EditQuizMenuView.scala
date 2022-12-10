package view

import model.Quiz.Quiz
import model.SavedCourse
import view.updates.{DefaultUpdate, ParameterlessViewUpdate, ViewUpdate}

object EditQuizMenuView extends DefaultUpdate:

  /** Update for the view containing the list of current curses */
  case class CourseListUpdate(override val updateParameter: Option[List[SavedCourse]]) extends ViewUpdate(updateParameter)

  /** Update for the view asking to select the course, used in Terminal View  */
  case object AskCourseSelectUpdate extends ParameterlessViewUpdate

  /** Update for the view containing the list of current quizzes in the course selected */
  case class QuizListUpdate(override val updateParameter: Option[List[Quiz]]) extends ViewUpdate(updateParameter)

  /** Update for the view asking to select the quiz, used in Terminal View  */
  case object AskQuizSelectUpdate extends ParameterlessViewUpdate

  /** Update for the view containing the current Quiz, used in Terminal View  */
  case class QuizPrintUpdate(override val updateParameter: Option[Quiz]) extends ViewUpdate(updateParameter)

  /** Update for the view asking to edit the quiz, used in Terminal View */
  case object AskQuizEditUpdate extends ParameterlessViewUpdate

  /** Update for the view for the feedback quiz edited */
  case object QuizEditedUpdate extends ParameterlessViewUpdate

  /** Update for the view for the feedback quiz deleted */
  case object QuizDeletedUpdate extends ParameterlessViewUpdate