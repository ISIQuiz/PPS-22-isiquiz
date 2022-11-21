package view

import controller.actions.Action
import controller.*
import controller.AddQuizMenuController.AddQuizAction
import model.Answer.Answer
import model.CourseIdentifier.CourseIdentifierImpl
import model.GameStage
import model.Quiz.Quiz
import model.SavedCourse.SavedCourseImpl
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}
import view.MainMenuView.*
import view.SelectMenuView.*
import view.AddQuizMenuView.*
import view.updates.ParameterlessViewUpdate


class TestAddQuizMenuView extends AnyFunSuite with BeforeAndAfterAll with BeforeAndAfterEach:

  var inputsIterator = Iterator("")

  class AddQuizMenuViewTest extends AddQuizMenuViewImpl:
    override def inputReader() = inputsIterator.next()

  var addQuizMenuView: AddQuizMenuView = new AddQuizMenuViewTest


  test("Default Print should be drawn") {
    assert(addQuizMenuView.draw(AddQuizMenuView.DefaultPrint) == "DefaultPrint")
  }


