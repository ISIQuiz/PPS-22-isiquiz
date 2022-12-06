package model

import model.stats.PlayerStats.{PlayerStats, initStats}
import utils.Configuration.PlayerCoursesFilePath
import utils.DefaultDataList.defaultCourseList
import utils.parser.CourseJsonParser
import utils.{DefaultDataList, FileHandler}

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

  def playerStats: PlayerStats
/**
 * Object for the game session model
 */
object Session:

  /**
   * Creates a new [[Session]] object
   * @param savedCourses the list of saved course, if empty it uses a sample list
   * @return Session
   */
  def apply(savedCourses: List[SavedCourse] = defaultCourseList, playerStats: PlayerStats = initStats): Session = SessionImpl(savedCourses, playerStats)

  /**
   * Case class for session model
   * @param savedCourses list of saved course in session
   */
  case class SessionImpl(savedCourses: List[SavedCourse], playerStats: PlayerStats) extends Session

  /**
   * Change the saved course list in session
   * @param savedCourses the new saved course list
   * @return a [[Session]]
   */
  def changeSavedCourses(session: Session, savedCourses: List[SavedCourse]): Session = session match
    case SessionImpl(_, playerStats) => SessionImpl(savedCourses, playerStats)

  /**
   * Change player stats in session
   * @param session
   * @param playerStats the new player stats
   * @return a [[Session]]
   */
  def changePlayerStats(session: Session, playerStats: PlayerStats): Session = session match
    case SessionImpl(savedCourses, _) => SessionImpl(savedCourses, playerStats)

