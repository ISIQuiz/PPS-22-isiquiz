package utils.parser

import model.Answer.Answer
import model.Quiz.Quiz
import model.stats.CourseInStats.CourseInStats
import model.stats.PlayerStats.PlayerStats
import model.stats.QuizInStats.QuizInStats
import model.CourseIdentifier.CourseIdentifier
import model.{Course, SavedCourse, Session}
import play.api.libs.json.*
import utils.parser.JsonLabels.*
import utils.parser.JsonParser

import java.util.UUID
import scala.util.Try

/** Trait for the [[StatsJsonParser]] parser */
trait StatsJsonParser extends JsonParser[PlayerStats]


/** Companion object of [[SavedCourse]] parser */
object StatsJsonParser:

  /** Creates a new [[StatsJsonParser]] */
  def apply(): StatsJsonParser = new StatsJsonParserImpl()

  // Implementation of StatsJsonParser trait
  private class StatsJsonParserImpl extends StatsJsonParser:

    override def serialize(playerStats: PlayerStats): JsObject =
      serializeObjectOfPlayerStats(playerStats)

    // Serialize an object based on his type
    private def serializeObjectOfPlayerStats[A](element: A): JsObject =
      element match
        case playerStats: PlayerStats =>
          JsObject(
            Seq(
              TotalScoreLabel -> JsNumber(playerStats.totalScore),
              TotalAnsweredQuestionsLabel -> JsNumber(playerStats.totalAnsweredQuestions),
              TotalCorrectAnswersLabel -> JsNumber(playerStats.totalCorrectAnswers),
              TotalAnswerPrecisionLabel -> JsNumber(playerStats.totalAnswerPrecision),
              TotalAverageTimeAnswerLabel -> JsNumber(playerStats.totalAverageTimeAnswer),
              SavedCourseListLabel -> JsArray(playerStats.courseInStatsList.map(serializeObjectOfPlayerStats))
            )
          )
        case courseInStats: CourseInStats => // CourseInStats
          JsObject(
            Seq(
              CourseIdentifierLabel -> serializeObjectOfPlayerStats(courseInStats.course.courseId),
              QuizListLabel -> JsArray(courseInStats.quizInStatsList.map(serializeObjectOfPlayerStats))
            )
          )
        case quizInStats: QuizInStats => // QuizInStats
          JsObject(
            Seq(
              QuizIdLabel -> JsString(quizInStats.quizId.toString),
              TotalSeenLabel -> JsNumber(quizInStats.totalSeen),
              TotalRightAnswersLabel -> JsNumber(quizInStats.totalRightAnswers),
              AverageTimeAnswerLabel -> JsNumber(quizInStats.averageTimeAnswer)
            )
          )
        case courseIdentifier: CourseIdentifier =>
          serializeCourseIdentifier(courseIdentifier)


    override def deserialize(string: String): Try[PlayerStats] =
      for {
        // Check if string is empty
        _ <- Try(if (string.isEmpty) throw IllegalArgumentException())
        // Check if json file is parsable
        jsonObject <- Try(Json.parse(string).as[JsObject])
      } yield {
        deserializeObjectOfPlayerStats(jsonObject, PlayerStatsLabel).asInstanceOf[PlayerStats]
      }

    // Deserialize an object based on his type
    private def deserializeObjectOfPlayerStats(jsonValue: JsValue, label: String): PlayerStats | List[CourseInStats] | List[QuizInStats] =
      (jsonValue, label) match
        case (jsonObject: JsObject, PlayerStatsLabel) =>
          PlayerStats(
            totalScore = (jsonObject \ TotalScoreLabel).as[Int],
            totalAnsweredQuestions = (jsonObject \ TotalAnsweredQuestionsLabel).as[Int],
            totalCorrectAnswers = (jsonObject \ TotalCorrectAnswersLabel).as[Int],
            totalAnswerPrecision = (jsonObject \ TotalAnswerPrecisionLabel).as[Int],
            totalAverageTimeAnswer = (jsonObject \ TotalAverageTimeAnswerLabel).as[Double],
            courseInStatsList = deserializeObjectOfPlayerStats((jsonObject \ SavedCourseListLabel).as[JsArray], SavedCourseListLabel).asInstanceOf[List[CourseInStats]]
          )
        case (jsonArray: JsArray, SavedCourseListLabel) =>
          jsonArray.value.map(
            courseInStats =>
              CourseInStats(
                course = Course(
                  courseId = deserializeCourseIdentifier((courseInStats \ CourseIdentifierLabel).as[JsObject])
                ),
                quizInStatsList = deserializeObjectOfPlayerStats((courseInStats \ QuizListLabel).as[JsArray], QuizListLabel).asInstanceOf[List[QuizInStats]],
              )
          ).toList
        case (jsonArray: JsArray, QuizListLabel) =>
          jsonArray.value.map(
            quizInStats =>
              QuizInStats(
                quizId = UUID.fromString((quizInStats \ QuizIdLabel).as[String]),
                totalSeen = (quizInStats \ TotalSeenLabel).as[Int],
                totalRightAnswers = (quizInStats \ TotalRightAnswersLabel).as[Int],
                averageTimeAnswer = (quizInStats \ AverageTimeAnswerLabel).as[Double]
              )
          ).toList
        case _ => List()