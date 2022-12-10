package model.stats

import model.{Course, SavedCourse, Session, stats}
import model.stats.CourseInStats.CourseInStats

import scala.util.Try

/** Object for PlayerStats model */
object PlayerStats:

  // Case class for player stats
  case class PlayerStats(totalScore: Int,
                         totalAnsweredQuestions: Int,
                         totalCorrectAnswers: Int,
                         totalAnswerPrecision: Int,
                         totalAverageTimeAnswer: Double,
                         courseInStatsList: List[CourseInStats])

  /**
   * Create a new [[PlayerStats]]
   *
   * @param totalScore             total user score
   * @param totalAnsweredQuestions total number of answered questions
   * @param totalCorrectAnswers    total number of question answered correctly
   * @param totalAnswerPrecision   total % answer precision
   * @param totalAverageTimeAnswer total average time to answer
   * @param courseInStatsList      the list of course in stats
   * @return a [[PlayerStats]]
   */
  def apply(totalScore: Int = initStats.totalScore,
            totalAnsweredQuestions: Int = initStats.totalAnsweredQuestions,
            totalCorrectAnswers: Int = initStats.totalCorrectAnswers,
            totalAnswerPrecision: Int = initStats.totalAnswerPrecision,
            totalAverageTimeAnswer: Double = initStats.totalAverageTimeAnswer,
            courseInStatsList: List[CourseInStats] = initStats.courseInStatsList) =
    PlayerStats(totalScore,
      totalAnsweredQuestions,
      totalCorrectAnswers,
      totalAnswerPrecision,
      totalAverageTimeAnswer,
      courseInStatsList)

  /**
   * Change total score in player stats
   *
   * @param playerStats
   * @param totalScore
   * @return updated [[PlayerStats]]
   */
  def changeTotalScore(playerStats: PlayerStats, totalScore: Int): PlayerStats = playerStats match
    case PlayerStats(_, totalAnsweredQuestions, totalCorrectAnswers, totalAnswerPrecision, totalAverageTimeAnswer, courseInStatsList) =>
      PlayerStats(totalScore, totalAnsweredQuestions, totalCorrectAnswers, totalAnswerPrecision, totalAverageTimeAnswer, courseInStatsList)

  /**
   * Change total answered questions
   *
   * @param playerStats
   * @param totalAnsweredQuestions
   * @return updated [[PlayerStats]]
   */
  def changeTotalAnsweredQuestions(playerStats: PlayerStats, totalAnsweredQuestions: Int): PlayerStats = playerStats match
    case PlayerStats(totalScore, _, totalCorrectAnswers, totalAnswerPrecision, totalAverageTimeAnswer, courseInStatsList) =>
      PlayerStats(totalScore, totalAnsweredQuestions, totalCorrectAnswers, totalAnswerPrecision, totalAverageTimeAnswer, courseInStatsList)

  /**
   * Change total correct answers
   *
   * @param playerStats
   * @param totalCorrectAnswers
   * @return updated [[PlayerStats]]
   */
  def changeTotalCorrectAnswers(playerStats: PlayerStats, totalCorrectAnswers: Int): PlayerStats = playerStats match
    case PlayerStats(totalScore, totalAnsweredQuestions, _, totalAnswerPrecision, totalAverageTimeAnswer, courseInStatsList) =>
      PlayerStats(totalScore, totalAnsweredQuestions, totalCorrectAnswers, totalAnswerPrecision, totalAverageTimeAnswer, courseInStatsList)

  /**
   * Change total answer precision
   *
   * @param playerStats
   * @param totalAnswerPrecision
   * @return updated [[PlayerStats]]
   */
  def changeTotalAnswerPrecision(playerStats: PlayerStats, totalAnswerPrecision: Int): PlayerStats = playerStats match
    case PlayerStats(totalScore, totalAnsweredQuestions, totalCorrectAnswers, _, totalAverageTimeAnswer, courseInStatsList) =>
      PlayerStats(totalScore, totalAnsweredQuestions, totalCorrectAnswers, totalAnswerPrecision, totalAverageTimeAnswer, courseInStatsList)


  /**
   * Change total average time to answer
   *
   * @param playerStats
   * @param totalAverageTimeAnswer
   * @return updated [[PlayerStats]]
   */
  def changeTotalAverageTimeAnswer(playerStats: PlayerStats, totalAverageTimeAnswer: Double): PlayerStats = playerStats match
    case PlayerStats(totalScore, totalAnsweredQuestions, totalCorrectAnswers, totalAnswerPrecision, _, courseInStatsList) =>
      PlayerStats(totalScore, totalAnsweredQuestions, totalCorrectAnswers, totalAnswerPrecision, totalAverageTimeAnswer, courseInStatsList)

  /**
   * Change course in stats list
   *
   * @param playerStats
   * @param courseInStatsList
   * @return updated [[PlayerStats]]
   */
  def changeCourseInStatsList(playerStats: PlayerStats, courseInStatsList: List[CourseInStats]): PlayerStats = playerStats match
    case PlayerStats(totalScore, totalAnsweredQuestions, totalCorrectAnswers, totalAnswerPrecision, totalAverageTimeAnswer, _) =>
      PlayerStats(totalScore, totalAnsweredQuestions, totalCorrectAnswers, totalAnswerPrecision, totalAverageTimeAnswer, courseInStatsList)

  /**
   * Update player stats with all the value that can be derivated
   *
   * @param session
   * @return updated [[Session]]
   */
  def updatePlayerStats(playerStats: PlayerStats): PlayerStats = playerStats match
    case PlayerStats(totalScore, _, _, _, _, courseInStatsList) =>
      PlayerStats(
        totalScore,
        calculateTotalCorrectAnswer(playerStats),
        calculateTotalAnsweredQuestions(playerStats),
        calculateTotalAnswerPrecision(playerStats),
        calculateTotalAverageTimeAnswer(playerStats),
        courseInStatsList
      )

  /**
   * Calculates total correct answer
   *
   * @param playerStats
   * @return an Int
   */
  private def calculateTotalCorrectAnswer(playerStats: PlayerStats): Int =
    playerStats.courseInStatsList.map(
      _.quizInStatsList.map(
        _.totalRightAnswers
      ).sum
    ).sum

  /**
   * Calculates total answered questions
   *
   * @param playerStats
   * @return an Int
   */
  private def calculateTotalAnsweredQuestions(playerStats: PlayerStats): Int =
    playerStats.courseInStatsList.map(
      _.quizInStatsList.map(
        _.totalSeen
      ).sum
    ).sum

  /**
   * Calculates total answer precision
   *
   * @param playerStats
   * @return an Int as percentage
   */
  private def calculateTotalAnswerPrecision(playerStats: PlayerStats): Int =
    Try(calculateTotalCorrectAnswer(playerStats) * 100 / calculateTotalAnsweredQuestions(playerStats)).getOrElse(0)

  /**
   * Calculates total average time to answer
   *
   * @param playerStats
   * @return a Double as time
   */
  private def calculateTotalAverageTimeAnswer(playerStats: PlayerStats): Double =
    playerStats.courseInStatsList.filter(courseInStats => courseInStats.quizInStatsList.nonEmpty).map(
      courseInStats => courseInStats.quizInStatsList.map(
        quizInStats => quizInStats.averageTimeAnswer
      ).sum / courseInStats.quizInStatsList.size
    ).sum / playerStats.courseInStatsList.size

  /**
   * Init player stats with default values
   *
   * @return a [[PlayerStats]]
   */
  def initStats: PlayerStats =
    PlayerStats(0, 0, 0, 0, 0, List())


 /* /** TODO
   * Reset all the personal stats to 0
   *
   * @param session
   * @return an updated Try[Session]
   */
  def resetPersonalStatsFile(session: Session): Try[Session] =
    Try {
      val newSession = Session.changePlayerStats(session, initStats)
      //storeDataToPath(newSession, StatsJsonParser(), PlayerStatsFilePath)
      newSession
    }
*/
// TODO def removeUnusedQuizInStats(session: Session): PlayerStats

// TODO def updateQuizInStats(session, course, quiz): PlayerStats
