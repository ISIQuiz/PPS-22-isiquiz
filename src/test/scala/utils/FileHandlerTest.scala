package utils

import org.scalatest.funsuite.AnyFunSuite
import utils.storage.Configuration.*
import utils.storage.FileHandler
import java.io.FileNotFoundException
import scala.util.{Failure, Success}

class FileHandlerTest extends AnyFunSuite:

  test("Test if can read a existent file") {
    val testString: String = "This is a test string"
    val testPath: String = HomeDirectoryPath + FileSeparator + "test_write_file"
    FileHandler.writeFile(testPath, testString)
    FileHandler.readFile(testPath) match
      case Success(string) => assert(string == testString)
      case _ => fail()
  }

  test("Test if a non existent file return error") {
    FileHandler.readFile("file-not-exist") match
      case Failure(exception: FileNotFoundException) => succeed
      case _ => fail()
  }

  test("Test if checkFileExists works"){
    val existentDirPath = CurrentDirectoryPath + FileSeparator + "src"
    val existentFilePath = CurrentDirectoryPath + FileSeparator + "build.sbt"

    FileHandler.checkFileExists(existentDirPath)
    FileHandler.checkFileExists(existentFilePath)
  }

  test("Test if it creates a new directory") {
    val dirPath = CurrentDirectoryPath + FileSeparator + "testDirectory"
    FileHandler.createDirectory(dirPath)

    FileHandler.checkFileExists(dirPath)
  }
