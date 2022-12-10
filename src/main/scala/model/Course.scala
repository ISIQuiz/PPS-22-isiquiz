package model

import model.CourseIdentifier.CourseIdentifier

/**
 * Trait for a course
 */
trait Course:
  
  /**
   * @return the course identifier
   */
  def courseId: CourseIdentifier

/**
 *  Object used for managing a Course
 */
object Course:

  /**
   * Creates a new [[Course]]
   * @param courseId the course identifier
   * @return Course
   */
  def apply(courseId: CourseIdentifier): Course = CourseImpl(courseId)

  /**
   * Case class for course
   * @param courseId the course identifier
   */
  case class CourseImpl(override val courseId: CourseIdentifier) extends Course