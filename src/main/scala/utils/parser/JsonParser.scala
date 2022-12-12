package utils.parser

import model.CourseIdentifier.CourseIdentifier
import play.api.libs.json.*
import utils.parser.JsonLabels.{CourseNameLabel, DegreeNameLabel, UniversityNameLabel}
import scala.util.Try

/** Trait for the [[JsonParser]] */
trait JsonParser[A]:

  /**
   * Serialize a generic type in a JSON Object
   *
   * @param element generic element to serialize
   * @return JSON Object
   */
  def serialize(element: A): JsObject

  /**
   * Deserialize the JSON string in a Try generic type
   *
   * @param string JSON string
   * @return a Try with the object deserialized
   */
  def deserialize(string: String): Try[A]

  /**
   * Course identifier serializer
   * @param courseIdentifier course identifier
   * @return a JsObject
   */
  def serializeCourseIdentifier(courseIdentifier: CourseIdentifier): JsObject =
    JsObject(
      Seq(
        CourseNameLabel -> JsString(courseIdentifier.courseName),
        DegreeNameLabel -> JsString(courseIdentifier.degreeName),
        UniversityNameLabel -> JsString(courseIdentifier.universityName)
      )
    )

  /**
   * Course identifier deserializer
   * @param jsonObject course identifier as JsOgject
   * @return a course identifier
   */
  def deserializeCourseIdentifier(jsonObject: JsObject): CourseIdentifier =
    CourseIdentifier(
      courseName = (jsonObject \ CourseNameLabel).as[String],
      degreeName = (jsonObject \ DegreeNameLabel).as[String],
      universityName = (jsonObject \ UniversityNameLabel).as[String]
    )

/** Companion object of [[JsonParser]] trait */
object JsonParser:

  /**
   * A string formatter for generic JSON Value
   *
   * @param jsonValue the JSON Value to convert
   * @return a well formatted JSON string
   */
  def toString(jsonValue: JsValue): String =
    Json.prettyPrint(jsonValue)
