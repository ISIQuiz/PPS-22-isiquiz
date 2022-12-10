package model.stats

import model.Course
import model.CourseIdentifier.*
import model.Quiz.Quiz
import model.SavedCourse.SavedCourse
import model.stats.CourseInStats.CourseInStats
import model.stats.PlayerStats.{initStats, mergePlayerStats, removeUnusedStats}
import model.stats.QuizInStats.QuizInStats
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import java.util.UUID

class PlayerStatsTest extends AnyFunSuite with Matchers:

  val playerStats = initStats
  val ci = CourseIdentifier("courseName", "degreeName", "universityName")


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
    val courseInStatsList: List[CourseInStats] = List(CourseInStats(Course(ci), List()))
    val playerStatsChanged = PlayerStats.changeCourseInStatsList(playerStats, courseInStatsList)
    playerStatsChanged.courseInStatsList shouldEqual courseInStatsList
  }

  val quiz1 = QuizInStats(UUID.fromString("00000000-0000-0000-0000-000000000002"), totalSeen = 4, totalScore = 2, totalRightAnswers = 2, averageTimeAnswer = 2)
  val quiz2 = QuizInStats(UUID.fromString("00000000-0000-0000-0000-000000000002"), totalSeen = 4, totalScore = 4, totalRightAnswers = 4, averageTimeAnswer = 4)


  import model.stats.PlayerStats.PlayerStats

  test("Test derived stats and merge") {
    val playerStats = PlayerStats(0, 0, 0, 0, 0, List(CourseInStats(Course(ci), List(quiz1))))
    val newPlayerStats = PlayerStats(0, 0, 0, 0, 0, List(CourseInStats(Course(ci), List(quiz2))))
    val mergedList = mergePlayerStats(playerStats, newPlayerStats)
    println(mergedList)
    val resultWanted = PlayerStats(6, 8, 6, 75, 3.0, List(CourseInStats(Course(ci), List(QuizInStats(UUID.fromString("00000000-0000-0000-0000-000000000002"), 8, 6, 6, 3.0)))))
    assert(mergedList.equals(resultWanted))
  }


  val quiz3 = QuizInStats(UUID.fromString("00000000-0000-0000-0000-000000000003"), totalSeen = 6, totalScore = 1, totalRightAnswers = 2, averageTimeAnswer = 5)


  test("Test remove unused stats") {

    val savedCourseList = List(
      SavedCourse(ci, Option("description"), List(Quiz(UUID.fromString("00000000-0000-0000-0000-000000000003"), "", List(), 12))))

    val playerStats = PlayerStats(5, 4, 1, 2, 3, List(CourseInStats(Course(ci), List(quiz1, quiz3))))
    val newPlayerStats = removeUnusedStats(savedCourseList, playerStats)
    println(newPlayerStats)
    val wantedResult = PlayerStats(1, 6, 2, 33, 5, List(CourseInStats(Course(ci), List(quiz3))))

    newPlayerStats shouldEqual wantedResult
  }



