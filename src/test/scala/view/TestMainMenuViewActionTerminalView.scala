package view

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}
import controller.{AppController, Controller, MainMenuController, Page, SelectMenuController, SettingsMenuController, StatisticsMenuController}
import view.terminalUI.TerminalMainMenu.*
import view.terminalUI.TerminalSelectMenu.*
import view.terminalUI.TerminalStatisticsMenu.*
import view.terminalUI.TerminalSettingsMenu.*

object TestMainMenuViewActionTerminalView:
  private var _input: String = null
  def input: String = _input
  def input_(input: String): Unit = _input = input

class TestMainMenuViewActionTerminalView extends AnyFunSuite with BeforeAndAfterAll with BeforeAndAfterEach:
  import TestMainMenuViewActionTerminalView.*

  class TerminalViewTest extends TerminalMainMenu:
    override def inputReader() = input

  var mainMenuView: MainMenuTerminalView = new TerminalViewTest

  override def beforeEach(): Unit = {
    super.beforeEach()
    AppController.handle(AppController.MainMenuAction)
  }

  test("Should change to select menu when play is selected") {
    input_("1")
    assert(AppController.currentPage.isInstanceOf[Page[SelectMenuController, SelectMenuView]])
  }

end TestMainMenuViewActionTerminalView
