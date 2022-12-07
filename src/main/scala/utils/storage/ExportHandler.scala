package utils.storage

import controller.AppController
import model.Answer.Answer
import model.Quiz.Quiz
import model.stats.PlayerStats.{PlayerStats, initStats}
import model.stats.{CourseInStats, QuizInStats}
import model.{Course, SavedCourse, Session}
import play.api.libs.json.JsObject
import utils.storage.FileHandler
import utils.storage.Configuration.*
import utils.parser.{CourseJsonParser, JsonParser, StatsJsonParser}
import java.io.File
import java.io.FileNotFoundException
import scala.util.{Failure, Success, Try}

/** Object that manage data export */
object ExportHandler extends DataStorageHandler:

  /**
   * Export data to personal directory if possible
   * Path and name are predetermined in [[Configuration]]
   *
   * @param data the data to export
   * @tparam A
   * @return a Try[String] with file path
   */
  def exportDataToPersonalDirectory[A](data: A): Try[String] = data match
    case session: Session =>
      exportSessionToPersonalDirectory(session)
    case savedCourseList: List[SavedCourse] =>
      exportSavedCourseListToPersonalDirectory(savedCourseList)
    case playerStats: PlayerStats =>
      exportPlayerStatsToPersonalDirectory(playerStats)

  /**
   * Export the list of [[SavedCourse]] to chosen directory
   * Name of file is predetermined in [[Configuration]]
   *
   * @param session
   * @return a Try[String] with file path
   */
  def exportSavedCoursesToPath(savedCourseList: List[SavedCourse], filePath: String): Try[String] =
    val path: String = filePath + FileSeparator + PlayerCoursesFileName
    storeDataToPath(savedCourseList, path)

  /**
   * Export personal [[Session]] to personal directory.
   *
   * @param session
   * @return a Try[String] with directory path
   */
  private def exportSessionToPersonalDirectory(session: Session): Try[String] =
    Try {
      exportSavedCourseListToPersonalDirectory(session.savedCourses)
      exportPlayerStatsToPersonalDirectory(session.playerStats)
      PlayerDataDirectoryPath
    }


  /**
   * Export the [[PlayerStats]] in a file inside the personal directory.
   *
   * @param session
   * @return a Try[String] with file path
   */
  private def exportPlayerStatsToPersonalDirectory(playerStats: PlayerStats): Try[String] =
    storeDataToPath(playerStats, PlayerStatsFilePath)


  /**
   * Export the [[SavedCourse]] list in a file inside the personal directory.
   *
   * @param session
   * @return a Try[String] with file path
   */
  private def exportSavedCourseListToPersonalDirectory(savedCourseList: List[SavedCourse]): Try[String] =
    exportSavedCoursesToPath(savedCourseList: List[SavedCourse], PlayerDataDirectoryPath)

