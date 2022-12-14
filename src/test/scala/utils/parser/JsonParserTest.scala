package utils.parser

import model.SavedCourse
import model.stats.PlayerStats.PlayerStats
import org.scalatest.funsuite.AnyFunSuite
import utils.storage.DefaultDataList.defaultCourseList
import utils.parser.{CourseJsonParser, JsonParser, StatsJsonParser}
import model.SavedCourse.*
import scala.util.{Failure, Success}

class JsonParserTest extends AnyFunSuite:

  val courseJsonParser: CourseJsonParser = CourseJsonParser()
  val statsJsonParser: StatsJsonParser = StatsJsonParser()
  val savedCourseList: List[SavedCourse] = defaultCourseList
  val playerStats = PlayerStats()

  test("Test serializing and deserializing JSON string of saved courses") {
    courseJsonParser.deserialize(JsonParser.toString(courseJsonParser.serialize(savedCourseList))) match
      case Success(s) => s.equals(savedCourseList)
      case _ => fail()
  }

  test("Test serializing and deserializing JSON string of player stats") {
    statsJsonParser.deserialize(JsonParser.toString(statsJsonParser.serialize(playerStats))) match
      case Success(p) => p.equals(playerStats)
      case _ => fail()
  }
