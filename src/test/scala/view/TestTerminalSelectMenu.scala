package view

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}
import controller.{AppController, Controller, MainMenuController, Page, SelectMenuController, StandardGameController}
import view.terminalUI.TerminalMainMenu.*
import view.terminalUI.TerminalSelectMenu
import view.terminalUI.TerminalSelectMenu.*
import view.terminalUI.TerminalStandardGameMenu.*

object TestTerminalSelectMenu:
  private var _input: String = null
  def input: String = _input
  def input_(input: String): Unit = _input = input

class TestTerminalSelectMenu extends AnyFunSuite with BeforeAndAfterAll with BeforeAndAfterEach:
  import TestTerminalSelectMenu.*

  class TerminalSelectMenuTest extends TerminalSelectMenu:
    override def inputReader() = input

  var terminalSelectMenu: TerminalSelectMenu = new TerminalSelectMenuTest

  override def beforeEach(): Unit = {
    super.beforeEach()
    AppController.handle(AppController.SelectMenuAction)
  }

  test("Selecting back should return to main menu") {
    input_("1")
    assert(AppController.currentPage.isInstanceOf[Page[MainMenuController, TerminalSelectMenu]])
  }

  test("Selecting start should start a quiz") {
    input_("2")
    assert(AppController.currentPage.isInstanceOf[Page[StandardGameController, TerminalSelectMenu]])
  }

end TestTerminalSelectMenu
