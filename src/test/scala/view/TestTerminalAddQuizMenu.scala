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
import view.terminalUI.TerminalAddQuizMenu
import view.terminalUI.TerminalMainMenu.*
import view.terminalUI.TerminalSelectMenu.*
import view.terminalUI.TerminalAddQuizMenu.*
import view.updates.ParameterlessViewUpdate


class TestTerminalAddQuizMenu extends AnyFunSuite with BeforeAndAfterAll with BeforeAndAfterEach:

  var inputsIterator = Iterator("")

  class TerminalAddQuizMenuTest extends TerminalAddQuizMenu:
    override def inputReader() = inputsIterator.next()

  var terminalAddQuizMenu: TerminalAddQuizMenu = new TerminalAddQuizMenuTest
