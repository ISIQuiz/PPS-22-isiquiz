package view

import model.stats.CourseInStats.CourseInStats
import model.stats.QuizInStats.QuizInStats
import view.updates.{DefaultUpdate, ParameterlessViewUpdate, ViewUpdate}

object StatisticsMenuView extends DefaultUpdate:

  /** Update for the view containing the list of current curses */
  case class CourseInStatsListUpdate(override val updateParameter: Option[List[CourseInStats]]) extends ViewUpdate(updateParameter)

  /** Update for the view containing the list of current quizzes in the course selected */
  case class QuizInStatsListUpdate(override val updateParameter: Option[List[QuizInStats]]) extends ViewUpdate(updateParameter)
