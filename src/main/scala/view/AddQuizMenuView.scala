package view

import model.SavedCourse
import view.updates.{DefaultUpdate, ParameterlessViewUpdate, ViewUpdate}

object AddQuizMenuView extends DefaultUpdate:

  case class AskCoursePrint[T](override val updateParameter: Option[T]) extends ViewUpdate(updateParameter)
  
  case class QuizPrint[T](override val updateParameter: Option[T]) extends ViewUpdate(updateParameter)
  
  case object AskQuizPrint extends ParameterlessViewUpdate
  
  case object QuizAdded extends ParameterlessViewUpdate

  case class CourseUpdate[T](override val updateParameter: Option[List[SavedCourse]]) extends ViewUpdate(updateParameter)