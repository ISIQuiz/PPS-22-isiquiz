package model.stats

/** Object for quiz in player stats model */
object QuizInStats:

  // Case class of quiz in stats
  case class QuizInStats(totalSeen: Int, totalRightAnswers: Int, averageTimeAnswer: Double)

  /**
   * Create a new [[QuizInStats]]
   * @param totalSeen number of times the user has answered the quiz
   * @param totalRightAnswers number of time the user answered the quiz correctly
   * @param averageTimeAnswer average time taken by user to answer
   * @return a [[QuizInStats]]
   */
  def apply(totalSeen: Int = 0,
            totalRightAnswers: Int = 0,
            averageTimeAnswer: Double = 0) = QuizInStats(totalSeen, totalRightAnswers, averageTimeAnswer)

  /**
   * Change total seen in quiz in stats
   * @param quizInStats
   * @param totalSeen
   * @return updated [[QuizInStats]]
   */
  def changeTotalSeen(quizInStats: QuizInStats, totalSeen: Int): QuizInStats = quizInStats match
    case QuizInStats(_, totalRightAnswers, averageTimeAnswer) => QuizInStats(totalSeen, totalRightAnswers, averageTimeAnswer)

  /**
   * Change total right answers in quiz in stats
   * @param quizInStats
   * @param totalRightAnswers
   * @return updated [[QuizInStats]]
   */
  def changeTotalRightAnswers(quizInStats: QuizInStats, totalRightAnswers: Int): QuizInStats = quizInStats match
    case QuizInStats(totalSeen, _, averageTimeAnswer) => QuizInStats(totalSeen, totalRightAnswers, averageTimeAnswer)

  /**
   * Change average time answer in quiz in stats
   * @param quizInStats
   * @param averageTimeAnswer
   * @return updated [[QuizInStats]]
   */
  def changeAverageTimeAnswer(quizInStats: QuizInStats, averageTimeAnswer: Double): QuizInStats = quizInStats match
    case QuizInStats(totalSeen, totalRightAnswers, _) => QuizInStats(totalSeen, totalRightAnswers, averageTimeAnswer)
