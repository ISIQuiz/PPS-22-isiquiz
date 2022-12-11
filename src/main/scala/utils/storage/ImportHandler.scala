package utils.storage

import model.Answer.Answer
import model.Quiz.Quiz
import model.SavedCourse.SavedCourse
import model.{Session}
import model.Session.*
import model.stats.PlayerStats.{PlayerStats, initStats}
import utils.parser.{CourseJsonParser, JsonParser, StatsJsonParser}
import utils.storage.Configuration.{PlayerCoursesFilePath, PlayerStatsFilePath}
import utils.storage.ExportHandler.exportSessionToPersonalDirectory
import scala.util.{Failure, Success, Try}

object ImportHandler extends DataStorageHandler:

  /**
   * Import the [[Session]] from personal directory.
   * Path and name are predetermined in [[Configuration]]
   *
   * @param session
   * @return an updated Try[Session]
   */
  def importSessionFromPersonalDirectory(session: Session): Try[Session] =
  // Load player courses and deserialize the JSON string in a SavedCourse list
    loadDataFromFile(CourseJsonParser(), PlayerCoursesFilePath) match
      case Success(savedCourseList: List[SavedCourse]) => // Parse SavedCourse list success
        val newSessionCourses = Session.changeSavedCourses(session, savedCourseList) // update Session saved course list
        importPlayerStatsFromPersonalDirectory(newSessionCourses)
      case Failure(f) => // Failed to parse savedCourse from file, store passed session to file
        println("courses and maybe stats do not exists, created default session")
        ExportHandler.exportDataToPersonalDirectory(session)
        Failure(f)


  /**
   * Import [[PlayerStats]] from personal directory.
   * Path and name are predetermined in [[Configuration]]
   *
   * @param session
   * @return an updated Try[Session]
   */
  private def importPlayerStatsFromPersonalDirectory(session: Session): Try[Session] =
  // Load player stats and deserialize the JSON string in PlayerStats
    loadDataFromFile(StatsJsonParser(), PlayerStatsFilePath) match
      case Success(playerStats: PlayerStats) => // Success import courses and stats from file
        println("courses and stats imported from player data")
        Success(Session.changePlayerStats(session, playerStats))
      case Failure(f) => // Failed to parse player stats, reset player stats to 0
        println("stats do not exist, created file with init stats to 0")
        storeDataToPath(initStats, PlayerStatsFilePath)
        Failure(f)

  /**
   * Import the list of [[SavedCourse]] from chosen file,
   * and merges it with the saved course list of the passed session.
   * Name of the file is predetermined in [[Configuration]]
   *
   * @param session
   * @param filePath
   * @return a Try of List[SavedCourse]
   */
  def importSavedCourseListFromFile(session: Session, filePath: String): Try[List[SavedCourse]] =
    for
      savedCourseList <- loadDataFromFile(CourseJsonParser(), filePath)
    yield
      DataStorageHandler.mergeSavedCourseLists(session.savedCourses, savedCourseList)



