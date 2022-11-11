package view

import View.*
import controller.StandardGameController
import model.GameStage.*

object StandardGameView:

  /** StandardGameView define aspects of a general StandardGameView */
  trait StandardGameView extends PageView
    enum UpdateType extends Enumeration:
      case NewQuiz
      case AnswerFeedback

  /** A basic implementation of a SelectMenuView  */
  class StandardGameViewImpl extends StandardGameView:

    override val actionsMap: Map[Int, Enumeration] = Map(
      0 -> StandardGameController.AvailableActions.Back,
      1 -> StandardGameController.AvailableActions.SelectAnswer,
      2 -> StandardGameController.AvailableActions.SelectAnswer,
      3 -> StandardGameController.AvailableActions.SelectAnswer,
      4 -> StandardGameController.AvailableActions.SelectAnswer
    )

    override def draw[T](update: UIUpdate[T]): String = update.updateType match
      case UpdateType.NewQuiz =>
        println("Standard quiz:")
        if (update.updateValue.isDefined){
          update.updateValue.get.asInstanceOf[GameStage].courseInGame.foreach(savedCourse => savedCourse.quizList.foreach(quiz =>
            println("0) Termina quiz");
            println(quiz);
          ))
        }
        "StandardGame"
      case UpdateType.AnswerFeedback =>
        println(update)
        "StandardGameUpdate"
