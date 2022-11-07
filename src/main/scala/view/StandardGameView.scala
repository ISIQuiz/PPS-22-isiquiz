package view

import View.*
import controller.StandardGameController
import model.Answer.AnswerImpl
import model.GameStage.*

object StandardGameView:

  /** StandardGameView define aspects of a general StandardGameView */
  trait StandardGameView extends PageView

  /** A basic implementation of a SelectMenuView  */
  class StandardGameViewImpl extends StandardGameView:

    override val actionsMap: Map[Int, Enumeration] = Map(
      1 -> StandardGameController.AvailableActions.Back
    )

    override def draw[T](update: Option[T]): String =
      println("Standard quiz:")
      update.get.asInstanceOf[GameStage].courseInGame.foreach(savedCourse => savedCourse.quizList.foreach(savedQuiz =>
        println("1) Termina quiz");
        println(s"${savedQuiz.text}");
        savedQuiz.answerList.zipWithIndex.foreach(answer => println(answer._2 + 1 + ") " + answer._1.text))
      ))
      "StandardGame"
