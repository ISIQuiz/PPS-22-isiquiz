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
   * Merge the second object into the first object. It's similar to JSON play library [[JsObject.deepMerge]] method.
   * The key difference is that can merge nested JsArrays if starting JsObjects have a similar structure
   *
   * @param firstObject  first JsObject to be merged
   * @param secondObject second JsObject to be merged
   * @return a merged JsObject
   */
  def merge(firstObject: JsObject, secondObject: JsObject): JsObject =
    val result = firstObject.fields ++ secondObject.fields.map {
      case (secondKey, secondValue) =>
        val maybeExistingValue = firstObject.value.get(secondKey)
        // if exist two value with same key do merge
        val newValue = (maybeExistingValue, secondValue) match {
          case (Some(f: JsObject), s: JsObject) => merge(f, s) // merge JsObjects
          case (Some(f: JsArray), s: JsArray) => JsArray(f.value.zip(s.value).map { // merge every JsObjects of JsArray
            case (f: JsObject, s: JsObject) => merge(f, s)
            case _ => JsObject.empty
          })
          case _ => secondValue
        }
        secondKey -> newValue
    }
    JsObject(result)

  /**
   * A string formatter for generic JSON Value
   *
   * @param jsonValue the JSON Value to convert
   * @return a well formatted JSON string 
   */
  def toString(jsonValue: JsValue): String =
    Json.prettyPrint(jsonValue)
