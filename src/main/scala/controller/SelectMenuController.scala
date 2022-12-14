package controller

import controller.{AppController, PageController}
import controller.AppController.*
import controller.actions.{Action, BackAction, ParameterlessAction}
import view.{SelectMenuView, View}
import view.updates.{ParameterlessViewUpdate, ViewUpdate}
import controller.{AppController, PageController, StandardGameController, BlitzGameController}
import model.{GameStage, QuizInGame, Session}
import model.Session.Session
import model.GameStage.*
import view.SelectMenuView.*
import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}
import scala.collection.mutable.ListBuffer
import scala.concurrent.{Await, Promise}
import scala.concurrent.duration.Duration

/** Companion object of select menu controller containing all the actions can be sent to it */
object SelectMenuController extends BackAction:

  case object Start extends ParameterlessAction
  case object Blitz extends ParameterlessAction
  case object Custom extends ParameterlessAction
  case class Selection[T](override val actionParameter: Option[T]) extends Action(actionParameter)

/** Defines the logic of the select page */
class SelectMenuController extends PageController:

  import SelectMenuController.*

  var gameStage = GameStage()

  override def handle[T](action: Action[T]): Unit = action match
    case Back => AppController.handle(AppController.MainMenuAction)
    case Start => if StandardGameController.isGameStagePlayable(gameStage) then
      AppController.handle(AppController.StandardGameAction(Option(gameStage)))
      else sendUpdate(CourseUnplayableUpdate)
    case Blitz => if BlitzGameController.isGameStagePlayable(gameStage) then
      AppController.handle(AppController.BlitzGameAction(Option(gameStage)))
      else sendUpdate(CourseUnplayableUpdate)
    case Custom => if StandardGameController.isGameStagePlayable(gameStage) then
      AppController.handle(AppController.CustomMenuAction(Option(gameStage)))
      else sendUpdate(CourseUnplayableUpdate)
    case Selection(actionParameter) => modifySelectedCourses(actionParameter)

  // Get session from application controller
  def getSession: Session = AppController.session

  override def nextIteration(): Unit =
    sendUpdate(DefaultUpdate)
    sendUpdate(CourseUpdate(Option(getSession.savedCourses.map(course => (course,gameStage.coursesInGame.contains(course))))))

  private def modifySelectedCourses[T](courseIndex: Option[T]): Unit =
    val selectedCourse = getSession.savedCourses(courseIndex.get.asInstanceOf[Int])

    if gameStage.coursesInGame.contains(selectedCourse) then
      gameStage.coursesInGame = gameStage.coursesInGame.diff(List(selectedCourse))
    else
      gameStage.coursesInGame = gameStage.coursesInGame ::: List(selectedCourse)
