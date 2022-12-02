package view

import view.updates.{DefaultUpdate, ParameterlessViewUpdate, ViewUpdate}

object StandardGameMenuView extends DefaultUpdate:
  case class NewQuizUpdate[T](override val updateParameter: Option[T]) extends ViewUpdate(updateParameter)
  case class AnswerFeedbackUpdate[T](override val updateParameter: Option[T]) extends ViewUpdate(updateParameter)
  case object TimeExpiredUpdate extends ParameterlessViewUpdate
