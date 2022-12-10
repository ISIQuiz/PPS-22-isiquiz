package view

import model.Quiz.Quiz
import model.SavedCourse.SavedCourse
import view.updates.{DefaultUpdate, ParameterlessViewUpdate, ViewUpdate}

object AddQuizMenuView extends DefaultUpdate:

  case class CourseUpdate(override val updateParameter: Option[List[SavedCourse]]) extends ViewUpdate(updateParameter)

  case object AskCourseUpdate extends ParameterlessViewUpdate

  case object AskQuizUpdate extends ParameterlessViewUpdate

  case class QuizPrintUpdate(override val updateParameter: Option[Quiz]) extends ViewUpdate(updateParameter)

  case object QuizAddedUpdate extends ParameterlessViewUpdate
