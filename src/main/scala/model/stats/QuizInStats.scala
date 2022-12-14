package model.stats

import model.SavedCourse.SavedCourse

import java.util.UUID

/** Object for quiz in player stats model */
object QuizInStats:

  /**
   * Create a new [[QuizInStats]]
   *
   * @param totalSeen         number of times the user has answered the quiz
   * @param totalScore        total score gained in the quiz
   * @param totalRightAnswers number of time the user answered the quiz correctly
   * @param averageTimeAnswer average time taken by user to answer
   */
  case class QuizInStats(quizId: UUID, totalSeen: Int = 0, totalScore: Int = 0, totalRightAnswers: Int = 0, averageTimeAnswer: Double = 0.0)

  /**
   * Change total seen in quiz in stats
   *
   * @param quizInStats
   * @param totalSeen
   * @return updated [[QuizInStats]]
   */
  def changeTotalSeen(quizInStats: QuizInStats, totalSeen: Int): QuizInStats =
    QuizInStats(quizInStats.quizId, totalSeen, quizInStats.totalScore, quizInStats.totalRightAnswers, quizInStats.averageTimeAnswer)

  /**
   * Change total score of the quiz
   * @param quizInStats
   * @param totalScore
   * @return updated [[QuizInStats]]
   */
  def changeTotalScore(quizInStats: QuizInStats, totalScore: Int): QuizInStats =
    QuizInStats(quizInStats.quizId, quizInStats.totalSeen, totalScore, quizInStats.totalRightAnswers, quizInStats.averageTimeAnswer)

  /**
   * Change total right answers in quiz in stats
   *
   * @param quizInStats
   * @param totalRightAnswers
   * @return updated [[QuizInStats]]
   */
  def changeTotalRightAnswers(quizInStats: QuizInStats, totalRightAnswers: Int): QuizInStats =
    QuizInStats(quizInStats.quizId, quizInStats.totalSeen, quizInStats.totalScore, totalRightAnswers, quizInStats.averageTimeAnswer)

  /**
   * Change average time answer in quiz in stats
   *
   * @param quizInStats
   * @param averageTimeAnswer
   * @return updated [[QuizInStats]]
   */
  def changeAverageTimeAnswer(quizInStats: QuizInStats, averageTimeAnswer: Double): QuizInStats =
    QuizInStats(quizInStats.quizId, quizInStats.totalSeen, quizInStats.totalScore, quizInStats.totalRightAnswers, averageTimeAnswer)


  /**
   * Get the quiz question guiven an quiz id
   *
   * @param quizInStatsId
   * @param savedCourseList
   * @return a [[String]] with the question
   */
  def getQuizQuestionById(quizInStatsId: UUID, savedCourseList: List[SavedCourse]): String =
    val list = savedCourseList.flatMap(s => s.quizList).map(q => (q.quizId, q.question))
    list.filter(a => a._1 == quizInStatsId).map(a => a._2).head