package utils

import controller.AppController
import model.Answer.Answer
import model.Quiz.Quiz
import model.stats.{CourseInStats, QuizInStats}
import model.stats.PlayerStats.{PlayerStats, initStats}
import model.{Course, SavedCourse, Session}
import play.api.libs.json.JsObject
import utils.Configuration.*
import utils.StorageHandler.loadDataFromFile
import utils.parser.{CourseJsonParser, JsonParser, StatsJsonParser}

import java.io.FileNotFoundException
import scala.util.{Failure, Success, Try}

object StorageHandler:

  // EXPORT DATA ---------------------------------------------------------------------------------------------

  /**
   * Store a [[JsObject]] as string in a file
   *
   * @param jsonObject
   * @param path where store the file
   * @return a Try[Unit]
   */
  private def storeObjectToPath(jsonObject: JsObject, path: String): Try[Unit] =
    FileHandler.writeFile(path, JsonParser.toString(jsonObject))


  /**
   * Store JSON parsed data in a file depending on which [[JsonParser]] type is passed
   *
   * @param session
   * @param jsonParser
   * @param filePath
   * @tparam A
   * @return a Try[Unit]
   */
  private def storeDataToPath[A](session: Session, jsonParser: JsonParser[A], filePath: String): Try[Unit] =
    FileHandler.createDirectory(PlayerDataDirectoryPath)
    storeObjectToPath(
      jsonParser match
        case courseJsonParser: CourseJsonParser => courseJsonParser.serialize(session.savedCourses)
        case statsJsonParser: StatsJsonParser => statsJsonParser.serialize(session.playerStats)
      ,
      filePath
    )


  /**
   * Export personal [[Session]] to personal directory.
   * Paths and names are predetermined
   *
   * @param session
   * @return a Try[String] with the path of the directory
   */
  def exportSessionToPersonalDirectory(session: Session): Try[String] =
    Try {
      exportSavedCourseListToPersonalDirectory(session)
      exportPlayerStatsToPersonalDirectory(session)
      PlayerDataDirectoryPath
    }

  /**
   * Export the [[SavedCourse]] list in a file inside the personal directory.
   * Path and name are predetermined
   *
   * @param session
   * @return a Try[Unit]
   */
  def exportSavedCourseListToPersonalDirectory(session: Session): Try[Unit] =
    storeDataToPath(session, CourseJsonParser(), PlayerCoursesFilePath)

  /**
   * Export the [[PlayerStats]] in a file inside the personal directory.
   * Path and name are predetermined
   *
   * @param session
   * @return a Try[Unit]
   */
  def exportPlayerStatsToPersonalDirectory(session: Session): Try[Unit] =
    storeDataToPath(session, StatsJsonParser(), PlayerStatsFilePath)

  /**
   * Export the list of [[SavedCourse]] to choosen directory
   *
   * @param session
   * @return a Try[String] with the path of the file
   */
  def exportSavedCoursesToPath(session: Session, filePath: String): Try[String] =
    val path: String = filePath + FileSeparator + PlayerCoursesFileName
    Try {
      storeDataToPath(session, CourseJsonParser(), path)
      path
    }


  // IMPORT DATA ----------------------------------------------------------------------------------------

  /**
   * Load data from a file and return the object depending on which [[JsonParser]] type is passed
   *
   * @param jsonParser
   * @param filePath
   * @tparam A
   * @return a Try[A]
   */
  private def loadDataFromFile[A](jsonParser: JsonParser[A], filePath: String): Try[A] =
    FileHandler.readFile(filePath) match
      case Success(jsonString: String) =>
        for {
          jsonParser <- jsonParser.deserialize(jsonString)
        } yield {
          jsonParser match
            case savedCourseList: List[SavedCourse] => savedCourseList
            case playerStats: PlayerStats => playerStats
        }
      case Failure(f) => Failure(f)

  /**
   * Import the [[Session]] from personal directory.
   * Path and name are predetermined
   *
   * @param session
   * @return an updated Try[Session]
   */
  def importSessionFromPersonalDirectory(session: Session): Try[Session] =
  // Load player courses and deserialize the JSON string in a SavedCourse list
    loadDataFromFile(CourseJsonParser(), PlayerCoursesFilePath) match
      case Success(savedCourseList: List[SavedCourse]) => // Parse SavedCourse list success
        val newSessionCourses = Session.changeSavedCourses(session, savedCourseList) // update Session saved course list

        // Load player stats and deserialize the JSON string in PlayerStats
        loadDataFromFile(StatsJsonParser(), PlayerStatsFilePath) match
          case Success(playerStats: PlayerStats) => // Success import courses and stats from file
            println("courses and stats imported from player data")
            Success(Session.changePlayerStats(newSessionCourses, playerStats))
          case Failure(f) => // Failed to parse player stats, reset to default player stats
            println("stats not exist, created file with init stats from courses")
            val newSession = Session.changePlayerStats(newSessionCourses, initStats)
            storeDataToPath(newSession, StatsJsonParser(), PlayerStatsFilePath)
            Failure(f)

      case Failure(f) => // Failed to parse savedCourse from file, store passed session to file
        println("courses and stats not exists, created default session")
        exportSessionToPersonalDirectory(session)
        Failure(f)


  /**
   * Import the list of [[SavedCourse]] from file
   *
   * @param session
   * @param filePath path chosen by user
   * @return a Try with a list of saved course
   */
  def importSavedCourseListFromFile(session: Session, filePath: String): Try[List[SavedCourse]] =
    for
      savedCourseList <- loadDataFromFile(CourseJsonParser(), filePath)
    yield
      mergeSavedCourseLists(session.savedCourses, savedCourseList)

  /**
   * Merge the elements of two list of [[SavedCourses]] using [[groupMapReduce]].
   * If two or more elements have the same id, it checks if their value are the same,
   * if not it takes the values of the first element and ignore the others
   * @param savedCourseList
   * @param newSavedCourseList
   * @return a resulting List[SavedCourse]
   */
  def mergeSavedCourseLists(savedCourseList: List[SavedCourse], newSavedCourseList: List[SavedCourse]): List[SavedCourse] =
    (savedCourseList ::: newSavedCourseList).groupMapReduce(sc => sc.courseId)(sc => (sc.description, sc.quizList))(
      (savedCourse, newSavedCourse) => (savedCourse._1, mergeQuizLists(savedCourse._2, newSavedCourse._2))
    ).map((k, v) => SavedCourse(k, v._1, v._2)).toList

  // Used by mergeSavedCourseLists to merge quiz lists
  private def mergeQuizLists(quizList: List[Quiz], newQuizList: List[Quiz]): List[Quiz] =
    (quizList ::: newQuizList).groupMapReduce(q => q.quizId)(q => (q.question, q.answerList, q.maxScore, q.imagePath))(
      (quiz, newQuiz) => (quiz._1, mergeAnswerLists(quiz._2, newQuiz._2), quiz._3, quiz._4)
    ).map((k, v) => Quiz(k, v._1, v._2, v._3, v._4)).toList

  // Used by mergeQuizLists to merge answer lists
  private def mergeAnswerLists(answerList: List[Answer], newAnswerList: List[Answer]): List[Answer] =
    (answerList ::: newAnswerList).groupMapReduce(a => a.text)(a => a.isCorrect)(
      (answer, newAnswer) => answer
    ).map((k, v) => Answer(k, v)).toList

  /**
   * Reset all the personal stats to 0
   * @param session
   * @return an updated Try[Session]
   */
  def resetPersonalStatsFile(session: Session): Try[Session] =
    Try {
      val newSession = Session.changePlayerStats(session, initStats)
      storeDataToPath(newSession, StatsJsonParser(), PlayerStatsFilePath)
      newSession
    }

//def updatePersonalStats(session: Session, playerStats: PlayerStats)