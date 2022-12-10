package model.stats

import model.Course
import model.CourseIdentifier.*
import model.stats.CourseInStats.CourseInStats
import model.stats.PlayerStats.initStats
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class PlayerStatsTest extends AnyFunSuite with Matchers :

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



