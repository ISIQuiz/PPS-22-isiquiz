package controller

import controller.{AppController, PageController}
import controller.AppController.*
import controller.actions.{Action, BackAction, ParameterlessAction}
import model.Quiz.Quiz
import model.SavedCourse.SavedCourse
import model.stats.CourseInStats.CourseInStats
import model.stats.QuizInStats.QuizInStats
import view.View
import view.terminalUI.TerminalStatisticsMenu
import view.updates.{ParameterlessViewUpdate, ViewUpdate}
import scala.collection.mutable.ListBuffer
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import view.StatisticsMenuView.*

/** Companion object of statistics menu controller */
object StatisticsMenuController extends BackAction:

  /** Action to select a course in the statistics menu controller */
  case class SelectCourseAction(override val actionParameter: Option[CourseInStats]) extends Action(actionParameter)

  /** Action to select a quiz in the statistics menu controller */
  case class SelectQuizAction(override val actionParameter: Option[QuizInStats]) extends Action(actionParameter)


/** Defines the logic of the statistics page */
class StatisticsMenuController extends PageController:

  import StatisticsMenuController.*

  var courseInStatsSelected: Option[CourseInStats] = Option.empty

  var quizInStatsSelected: Option[QuizInStats] = Option.empty

  override def handle[T](action: Action[T]): Unit = action match
    case Back => AppController.handle(MainMenuAction)
    case SelectCourseAction(actionParameter) =>
      courseInStatsSelected = actionParameter
      quizInStatsSelected = None
    case SelectQuizAction(actionParameter) => quizInStatsSelected = actionParameter

  override def nextIteration(): Unit =
    sendUpdate(DefaultUpdate)
    if courseInStatsSelected.isEmpty then
      sendUpdate(CourseInStatsListUpdate(Option(AppController.session.playerStats.courseInStatsList)))
    else if quizInStatsSelected.isEmpty then
      sendUpdate(QuizInStatsListUpdate(Option(courseInStatsSelected.get.quizInStatsList)))
