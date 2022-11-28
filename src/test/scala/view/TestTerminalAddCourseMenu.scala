package view

import controller.actions.Action
import controller.*
import model.Answer.Answer
import model.CourseIdentifier.CourseIdentifierImpl
import model.GameStage
import model.SavedCourse.SavedCourseImpl
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}
import view.terminalUI.TerminalAddCourseMenu
import view.terminalUI.TerminalMainMenu.*
import view.terminalUI.TerminalSelectMenu.*
import view.terminalUI.TerminalAddCourseMenu.*
import view.updates.ParameterlessViewUpdate


class TestTerminalAddCourseMenu extends AnyFunSuite with BeforeAndAfterAll with BeforeAndAfterEach:

  var inputsIterator = Iterator("")

  class TerminalAddCourseMenuTest extends TerminalAddCourseMenu:
    override def inputReader() = inputsIterator.next()

  var terminalAddCourseMenu: TerminalAddCourseMenu = new TerminalAddCourseMenuTest
