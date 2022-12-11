package utils.storage

import model.Answer.Answer
import model.Quiz.Quiz
import model.stats.PlayerStats.{PlayerStats, initStats}
import model.{Session}
import model.SavedCourse.SavedCourse
import play.api.libs.json.JsObject
import utils.parser.{CourseJsonParser, JsonParser, StatsJsonParser}
import utils.storage.Configuration.{PlayerDataDirectoryPath, PlayerStatsFilePath}
import scala.util.{Failure, Success, Try}

/** Trait fo Data store management */
trait DataStorageHandler:

  /**
   * Store a [[JsObject]] as string in a file
   *
   * @param jsonObject
   * @param path where store the file
   * @return a Try[String] with file path
   */
  private def storeObjectToPath(jsonObject: JsObject, path: String): Try[String] =
    FileHandler.writeFile(path, JsonParser.toString(jsonObject))

  /**
   * Store JSON parsed data in a file depending on which [[JsonParser]] type is passed
   *
   * @param data
   * @param filePath
   * @tparam A
   * @return a Try[String] with file path
   */
  def storeDataToPath[A](data: A, filePath: String): Try[String] =
    FileHandler.createDirectory(PlayerDataDirectoryPath)
    storeObjectToPath(
      data match
        case savedCourseList: List[SavedCourse] => CourseJsonParser().serialize(savedCourseList)
        case playerStats: PlayerStats => StatsJsonParser().serialize(playerStats)
      ,
      filePath
    )

  /**
   * Load data from a file and return the object depending on which [[JsonParser]] type is passed
   *
   * @param jsonParser
   * @param filePath
   * @tparam A
   * @return a Try[A]
   */
  def loadDataFromFile[A](jsonParser: JsonParser[A], filePath: String): Try[A] =
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


/** Companion object of DataStorageHandler trait */
object DataStorageHandler:

  /**
   * Merge the elements of two list of [[SavedCourses]] using [[List.groupMapReduce]].
   * If two or more elements have the same id, it checks if their value are the same,
   *
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

