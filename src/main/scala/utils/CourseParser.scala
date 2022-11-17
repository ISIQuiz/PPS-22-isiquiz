package utils

import model.Answer.Answer
import model.Quiz.Quiz
import model.{CourseIdentifier, SavedCourse}
import play.api.libs.json.*
import utils.CourseJsonLabels.*

/** Trait for the [[SavedCourse]] parser */
trait CourseParser:

  /**
   * Serialize the SavedCourse list in a JSON string
   * @param savedCourseList the saved course list
   * @return a JSON string
   */
  def serializeSavedCourses(savedCourseList: List[SavedCourse]): String

  /**
   * Deserialize the JSON string in a SavedCourse list
   * @param jsonString the string to deserialize
   * @return a saved course list
   */
  def deserializeSavedCourses(jsonString: String): List[SavedCourse]

/** Companion object of [[SavedCourse]] parser*/
object CourseParser:

  /** Creates a new [[CourseParser]] */
  def apply(): CourseParser = new CourseParserImpl()

  // Implementation of CourseParser trait
  private class CourseParserImpl extends CourseParser:

    override def serializeSavedCourses(savedCourses: List[SavedCourse]): String =
      Json.prettyPrint(JsArray(savedCourses.map(serializeObject)))

    // Serialize an object based on his type 
    private def serializeObject[T](element: T): JsObject = element match
      case savedCourse: SavedCourse =>
        JsObject(
          Seq(
            CourseIdentifierLabel -> JsObject(
              Seq(
                CourseNameLabel -> JsString(savedCourse.courseId.courseName),
                DegreeNameLabel -> JsString(savedCourse.courseId.degreeName),
                UniversityNameLabel -> JsString(savedCourse.courseId.universityName)
              )
            ),
            DescriptionLabel -> JsString(savedCourse.description.getOrElse("")),
            QuizListLabel -> JsArray(savedCourse.quizList.map(serializeObject))
          )
        )
      case quiz: Quiz =>
        JsObject(
          Seq(
            QuestionLabel -> JsString(quiz.question),
            AnswerListLabel -> JsArray(quiz.answerList.map(serializeObject)),
            MaxScoreLabel -> JsNumber(quiz.maxScore),
            ImagePathLabel -> JsString(quiz.imagePath.getOrElse(""))
          )
        )
      case answer: Answer =>
        JsObject(
          Seq(
            TextLabel -> JsString(answer.text),
            IsCorrectLabel -> JsBoolean(answer.isCorrect)
          )
        )
      case _ => JsObject.empty

    
    override def deserializeSavedCourses(jsonString: String): List[SavedCourse] =
      val jsonArray = Json.parse(jsonString).as[JsArray]
      jsonArray.value.map(
        course =>
          SavedCourse(
            courseId = deserializeCourseIdentifier((course \ CourseIdentifierLabel).as[JsObject]),
            description = (course \ DescriptionLabel).asOpt[String],
            quizList = deserializeQuizList((course \ QuizListLabel).as[JsArray])
        )
      ).toList

    // Deserialize the JSON object in a CourseIdentifier
    private def deserializeCourseIdentifier(jsonObject: JsObject): CourseIdentifier =
      CourseIdentifier(
        courseName = (jsonObject \ CourseNameLabel).as[String],
        degreeName = (jsonObject \ DegreeNameLabel).as[String],
        universityName = (jsonObject \ UniversityNameLabel).as[String]
      )

    // Deserialize the JSON array in a list of quiz
    private def deserializeQuizList(jsonArray: JsArray): List[Quiz] =
      jsonArray.value.map(
        quiz =>
          Quiz(
            question = (quiz \ QuestionLabel).as[String],
            answerList = deserializeAnswerList((quiz \ AnswerListLabel).as[JsArray]),
            maxScore = (quiz \ MaxScoreLabel).as[Int],
            imagePath = (quiz \ ImagePathLabel).asOpt[String],
          )
      ).toList

    // Deserialize the JSON array in a list of answers
    private def deserializeAnswerList(jsonArray: JsArray): List[Answer] =
      jsonArray.value.map(
        answer =>
          Answer(
            text = (answer \ TextLabel).as[String],
            isCorrect = (answer \ IsCorrectLabel).as[Boolean]
          )
      ).toList
