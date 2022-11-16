package view

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}
import controller.{AppController, Controller, MainMenuController, Page, SelectMenuController, SettingsMenuController, StatisticsMenuController}
import view.MainMenuView.*
import view.SelectMenuView.*
import view.StatisticsMenuView.*
import view.SettingsMenuView.*

object TestMainMenuView:
  private var _input: String = null
  def input: String = _input
  def input_(input: String): Unit = _input = input

class TestMainMenuView extends AnyFunSuite with BeforeAndAfterAll with BeforeAndAfterEach:
  import TestMainMenuView.*

  class MainMenuViewTest extends MainMenuViewImpl:
    override def inputReader() = input

  var mainMenuView: MainMenuView = new MainMenuViewTest

  override def beforeEach(): Unit = {
    super.beforeEach()
    AppController.handle(AppController.MainMenu)
  }

  test("Main menu view should draw main menu") {
    assert(mainMenuView.draw(MainMenuView.DefaultUpdate) == "MainMenu")
  }

  test("Should change to select menu when play is selected") {
    input_("1")
    assert(AppController.currentPage.isInstanceOf[Page[SelectMenuController, SelectMenuView]])
  }

end TestMainMenuView
