package viewTest

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}
import controller.Controller.*
import controller.{MainMenuController, SelectMenuController, StatisticsMenuController, SettingsMenuController}
import view.MainMenuView.*
import view.SelectMenu.*
import view.StatisticsMenu.*
import view.SettingsMenu.*

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
    AppController.handle(AppController.AvailablePages.MainMenu, Option.empty)
  }

  test("Main menu view should draw main menu") {
    assert(mainMenuView.draw() == "MainMenu")
  }

  test("Should change to select menu when play is selected") {
    input_("1")
    assert(AppController.currentPage.isInstanceOf[Page[SelectMenuController, SelectMenuView]])
  }

end TestMainMenuView
