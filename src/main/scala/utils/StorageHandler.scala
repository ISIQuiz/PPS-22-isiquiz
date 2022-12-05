package utils

import model.stats.{CourseInStats, QuizInStats}
import model.stats.PlayerStats.PlayerStats
import model.{Course, SavedCourse, Session}
import play.api.libs.json.JsObject
import utils.Configuration.*
import utils.DefaultDataList.defaultPlayerStats
import utils.parser.{CourseJsonParser, JsonParser, StatsJsonParser}

import java.io.FileNotFoundException
import scala.util.{Failure, Success, Try}

object StorageHandler:

  private def storeObjectToPath(jsonObject: JsObject, path: String): Try[Unit] =
    FileHandler.writeFile(path, JsonParser.toString(jsonObject))


  private def storeDataToDirectory[A](session: Session, jsonParser: JsonParser[A], filePath: String): Try[Unit] =
    FileHandler.createDirectory(PlayerDataDirectoryPath)
    storeObjectToPath(
      jsonParser match
        case courseJsonParser: CourseJsonParser => courseJsonParser.serialize(session.savedCourses)
        case statsJsonParser: StatsJsonParser => statsJsonParser.serialize(session.playerStats)
      ,
      filePath
    )

  /**
   * Export personal session to personal folder
   *
   * @param session
   * @return a Try
   */
  def exportSessionToDirectory(session: Session): Try[Unit] =
    storeDataToDirectory(session, CourseJsonParser(), PlayerCoursesFilePath)
    storeDataToDirectory(session, StatsJsonParser(), PlayerStatsFilePath)

  /**
   * Export saved courses to choosen directory
   *
   * @param session
   * @return a Try
   */
  def exportSavedCoursesToPersonalDirectory(session: Session, filePath: String): Try[Unit] =
    storeDataToDirectory(session, CourseJsonParser(), filePath + FileSeparator + PlayerCoursesFileName)


/*
  /**
   * Store [[Session]] in a JSON file
   *
   * @param session
   */
  def storeSessionToDirectory(session: Session): Unit =

    val courseJsonParser = CourseJsonParser()
    val statsJsonParser = StatsJsonParser()

    FileHandler.createDirectory(PlayerDataDirectoryPath)

    storeObjectToPath(
      JsonParser.merge(statsJsonParser.serialize(session.playerStats), courseJsonParser.serialize(session.savedCourses)),
      SessionFilePath
    )


  /**
   * Load [[Session]] from JSON file. If no file found it generates the default one
   *
   * @param session
   * @return a Try[Session]
   */
  def loadSessionFromFile(session: Session): Try[Session] =
    val courseJsonParser = CourseJsonParser()
    val statsJsonParser = StatsJsonParser()

    // Load player data file
    FileHandler.readFile(SessionFilePath) match
      case Success(jsonString: String) => // Read file success

        // Deserialize the JSON string in a SavedCourse list
        courseJsonParser.deserialize(jsonString) match
          case Success(savedCourseList: List[SavedCourse]) => // Parse SavedCourse list success
            val newSession = Session.changeSavedCourses(session, savedCourseList) // update Session saved course list

            // Deserialize the JSON string in PlayerStats
            statsJsonParser.deserialize(jsonString) match
              case Success(playerStats: PlayerStats) =>
                Success(Session.changePlayerStats(newSession, playerStats))
              case Failure(f) => // Failed to parse player stats, reset to default player stats based on saved course list and store to file
                val playerStats = defaultPlayerStatsFromSavedCourseList(newSession.savedCourses)
                storeSessionToDirectory(Session.changePlayerStats(newSession, playerStats))
                Failure(f)

          case Failure(f) => // Failed to parse savedCourse from file, store passed session to file
            storeSessionToDirectory(session)
            Failure(f)

      case Failure(f: FileNotFoundException) => // Failed to read from file, write a new one with session passed
        storeSessionToDirectory(session)
        Failure(f)
        */
