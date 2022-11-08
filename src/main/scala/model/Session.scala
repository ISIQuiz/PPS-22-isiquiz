package model

/**
 * Trait needed to manage a game session
 */
trait Session:
  // def playerStats: PlayerStats

  /**
   * @return list of saved course
   */
  def savedCourses: List[SavedCourse]

/**
 * Object for the game session model
 */
object Session:

  /**
   * Creates a new [[Session]] object
   * @param savedCourses the list of saved course
   * @return Session
   */
  def apply(savedCourses: List[SavedCourse]): Session = SessionImpl(savedCourses)

  /**
   * Case class for the game session
   * @param savedCourses
   */
  case class SessionImpl(savedCourses: List[SavedCourse]) extends Session

  def changeSavedCourses(savedCourses: List[SavedCourse]): Session = SessionImpl(savedCourses)

