package model

import model.Quiz.*
/**
 *  Trait for a saved course
 */
trait SavedCourse extends Course:
  
  /**
   * @return course description
   */
  def description: Option[String]

  /**
   * @return list of quiz
   */
  def quizList: List[Quiz]


/**
 * Object used for managing a saved course
 */
case object SavedCourse:

  /**
   * Creates a new [[SavedCourse]]
   * @param courseId course identifier
   * @param description course description (optional)
   * @param quizList quiz list of the course 
   * @return SavedCourse
   */
  def apply(courseId: CourseIdentifier,
            description: Option[String],
            quizList: List[Quiz]): SavedCourse =
    SavedCourseImpl(courseId, description, quizList)

  /** Case class for saved course model
  * @param description description of the course (optional)
  * @param quizList quiz list of saved quiz
  */
  case class SavedCourseImpl(override val courseId: CourseIdentifier,
                             override val description: Option[String],
                             override val quizList: List[Quiz]) extends SavedCourse
  
  /**
   * Change the course identifier
   * @param savedCourse the saved course to edit
   * @param courseId a new course identifier
   * @return SavedCourse
   */
  def changeCourseIdentifier(savedCourse: SavedCourse, courseId: CourseIdentifier): SavedCourse = savedCourse match
    case SavedCourseImpl(_, description, quizList) => SavedCourse(courseId, description, quizList)

  /**
   * Change the course description
   * @param savedCourse the saved course to edit
   * @param description a new course description (optional)
   * @return SavedCourse
   */
  def changeDescription(savedCourse: SavedCourse, description: Option[String]): SavedCourse = savedCourse match
    case SavedCourseImpl(courseId, _, quizList) =>
      SavedCourse(courseId, description, quizList)

  /**
   * Change the list of quiz
   * @param savedCourse the saved course to edit
   * @param quizList a new list of quiz
   * @return SavedCourse
   */
  def changeQuizList(savedCourse: SavedCourse, quizList: List[Quiz]): SavedCourse = savedCourse match
    case SavedCourseImpl(courseId, description, _) =>
      SavedCourse(courseId, description, quizList)