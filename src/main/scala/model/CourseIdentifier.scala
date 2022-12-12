package model

/**
 * Object used for managing a course identifier
 */
object CourseIdentifier:

  /**
   * Case class for the CourseIdentifier model
   *
   * @param courseName     the name of the course (E.g. Paradigmi di Programmazione e Sviluppo)
   * @param degreeName     the name of the degree course (E.g. Laurea Magistrale in Ingegneria e Scienze Informatiche)
   * @param universityName the name of the university (E.g. University of Bologna)
   */
  case class CourseIdentifier(courseName: String, degreeName: String, universityName: String)

  /**
   * Change the name of the course
   * @param courseId the course identifier to edit
   * @param courseName name of the course
   * @return CourseIdentifier
   */
  def changeCourseName(courseId: CourseIdentifier, courseName: String): CourseIdentifier = CourseIdentifier(courseName, courseId.degreeName, courseId.universityName)

  /**
   * Change the name of the course  degree
   * @param courseId the course identifier to edit
   * @param degreeName name of the course degree
   * @return CourseIdentifier
   */
  def changeDegreeName(courseId: CourseIdentifier, degreeName: String): CourseIdentifier = CourseIdentifier(courseId.courseName, degreeName, courseId.universityName)

  /**
   * Change the name of the course university
   * @param courseId the course identifier to edit
   * @param universityName name of the course university
   * @return CourseIdentifier
   */
  def changeUniversityName(courseId: CourseIdentifier, universityName: String): CourseIdentifier = CourseIdentifier(courseId.courseName, courseId.degreeName, universityName)
