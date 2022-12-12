package model

import model.SavedCourse.SavedCourse
import model.stats.PlayerStats.{PlayerStats, initStats}
import utils.storage.Configuration.PlayerCoursesFilePath
import utils.storage.DefaultDataList.defaultCourseList
import utils.parser.CourseJsonParser
import utils.storage.{DefaultDataList, FileHandler}
import scala.util.{Failure, Success, Try}

/**
 * Object for the game session model
 */
object Session:

  /**
   * Case class for session model
   *
   * @param savedCourses list of saved course in session
   * @param playerStats
   */
  case class Session(savedCourses: List[SavedCourse] = defaultCourseList, playerStats: PlayerStats = initStats)

  /**
   * Change the saved course list in session
   *
   * @param savedCourses the new saved course list
   * @return a [[Session]]
   */
  def changeSavedCourses(session: Session, savedCourses: List[SavedCourse]): Session = Session(savedCourses, session.playerStats)

  /**
   * Change player stats in session
   *
   * @param session
   * @param playerStats the new player stats
   * @return a [[Session]]
   */
  def changePlayerStats(session: Session, playerStats: PlayerStats): Session = Session(session.savedCourses, playerStats)
