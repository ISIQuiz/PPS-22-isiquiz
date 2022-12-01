package utils

import model.stats.{CourseInStats, QuizInStats}
import model.stats.PlayerStats.{PlayerStats, defaultPlayerStatsFromSavedCourseList}
import model.{Course, SavedCourse, Session}
import utils.Configuration.{CurrentDirectoryPath, PlayerDataFileName, SavedCoursesFilePath}
import utils.parser.{CourseJsonParser, JsonParser, StatsJsonParser}

import java.io.FileNotFoundException
import scala.util.{Failure, Success, Try}

object StorageHandler:
  
  /**
   * Store [[Session]] in a JSON file
   * @param session
   */
  def storeSessionToFile(session: Session): Unit =
    val fileHandler = FileHandler()
    val courseJsonParser = CourseJsonParser()
    val statsJsonParser = StatsJsonParser()

    fileHandler.writeFile(CurrentDirectoryPath + PlayerDataFileName,
      JsonParser.toString(
        JsonParser.merge(statsJsonParser.serialize(session.playerStats), courseJsonParser.serialize(session.savedCourses))
      )
    )

  /**
   * Load [[Session]] from JSON file. If no file found it generates the default one
   * @param session
   * @return a Try[Session]
   */
  def loadSessionFromFile(session: Session): Try[Session] =
    val fileHandler = FileHandler()
    val courseJsonParser = CourseJsonParser()
    val statsJsonParser = StatsJsonParser()

    // Load player data file
    fileHandler.readFile(CurrentDirectoryPath + PlayerDataFileName) match
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
                storeSessionToFile(Session.changePlayerStats(newSession, playerStats))
                Failure(f)

          case Failure(f) => // Failed to parse savedCourse from file, store passed session to file
            storeSessionToFile(session)
            Failure(f)

      case Failure(f: FileNotFoundException) => // Failed to read from file, write a new one with session passed
        storeSessionToFile(session)
        Failure(f)