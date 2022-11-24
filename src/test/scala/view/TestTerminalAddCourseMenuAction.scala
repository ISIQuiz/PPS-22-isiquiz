package view

import controller.actions.Action
import controller.*
import model.Answer.Answer
import model.CourseIdentifier.CourseIdentifierImpl
import model.GameStage
import model.SavedCourse.SavedCourseImpl
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}
import view.terminalUI.TerminalMainMenu.*
import view.terminalUI.TerminalSelectMenu.*
import view.terminalUI.TerminalAddCourseMenu.*
import view.updates.ParameterlessViewUpdate


class TestTerminalAddCourseMenuAction extends AnyFunSuite with BeforeAndAfterAll with BeforeAndAfterEach:

  var inputsIterator = Iterator("")

  class AddCourseMenuViewTest extends AddCourseMenuViewImpl:
    override def inputReader() = inputsIterator.next()

  var addCourseMenuView: AddCourseMenuView = new AddCourseMenuViewTest
