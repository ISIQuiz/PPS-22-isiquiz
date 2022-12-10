package model.stats

import model.{Course, CourseIdentifier}
import model.stats.CourseInStats.CourseInStats
import model.stats.PlayerStats.initStats
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import java.util.UUID

class PlayerStatsTest extends AnyFunSuite with Matchers:

  val playerStats = PlayerStats()

  test("Test default PlayerStats") {
    playerStats shouldEqual initStats
  }

  test("Test change total score") {
    val playerStatsChanged = PlayerStats.changeTotalScore(playerStats, 1)
    playerStatsChanged.totalScore shouldEqual 1
  }

  test("Test change total answered question") {
    val playerStatsChanged = PlayerStats.changeTotalAnsweredQuestions(playerStats, 2)
    playerStatsChanged.totalAnsweredQuestions shouldEqual 2
  }

  test("Test change total correct answers") {
    val playerStatsChanged = PlayerStats.changeTotalCorrectAnswers(playerStats, 3)
    playerStatsChanged.totalCorrectAnswers shouldEqual 3
  }


  test("Test change total answer precision") {
    val playerStatsChanged = PlayerStats.changeTotalAnswerPrecision(playerStats, 4)
    playerStatsChanged.totalAnswerPrecision shouldEqual 4
  }

  test("Test change total average time to answer") {
    val playerStatsChanged = PlayerStats.changeTotalAverageTimeAnswer(playerStats, 5)
    playerStatsChanged.totalAverageTimeAnswer shouldEqual 5
  }

  test("Test change course in stats list") {
    val courseInStatsList: List[CourseInStats] = List(
      CourseInStats(
        Course(
          CourseIdentifier("courseName", "degreeName", "universityName")
        ),
        List()
      )
    )
    val playerStatsChanged = PlayerStats.changeCourseInStatsList(playerStats, courseInStatsList)
    playerStatsChanged.courseInStatsList shouldEqual courseInStatsList
  }


  test("Test derived stats and merge") {

    val ci = CourseIdentifier("aaa", "bbb", "ccc")
    val quiz1 = QuizInStats(UUID.fromString("00000000-0000-0000-0000-000000000002"), totalSeen = 4, totalScore = 2, totalRightAnswers = 2, averageTimeAnswer = 2)
    val quiz2 = QuizInStats(UUID.fromString("00000000-0000-0000-0000-000000000002"), totalSeen = 4, totalScore = 4, totalRightAnswers = 4, averageTimeAnswer = 4)

    val playerStats = PlayerStats(0, 0, 0, 0, 0, List(CourseInStats(Course(ci), List(quiz1))))

    val newPlayerStats = PlayerStats(0, 0, 0, 0, 0, List(CourseInStats(Course(ci), List(quiz2))))

    val mergedList = PlayerStats.mergePlayerStats(playerStats, newPlayerStats)
    println(mergedList)
    val resultWanted = PlayerStats(6, 8, 6, 75, 3.0, List(CourseInStats(Course(ci), List(QuizInStats(UUID.fromString("00000000-0000-0000-0000-000000000002"), 8, 6, 6, 3.0)))))
    assert(mergedList.equals(resultWanted))

  }



