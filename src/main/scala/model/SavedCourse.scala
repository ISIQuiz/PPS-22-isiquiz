package model

import model.Course
import model.CourseIdentifier.CourseIdentifier
import model.Quiz.Quiz

/**
 * Object used for managing a saved course
 */
object SavedCourse:

  /**
   * Case class for saved course model
   * @param courseId course identifier
   * @param description description of the course (optional)
   * @param quizList quiz list of saved quiz
  */
  case class SavedCourse(courseId: CourseIdentifier, description: Option[String], quizList: List[Quiz]) extends Course

  /**
   * Change the course identifier
   * @param savedCourse the saved course to edit
   * @param courseId a new course identifier
   * @return SavedCourse
   */
  def changeCourseIdentifier(savedCourse: SavedCourse, courseId: CourseIdentifier): SavedCourse = SavedCourse(courseId, savedCourse.description, savedCourse.quizList)

  /**
   * Change the course description
   * @param savedCourse the saved course to edit
   * @param description a new course description (optional)
   * @return SavedCourse
   */
  def changeDescription(savedCourse: SavedCourse, description: Option[String]): SavedCourse = SavedCourse(savedCourse.courseId, description, savedCourse.quizList)

  /**
   * Change the list of quiz
   * @param savedCourse the saved course to edit
   * @param quizList a new list of quiz
   * @return SavedCourse
   */
  def changeQuizList(savedCourse: SavedCourse, quizList: List[Quiz]): SavedCourse = SavedCourse(savedCourse.courseId, savedCourse.description, quizList)
