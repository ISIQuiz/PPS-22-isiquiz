package model.stats

import model.CourseIdentifier.CourseIdentifier
import model.{Course, GameStage, QuizAnswered, QuizInGame, Review, SavedCourse, Session, stats}
import model.SavedCourse.SavedCourse
import model.stats.CourseInStats.{CourseInStats, changeCourse}
import model.stats.QuizInStats.QuizInStats

import java.util.UUID
import scala.util.{Failure, Success, Try}

/** Object for PlayerStats model */
object PlayerStats:

  /**
   * Case class for PlayerStats model
   *
   * @param totalScore             total user score
   * @param totalAnsweredQuestions total number of answered questions
   * @param totalCorrectAnswers    total number of question answered correctly
   * @param totalAnswerPrecision   total % answer precision
   * @param totalAverageTimeAnswer total average time to answer
   * @param courseInStatsList      the list of course in stats
   */
  case class PlayerStats(totalScore: Int = initStats.totalScore,
                         totalAnsweredQuestions: Int = initStats.totalAnsweredQuestions,
                         totalCorrectAnswers: Int = initStats.totalCorrectAnswers,
                         totalAnswerPrecision: Int = initStats.totalAnswerPrecision,
                         totalAverageTimeAnswer: Double = initStats.totalAverageTimeAnswer,
                         courseInStatsList: List[CourseInStats] = initStats.courseInStatsList)

  /**
   * Change total score in player stats
   *
   * @param playerStats
   * @param totalScore
   * @return an updated [[PlayerStats]]
   */
  def changeTotalScore(playerStats: PlayerStats, totalScore: Int): PlayerStats =
    PlayerStats(totalScore, playerStats.totalAnsweredQuestions, playerStats.totalCorrectAnswers, playerStats.totalAnswerPrecision, playerStats.totalAverageTimeAnswer, playerStats.courseInStatsList)

  /**
   * Change total answered questions
   *
   * @param playerStats
   * @param totalAnsweredQuestions
   * @return an updated [[PlayerStats]]
   */
  def changeTotalAnsweredQuestions(playerStats: PlayerStats, totalAnsweredQuestions: Int): PlayerStats =
    PlayerStats(playerStats.totalScore, totalAnsweredQuestions, playerStats.totalCorrectAnswers, playerStats.totalAnswerPrecision, playerStats.totalAverageTimeAnswer, playerStats.courseInStatsList)

  /**
   * Change total correct answers
   *
   * @param playerStats
   * @param totalCorrectAnswers
   * @return an updated [[PlayerStats]]
   */
  def changeTotalCorrectAnswers(playerStats: PlayerStats, totalCorrectAnswers: Int): PlayerStats =
    PlayerStats(playerStats.totalScore, playerStats.totalAnsweredQuestions, totalCorrectAnswers, playerStats.totalAnswerPrecision, playerStats.totalAverageTimeAnswer, playerStats.courseInStatsList)

  /**
   * Change total answer precision
   *
   * @param playerStats
   * @param totalAnswerPrecision
   * @return an updated [[PlayerStats]]
   */
  def changeTotalAnswerPrecision(playerStats: PlayerStats, totalAnswerPrecision: Int): PlayerStats =
    PlayerStats(playerStats.totalScore, playerStats.totalAnsweredQuestions, playerStats.totalCorrectAnswers, totalAnswerPrecision, playerStats.totalAverageTimeAnswer, playerStats.courseInStatsList)

  /**
   * Change total average time to answer
   *
   * @param playerStats
   * @param totalAverageTimeAnswer
   * @return an updated [[PlayerStats]]
   */
  def changeTotalAverageTimeAnswer(playerStats: PlayerStats, totalAverageTimeAnswer: Double): PlayerStats =
    PlayerStats(playerStats.totalScore, playerStats.totalAnsweredQuestions, playerStats.totalCorrectAnswers, playerStats.totalAnswerPrecision, totalAverageTimeAnswer, playerStats.courseInStatsList)

  /**
   * Change course in stats list
   *
   * @param playerStats
   * @param courseInStatsList
   * @return an updated [[PlayerStats]]
   */
  def changeCourseInStatsList(playerStats: PlayerStats, courseInStatsList: List[CourseInStats]): PlayerStats =
    PlayerStats(playerStats.totalScore, playerStats.totalAnsweredQuestions, playerStats.totalCorrectAnswers, playerStats.totalAnswerPrecision, playerStats.totalAverageTimeAnswer, courseInStatsList)

  /**
   * Update [[PlayerStats]] with all the values that can be derived
   *
   * @param courseInStatsList
   * @return an updated [[PlayerStats]]
   */
  def updatePlayerStats(courseInStatsList: List[CourseInStats]): PlayerStats =
    PlayerStats(
      totalScore = calculateTotalScore(courseInStatsList),
      totalAnsweredQuestions = calculateTotalAnsweredQuestions(courseInStatsList),
      totalCorrectAnswers = calculateTotalCorrectAnswer(courseInStatsList),
      totalAnswerPrecision = calculateTotalAnswerPrecision(calculateTotalAnsweredQuestions(courseInStatsList), calculateTotalCorrectAnswer(courseInStatsList)),
      totalAverageTimeAnswer = calculateTotalAverageTimeAnswer(courseInStatsList),
      courseInStatsList = courseInStatsList
    )

  /**
   * Add a [[QuizInGame]] to the [[PlayerStats]] of the current game
   *
   * @param playerStats
   * @param quizInGame
   * @param isCorrect
   * @param score
   * @param timeToAnswer
   * @return an updated [[PlayerStats]] with the newly added quiz in game
   */
  def addQuizInGameToStats(playerStats: PlayerStats, quizInGame: QuizInGame, isCorrect: Boolean, score: Int, timeToAnswer: Double): PlayerStats =

    val newQuizInStats = if (isCorrect)
      QuizInStats(quizInGame.quiz.quizId, 1, score, 1, formatDouble(timeToAnswer))
    else
      QuizInStats(quizInGame.quiz.quizId, 1)

    val newCourseInStats = CourseInStats(quizInGame.course, List(newQuizInStats))

    // Check if course already exists
    val newCourseInStatsList = if (playerStats.courseInStatsList.map(c => c.course).contains(quizInGame.course))
    // Merge existent player stats with the new player stats
      mergeCourseInStatsLists(playerStats.courseInStatsList, List(newCourseInStats))
    else
      playerStats.courseInStatsList.appended(newCourseInStats)

    updatePlayerStats(newCourseInStatsList)

  /**
   * Merge two [[PlayerStats]] in a new one
   *
   * @param playerStats
   * @param newPlayerStats
   * @return an updated [[PlayerStats]]
   */
  def mergePlayerStats(playerStats: PlayerStats, newPlayerStats: PlayerStats): PlayerStats =
    updatePlayerStats(mergeCourseInStatsLists(playerStats.courseInStatsList, newPlayerStats.courseInStatsList))

  // Merge two lists of CourseInStats
  private def mergeCourseInStatsLists(courseInStatsList: List[CourseInStats], newCourseInStatsList: List[CourseInStats]): List[CourseInStats] =
    (courseInStatsList ::: newCourseInStatsList).groupMapReduce(c => c.course.courseId)(c => c.quizInStatsList)(
      (quizInStatsList, newQuizInStatsList) => mergeQuizInStatsLists(quizInStatsList, newQuizInStatsList)
    ).map((k, v) => CourseInStats(Course(k), v)).toList

  // Merge two lists of QuizInStats and calculates the new values
  private def mergeQuizInStatsLists(quizInStatsList: List[QuizInStats], newQuizInStatsList: List[QuizInStats]): List[QuizInStats] =
    (quizInStatsList ::: newQuizInStatsList).groupMapReduce(q => q.quizId)(q => (q.totalSeen, q.totalScore, q.totalRightAnswers, q.averageTimeAnswer))(
      (quizInStats, newQuizInStats) => (
        quizInStats._1 + newQuizInStats._1, // sum total seen
        quizInStats._2 + newQuizInStats._2, // sum total score
        quizInStats._3 + newQuizInStats._3, // sum total right answer
        calculateWeightedAverage(quizInStats._4, quizInStats._3, newQuizInStats._4, newQuizInStats._3) //calculate weighted average
      )
    ).map((k, v) => QuizInStats(k, v._1, v._2, v._3, v._4)).toList

  // Calculate weighted average time to answer only on correct answer
  private def calculateWeightedAverage(averageTimeAnswer: Double, totalCorrectAnswers: Int, newAverageTimeAnswer: Double, newTotalCorrectAnswers: Int): Double =
    val c: Double = if ((totalCorrectAnswers + newTotalCorrectAnswers) > 0)
      ((averageTimeAnswer * totalCorrectAnswers) + (newAverageTimeAnswer * newTotalCorrectAnswers)) / (totalCorrectAnswers + newTotalCorrectAnswers)
    else
      0
    formatDouble(c)

  // Calculate total score
  private def calculateTotalScore(courseInStatsList: List[CourseInStats]): Int =
    courseInStatsList.map(
      _.quizInStatsList.map(
        _.totalScore
      ).sum
    ).sum

  //Calculates total answered questions
  private def calculateTotalAnsweredQuestions(courseInStatsList: List[CourseInStats]): Int =
    courseInStatsList.map(
      _.quizInStatsList.map(
        _.totalSeen
      ).sum
    ).sum

  //Calculates total correct answer
  private def calculateTotalCorrectAnswer(courseInStatsList: List[CourseInStats]): Int =
    courseInStatsList.map(
      _.quizInStatsList.map(
        _.totalRightAnswers
      ).sum
    ).sum

  /**
   * Calculates total answer precision given two value
   *
   * @param totalAnswered
   * @param totalCorrectAnswer
   * @return an Int as percentage
   */
  def calculateTotalAnswerPrecision(totalAnswered: Int, totalCorrectAnswer: Int): Int =
    val percentage = Try((totalCorrectAnswer * 100) / totalAnswered).getOrElse(0)
    if (percentage > 100) 100 else percentage

  // Calculates total average time to answer
  private def calculateTotalAverageTimeAnswer(courseInStatsList: List[CourseInStats]): Double =
    val totalCorrectAnswer = calculateTotalCorrectAnswer(courseInStatsList)
    if (courseInStatsList.nonEmpty && totalCorrectAnswer != 0)
      val totalAverageTimeAnswer = courseInStatsList.flatMap(c => c.quizInStatsList).map(q => q.averageTimeAnswer * q.totalRightAnswers).sum
      formatDouble(totalAverageTimeAnswer / totalCorrectAnswer)
    else
      0

  /**
   * Init player stats with default values
   *
   * @return a [[PlayerStats]]
   */
  def initStats: PlayerStats =
    PlayerStats(0, 0, 0, 0, 0, List())

  /**
   * Removes statistics that don't have a correspondent [[SavedCourse]]
   *
   * @param savedCourseList
   * @param playerStats
   * @return an updated [[PlayerStats]]
   */
  def removeUnusedStats(savedCourseList: List[SavedCourse], playerStats: PlayerStats): PlayerStats =

    // check empty
    val listSavedCourseId = savedCourseList.map(l => l.courseId)
    val listOfQuizId = savedCourseList.flatMap(c => c.quizList).map(q => q.quizId)

    val newCourseInStatsList = playerStats.courseInStatsList
      .map(c => CourseInStats(
        c.course,
        c.quizInStatsList.filter(q => listOfQuizId.contains(q.quizId))
      )).filter(c => (listSavedCourseId.contains(c.course.courseId) && c.quizInStatsList.nonEmpty))

    updatePlayerStats(newCourseInStatsList)

  // Round double to two decimal places
  private def formatDouble(d: Double): Double =
    BigDecimal(d).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble

  /**
   * Update course in stats list in player stats
   *
   * @param oldCourseId
   * @param updatedCourseId
   * @param playerStats
   * @return an updated [[PlayerStats]]
   */
  def updateCourseInStatsList(oldCourseId: CourseIdentifier, updatedCourseId: CourseIdentifier, playerStats: PlayerStats): PlayerStats =
    Try(playerStats.courseInStatsList.filter(c => c.course.courseId == oldCourseId).head) match
      case Success(c) =>
        val cList: List[CourseInStats] = playerStats.courseInStatsList.filterNot(c => c.course.courseId == oldCourseId)
        val newCourseInStats = CourseInStats(Course(updatedCourseId), c.quizInStatsList)
        val newList = cList.appended(newCourseInStats)
        changeCourseInStatsList(playerStats, newList)

      case Failure(f) =>
        playerStats