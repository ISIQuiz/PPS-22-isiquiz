package controller

import controller.actions.{Action, BackAction, ParameterlessAction}
import controller.AppController.*
import model.GameStage
import view.AddCourseMenuView.*
import view.ReviewMenuView.{CurrentReviewUpdate, TotalCorrectAnswersUpdate, TotalPointsUpdate}
import view.updates.DefaultUpdate


/** Companion object of review menu controller containing all the actions can be sent */
object ReviewMenuController extends BackAction :
  /** action to end the game in the controller of the review of a game */
  case object End extends ParameterlessAction
  
/** Controls the behavior of the review page */
class ReviewMenuController(var gameStage: GameStage) extends PageController :

  import ReviewMenuController.*

  override def handle[T](action: Action[T]): Unit = action match
    case End => AppController.handle(MainMenuAction)


  override def nextIteration(): Unit =
    sendUpdate(DefaultUpdate)
    sendUpdate(CurrentReviewUpdate(Option(gameStage.review)))
    sendUpdate(TotalPointsUpdate(Option(gameStage.review.totalPoints)))
    sendUpdate(TotalCorrectAnswersUpdate(Option(gameStage.review.totalCorrectAnswers)))

