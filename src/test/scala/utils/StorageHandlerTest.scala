package utils

import controller.AppController.session
import model.Session
import org.scalatest.funsuite.AnyFunSuite
import utils.Configuration.HomeDirectoryPath

import java.io.FileNotFoundException
import java.nio.file.FileStore
import scala.util.{Failure, Success}

class StorageHandlerTest extends AnyFunSuite:
  val session = Session()

  // TODO da fare in importa
  /*test("Test if non existent player_data file return error") {
    StorageHandler.loadSessionFromFile(session) match
      case Failure(exception: FileNotFoundException) =>
        println(exception)
        succeed
      case _ => fail()
  }*/

  test("Test if can store session file in player directory") {
    StorageHandler.exportSessionToDirectory(session) match
      case Success(s) => succeed
      case Failure(f) => fail()
  }

  test("Test if can store saved courses in chosen path") {
    StorageHandler.exportSavedCoursesToPersonalDirectory(session, HomeDirectoryPath) match
      case Success(s) => succeed
      case Failure(f) => fail()
  }

