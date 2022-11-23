package view

import View.*
import view.updates.{ParameterlessViewUpdate, ViewUpdate}
import controller.StandardGameController
import controller.StandardGameController.*
import controller.actions.{Action, ParameterlessAction}
import model.GameStage
import scala.collection.mutable.Map

object StandardGameView:

  case object DefaultUpdate extends ParameterlessViewUpdate
  case class NewQuizUpdate[T](override val updateParameter: Option[T]) extends ViewUpdate[T](updateParameter)
  case class AnswerFeedbackUpdate[T](override val updateParameter: Option[T]) extends ViewUpdate[T](updateParameter)

  /** StandardGameView define aspects of a general StandardGameView */
  trait StandardGameView extends PageView

  /** A basic implementation of a SelectMenuView  */
  class StandardGameViewImpl extends StandardGameView:

    override val actionsMap: Map[String, Action[Any]] = Map(
      "0" -> Back.asInstanceOf[Action[Any]]
    )

    override def updateUI[T](update: ViewUpdate[Any]): Unit = update match
      case DefaultUpdate =>
        println("Standard quiz:")
        println("0) Termina quiz")
        handleInput()
      case NewQuizUpdate(updateParameter: Option[T]) =>
        if updateParameter.isDefined then
          val quizInGame = updateParameter.get.asInstanceOf[GameStage].quizInGame
          val printAnswers = quizInGame.answers.map(answer =>
            s"${quizInGame.answers.indexOf(answer) + 1}) ${answer.text}")
          println(quizInGame.quiz.question)
          quizInGame.answers.foreach(answer => actionsMap += ((quizInGame.answers.indexOf(answer) + 1).toString -> SelectAnswer(Option(quizInGame.answers.indexOf(answer) + 1)).asInstanceOf[Action[Any]]))
          println("Seleziona una risposta:")
          printAnswers.foreach(answer => println(answer))
      case AnswerFeedbackUpdate(updateParameter: Option[T]) =>
        println(updateParameter.get)
