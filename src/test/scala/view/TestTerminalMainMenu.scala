package view

import controller.AppController.MainMenuAction
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}
import controller.{AppController, Controller, MainMenuController, Page, SelectMenuController, SettingsMenuController, StatisticsMenuController}
import view.terminalUI.{TerminalMainMenu, TerminalSelectMenu}
import view.terminalUI.TerminalMainMenu.*
import view.terminalUI.TerminalSelectMenu.*
import view.terminalUI.TerminalStatisticsMenu.*
import view.terminalUI.TerminalSettingsMenu.*

object TestTerminalMainMenu:
  private var _input: String = null
  def input: String = _input
  def input_(input: String): Unit = _input = input

class TestTerminalMainMenu extends AnyFunSuite with BeforeAndAfterAll with BeforeAndAfterEach:
  import TestTerminalMainMenu.*

  class TerminalMainMenuTest extends TerminalMainMenu:
    override def inputReader() = input

  var terminalMainMenu: TerminalMainMenu = new TerminalMainMenuTest

  override def beforeEach(): Unit = {
    super.beforeEach()
    AppController.handle(MainMenuAction)
  }

  test("Should change to select menu when play is selected") {
    input_("1")
    assert(AppController.currentPage.isInstanceOf[Page[SelectMenuController, TerminalSelectMenu]])
  }

end TestTerminalMainMenu
