package model.stats

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import java.util.UUID

class QuizInStatsTest extends AnyFunSuite with Matchers :

  val quizInStats = QuizInStats(UUID.randomUUID())

  test("Test default QuizInStats") {
    quizInStats.totalSeen shouldEqual 0
    quizInStats.totalRightAnswers shouldEqual 0
    quizInStats.averageTimeAnswer shouldEqual 0
  }

  test("Test change total seen") {
    val quizInStatsChanged = QuizInStats.changeTotalSeen(quizInStats, 1)
    quizInStatsChanged.totalSeen shouldEqual 1
  }

  test("Test change total right answer") {
    val quizInStatsChanged = QuizInStats.changeTotalRightAnswers(quizInStats, 2)
    quizInStatsChanged.totalRightAnswers shouldEqual 2
  }

  test("Test change average time to answers") {
    val quizInStatsChanged = QuizInStats.changeAverageTimeAnswer(quizInStats, 3.33)
    quizInStatsChanged.averageTimeAnswer shouldEqual 3.33
  }



