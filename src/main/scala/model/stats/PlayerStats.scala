package model.stats

import model.{Course, SavedCourse}
import model.stats.CourseInStats.CourseInStats
import utils.DefaultDataList.defaultPlayerStats

/** Object for PlayerStats model */
object PlayerStats:

  // Case class for player stats
  case class PlayerStats(totalScore: Int,
                         totalAnsweredQuestions: Int,
                         totalCorrectAnswers: Int,
                         courseInStatsList: List[CourseInStats])

  /**
   * Create a new [[PlayerStats]]
   * @param totalScore total user score
   * @param totalAnsweredQuestions total number of answered questions
   * @param totalCorrectAnswers total number of question answered correctly
   * @param courseInStatsList the list of course in stats
   * @return a [[PlayerStats]]
   */
  def apply(totalScore: Int = defaultPlayerStats.totalScore,
            totalAnsweredQuestions: Int = defaultPlayerStats.totalAnsweredQuestions,
            totalCorrectAnswers: Int = defaultPlayerStats.totalCorrectAnswers,
            courseInStatsList: List[CourseInStats] = defaultPlayerStats.courseInStatsList) =
    PlayerStats(totalScore, totalAnsweredQuestions, totalCorrectAnswers, courseInStatsList)

  /**
   * Change total score in player stats
   * @param playerStats
   * @param totalScore
   * @return updated [[PlayerStats]]
   */
  def changeTotalScore(playerStats: PlayerStats, totalScore: Int): PlayerStats = playerStats match
    case PlayerStats(_, totalAnsweredQuestions, totalCorrectAnswers, courseInStatsList) =>
      PlayerStats(totalScore, totalAnsweredQuestions, totalCorrectAnswers, courseInStatsList)

  /**
   * Change total answered questions
   * @param playerStats
   * @param totalAnsweredQuestions
   * @return updated [[PlayerStats]]
   */
  def changeTotalAnsweredQuestions(playerStats: PlayerStats, totalAnsweredQuestions: Int): PlayerStats = playerStats match
    case PlayerStats(totalScore, _, totalCorrectAnswers, courseInStatsList) =>
      PlayerStats(totalScore, totalAnsweredQuestions, totalCorrectAnswers, courseInStatsList)

  /**
   * Change total correct answers
   * @param playerStats
   * @param totalCorrectAnswers
   * @return updated [[PlayerStats]]
   */
  def changeTotalCorrectAnswers(playerStats: PlayerStats, totalCorrectAnswers: Int): PlayerStats = playerStats match
    case PlayerStats(totalScore, totalAnsweredQuestions, _, courseInStatsList) =>
      PlayerStats(totalScore, totalAnsweredQuestions, totalCorrectAnswers, courseInStatsList)

  /**
   * Change course in stats list
   * @param playerStats
   * @param courseInStatsList
   * @return updated [[PlayerStats]]
   */
  def changeCourseInStatsList(playerStats: PlayerStats, courseInStatsList: List[CourseInStats]): PlayerStats = playerStats match
    case PlayerStats(totalScore, totalAnsweredQuestions, totalCorrectAnswers, _) =>
      PlayerStats(totalScore, totalAnsweredQuestions, totalCorrectAnswers, courseInStatsList)

  /**
   * Init player stats with default values based on passed SavedCourse list
   * @param savedCourseList
   * @return a [[PlayerStats]]
   */
  def defaultPlayerStatsFromSavedCourseList(savedCourseList: List[SavedCourse]): PlayerStats =
    PlayerStats(0, 0, 0,
      savedCourseList.map(
        savedCourse => CourseInStats(
          Course(
            savedCourse.courseId,
          ),
          savedCourse.quizList.map(
            quiz => QuizInStats()
          )
        )
      ).toList
    )