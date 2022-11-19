package utils

import org.scalatest.funsuite.AnyFunSuite
import utils.Configuration.*
import java.io.FileNotFoundException
import scala.util.{Failure, Success}

class FileHandlerTest extends AnyFunSuite:
  val fileHandler: FileHandler = FileHandler()

  test("Test if can read a existent file"){
    val testString: String = "This is a test string"
    val testPath: String = HomeDirectoryPath + "test_write_file"
    fileHandler.writeFile(testPath, testString)
    fileHandler.readFile(testPath) match
      case Success(string) => assert(string == testString)
      case _ => fail()
  }

  test("Test if a non existent file return error") {
    fileHandler.readFile("file-not-exist") match
      case Failure(exception: FileNotFoundException) => succeed
      case _ => fail()
  }

  test("Test if can read a existent resource file and is not empty") {
    fileHandler.readResource(SavedCoursesFilePath) match
      case Success(string) => assert(string.nonEmpty)
      case _ => fail()
  }

  test("Test if a non existent resource file return error") {
    fileHandler.readResource("file-not-exist") match
      case Failure(exception: FileNotFoundException) => succeed
      case _ => fail()
  }