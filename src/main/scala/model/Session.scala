package model

import utils.Configuration.SavedCoursesFilePath
import utils.DefaultCourseList.defaultCourseList
import utils.{CourseJsonParser, DefaultCourseList, FileHandler}

import scala.util.{Failure, Success, Try}

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
   * @param savedCourses the list of saved course, if empty it uses a sample list
   * @return Session
   */
  def apply(savedCourses: List[SavedCourse] = defaultCourseList): Session = SessionImpl(savedCourses)

  /**
   * Case class for session model
   * @param savedCourses list of saved course in session
   */
  case class SessionImpl(savedCourses: List[SavedCourse]) extends Session

  /**
   * Change the saved course list in session
   * @param savedCourses the new saved course list
   * @return Session
   */
  def changeSavedCourses(session: Session, savedCourses: List[SavedCourse]): Session = session match
    case SessionImpl(_) => SessionImpl(savedCourses)

