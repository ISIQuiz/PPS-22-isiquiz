package model.stats

import model.Course
import model.stats.CourseInStats.CourseInStats
import model.stats.QuizInStats.QuizInStats

/** Object for course in player stats model */
object CourseInStats:

  // Case class for course in stats
  case class CourseInStats(course: Course, quizInStatsList: List[QuizInStats])

  /**
   * Creates a new [[CourseInStats]]
   * @param course
   * @param quizInStatsList
   * @return a [[CourseInStats]]
   */
  def apply(course: Course, quizInStatsList: List[QuizInStats]) = CourseInStats(course, quizInStatsList)

  /**
   * Change course in course in stats
   * @param courseInStats
   * @param course
   * @return updated [[CourseInStats]]
   */
  def changeCourse(courseInStats: CourseInStats, course: Course): CourseInStats = courseInStats match
    case CourseInStats(_, quizInStatsList) => CourseInStats(course, quizInStatsList)

  /**
   * Change the list of quiz in stats
   * @param courseInStats
   * @param quizInStatsList
   * @return updated [[CourseInStats]]
   */
  def changeQuizInStatsList(courseInStats: CourseInStats,quizInStatsList: List[QuizInStats]): CourseInStats = courseInStats match
    case CourseInStats(course, _) => CourseInStats(course, quizInStatsList)