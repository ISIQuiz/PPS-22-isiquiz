package view

import model.Quiz.Quiz
import model.SavedCourse
import view.updates.{DefaultUpdate, ParameterlessViewUpdate, ViewUpdate}

object EditQuizMenuView extends DefaultUpdate:

  case class CourseListUpdate(override val updateParameter: Option[List[SavedCourse]]) extends ViewUpdate(updateParameter)

  case object AskCourseUpdate extends ParameterlessViewUpdate

  case class QuizListUpdate(override val updateParameter: Option[List[Quiz]]) extends ViewUpdate(updateParameter)

  case object AskQuizUpdate extends ParameterlessViewUpdate

  case class QuizPrintUpdate(override val updateParameter: Option[Quiz]) extends ViewUpdate(updateParameter)

  case object AskQuizEditUpdate extends ParameterlessViewUpdate
  
  case object QuizEditedUpdate extends ParameterlessViewUpdate