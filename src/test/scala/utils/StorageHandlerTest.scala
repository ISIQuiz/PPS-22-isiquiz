package utils

import controller.AppController.session
import model.Session
import org.scalatest.funsuite.AnyFunSuite
import java.io.FileNotFoundException
import scala.util.{Failure, Success}

class StorageHandlerTest extends AnyFunSuite:
  val session = Session()

  test("Test if non existent player_data file return error") {
    StorageHandler.loadSessionFromFile(session) match
      case Failure(exception: FileNotFoundException) =>
        println(exception)
        succeed
      case _ => fail()
  }

  test("Test if can store and load session in player_data file") {
    StorageHandler.storeSessionToFile(session)
    StorageHandler.loadSessionFromFile(session) match
      case Success(s) => s.equals(session)
      case Failure(f) => fail()
  }

