package model.stats

import model.Course
import model.stats.CourseInStats.CourseInStats
import model.stats.QuizInStats.QuizInStats

/** Object for course in player stats model */
object CourseInStats:

  /**
   * Case class for CourseInStats model
   *
   * @param course
   * @param quizInStatsList
   */
  case class CourseInStats(course: Course, quizInStatsList: List[QuizInStats])

  /**
   * Change course in course in stats
   *
   * @param courseInStats
   * @param course
   * @return updated [[CourseInStats]]
   */
  def changeCourse(courseInStats: CourseInStats, course: Course): CourseInStats = courseInStats match
    case CourseInStats(_, quizInStatsList) => CourseInStats(course, quizInStatsList)

  /**
   * Change the list of quiz in stats
   *
   * @param courseInStats
   * @param quizInStatsList
   * @return updated [[CourseInStats]]
   */
  def changeQuizInStatsList(courseInStats: CourseInStats, quizInStatsList: List[QuizInStats]): CourseInStats = courseInStats match
    case CourseInStats(course, _) => CourseInStats(course, quizInStatsList)