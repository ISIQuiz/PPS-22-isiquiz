package model.stats

import model.stats.CourseInStats
import model.stats.QuizInStats.QuizInStats
import model.Course
import model.CourseIdentifier.*
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import java.util.UUID

class CourseInStatsTest extends AnyFunSuite with Matchers:

  val courseInStats =
    CourseInStats(
      Course(
        CourseIdentifier("courseName", "degreeName", "universityName")
      ),
      List()
    )

  test("Test change course") {
    val course = Course(CourseIdentifier("test", "test", "test"))

    val courseInStatsChanged = CourseInStats.changeCourse(courseInStats, course)
    courseInStatsChanged.course shouldEqual course
  }
  
  test("Test change quiz in stats list") {
    val quizInStatsList = List(
      QuizInStats(UUID.randomUUID(), 1, 2, 3),
      QuizInStats(UUID.randomUUID(), 4, 5, 6)
    )

    val courseInStatsChanged = CourseInStats.changeQuizInStatsList(courseInStats, quizInStatsList)
    courseInStatsChanged.quizInStatsList shouldEqual quizInStatsList
  }