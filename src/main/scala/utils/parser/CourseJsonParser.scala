package utils.parser

import model.Answer.Answer
import model.Quiz.Quiz
import model.SavedCourse.SavedCourse
import model.CourseIdentifier.CourseIdentifier
import model.{Course, Session}
import play.api.libs.json.*
import utils.parser.JsonLabels.*
import java.util.UUID
import scala.util.Try

/** Trait for the [[CourseJsonParser]] parser */
trait CourseJsonParser extends JsonParser[List[SavedCourse]]

/** Companion object of [[SavedCourse]] parser */
object CourseJsonParser:

  /** Creates a new [[CourseJsonParser]] */
  def apply(): CourseJsonParser = new CourseJsonParserImpl()

  // Implementation of CourseJsonParser trait
  private class CourseJsonParserImpl extends CourseJsonParser :

    override def serialize(savedCourseList: List[SavedCourse]): JsObject =
      JsObject(Seq(SavedCourseListLabel -> JsArray(savedCourseList.map(serializeObjectOfSavedCourse))))

    // Serialize an object based on his type
    private def serializeObjectOfSavedCourse[A](element: A): JsObject =
      element match
        case savedCourseList: List[SavedCourse] =>
          JsObject(Seq(
            SavedCourseListLabel -> JsArray(savedCourseList.map(serializeObjectOfSavedCourse))
          ))
        case savedCourse: SavedCourse => // SavedCourse
          JsObject(Seq(
            CourseIdentifierLabel -> serializeObjectOfSavedCourse(savedCourse.courseId),
            DescriptionLabel -> JsString(savedCourse.description.getOrElse("")),
            QuizListLabel -> JsArray(savedCourse.quizList.map(serializeObjectOfSavedCourse))
          ))
        case quiz: Quiz => // Quiz
          JsObject(Seq(
            QuizIdLabel -> JsString(quiz.quizId.toString),
            QuestionLabel -> JsString(quiz.question),
            AnswerListLabel -> JsArray(quiz.answerList.map(serializeObjectOfSavedCourse)),
            MaxScoreLabel -> JsNumber(quiz.maxScore),
            ImagePathLabel -> JsString(quiz.imagePath.getOrElse(""))
          ))
        case answer: Answer => // Answer
          JsObject(Seq(
            TextLabel -> JsString(answer.text),
            IsCorrectLabel -> JsBoolean(answer.isCorrect)
          ))
        case courseIdentifier: CourseIdentifier =>
          serializeCourseIdentifier(courseIdentifier)

        case _ => JsObject.empty


    override def deserialize(string: String): Try[List[SavedCourse]] =
      for {
        // Check if string is empty
        _ <- Try(if (string.isEmpty) throw IllegalArgumentException())
        // Check if json file is parsable
        jsonObject <- Try(Json.parse(string).as[JsObject])
      } yield {
        deserializeObjectOfSavedCourse((jsonObject \ SavedCourseListLabel).as[JsArray], SavedCourseListLabel).asInstanceOf[List[SavedCourse]]
      }

    // Deserialize an object based on his type
    private def deserializeObjectOfSavedCourse(jsonArray: JsArray, label: String): List[SavedCourse] | List[Quiz] | List[Answer] =
      (jsonArray, label) match
        case (jsonArray, SavedCourseListLabel) =>
          jsonArray.value.map(
            course =>
              SavedCourse(
                courseId = deserializeCourseIdentifier((course \ CourseIdentifierLabel).as[JsObject]),
                description = (course \ DescriptionLabel).asOpt[String],
                quizList = deserializeObjectOfSavedCourse((course \ QuizListLabel).as[JsArray], QuizListLabel).asInstanceOf[List[Quiz]]
              )
          ).toList
        case (jsonArray, QuizListLabel) =>
          jsonArray.value.map(
            quiz =>
              Quiz(
                quizId = UUID.fromString((quiz \ QuizIdLabel).as[String]),
                question = (quiz \ QuestionLabel).as[String],
                answerList = deserializeObjectOfSavedCourse((quiz \ AnswerListLabel).as[JsArray], AnswerListLabel).asInstanceOf[List[Answer]],
                maxScore = (quiz \ MaxScoreLabel).as[Int],
                imagePath = (quiz \ ImagePathLabel).asOpt[String],
              )
          ).toList
        case (jsonArray, AnswerListLabel) =>
          jsonArray.value.map(
            answer =>
              Answer(
                text = (answer \ TextLabel).as[String],
                isCorrect = (answer \ IsCorrectLabel).as[Boolean]
              )
          ).toList
        case _ => List()
