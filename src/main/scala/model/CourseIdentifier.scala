package model

/**
 * Trait for the identifier of the course
 */
trait CourseIdentifier:
  
  /**
   * @return name of the course
   */
  def courseName: String

  /**
   * @return name of the course degree 
   */
  def degreeName: String

  /**
   * @return name of the course university 
   */
  def universityName: String

/**
 * Object used for managing a course identifier
 */
object CourseIdentifier:

  /**
   * Creates a new [[CourseIdentifier]]
   * @param courseName the name of the course (E.g. Paradigmi di Programmazione e Sviluppo)
   * @param degreeName the name of the degree course (E.g. Laurea Magistrale in Ingegneria e Scienze Informatiche)
   * @param universityName the name of the university (E.g. University of Bologna)
   * @return CourseIdentifier
   */
  def apply(courseName: String, degreeName: String, universityName: String): CourseIdentifier = CourseIdentifierImpl(courseName, degreeName, universityName)

  /**
   * Case class for the course identifier model
   * @param courseName the name of the course
   * @param degreeName the name of the degree course
   * @param universityName the name of the university
   */
  case class CourseIdentifierImpl(override val courseName: String, override val degreeName: String, override val universityName: String) extends CourseIdentifier

  /**
   * Change the name of the course 
   * @param courseId the course identifier to edit
   * @param courseName name of the course
   * @return CourseIdentifier
   */
  def changeCourseName(courseId: CourseIdentifier, courseName: String): CourseIdentifier = courseId match
    case CourseIdentifierImpl(_, degreeName, universityName) => CourseIdentifier(courseName, degreeName, universityName)

  /**
   * Change the name of the course  degree
   * @param courseId the course identifier to edit
   * @param degreeName name of the course degree
   * @return CourseIdentifier
   */
  def changeDegreeName(courseId: CourseIdentifier, degreeName: String): CourseIdentifier = courseId match
    case CourseIdentifierImpl(courseName, _, universityName) => CourseIdentifier(courseName, degreeName, universityName)

  /**
   * Change the name of the course university 
   * @param courseId the course identifier to edit
   * @param universityName name of the course university
   * @return CourseIdentifier
   */
  def changeUniversityName(courseId: CourseIdentifier, universityName: String): CourseIdentifier = courseId match
    case CourseIdentifierImpl(courseName, degreeName, _) => CourseIdentifier(courseName, degreeName, universityName)