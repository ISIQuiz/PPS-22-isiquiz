package view

import view.updates.{DefaultUpdate, ViewUpdate, ParameterlessViewUpdate}

object AddQuizMenuView extends DefaultUpdate:

  case class AskCoursePrint[T](override val updateParameter: Option[T]) extends ViewUpdate(updateParameter)
  
  case class QuizPrint[T](override val updateParameter: Option[T]) extends ViewUpdate(updateParameter)
  
  case object AskQuizPrint extends ParameterlessViewUpdate
  
  case object QuizAdded extends ParameterlessViewUpdate