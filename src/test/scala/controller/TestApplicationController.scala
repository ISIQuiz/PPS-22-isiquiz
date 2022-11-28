package controller

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}
import controller.Controller
import controller.AppController.*
import controller.{MainMenuController, SelectMenuController, SettingsMenuController, StatisticsMenuController}
import model.Session
import utils.DefaultCourseList
import view.View.ViewFactory
import view.View.ViewFactory.GUIType.ScalaFX
import view.terminalUI.{TerminalMainMenu, TerminalSelectMenu, TerminalSettingsMenu, TerminalStatisticsMenu}
import view.terminalUI.TerminalMainMenu.*
import view.terminalUI.TerminalSelectMenu.*
import view.terminalUI.TerminalStatisticsMenu.*
import view.terminalUI.TerminalSettingsMenu.*
import view.View.ViewFactory.GUIType.*

class TestApplicationController extends AnyFunSuite with BeforeAndAfterAll with BeforeAndAfterEach:

  override def beforeEach(): Unit = {
    super.beforeEach()
  }

  test("Application should start in main menu page") {
    assert(AppController.currentPage.isInstanceOf[Page[MainMenuController, TerminalMainMenu]])
  }

  test("Application should go to main menu page when action is performed") {
    AppController.handle(MainMenuAction)
    assert(AppController.currentPage.isInstanceOf[Page[MainMenuController, TerminalMainMenu]])
  }

  test("Going from main menu to select page when action is performed") {
    AppController.handle(SelectMenuAction)
    assert(AppController.currentPage.isInstanceOf[Page[SelectMenuController, TerminalSelectMenu]])
  }

  test("Going from main menu to statistics page when action is performed") {
    AppController.handle(StatisticsMenuAction)
    assert(AppController.currentPage.isInstanceOf[Page[StatisticsMenuController, TerminalStatisticsMenu]])
  }

  test("Going from main menu to settings page when action is performed") {
    AppController.handle(SettingsMenuAction)
    assert(AppController.currentPage.isInstanceOf[Page[SettingsMenuController, TerminalSettingsMenu]])
  }

  test("Application should start with default Session value") {
    assert(AppController.session.isInstanceOf[Session])
  }

end TestApplicationController
