package controller

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}
import controller.Controller
import controller.AppController.*
import controller.{MainMenuController, SelectMenuController, SettingsMenuController, StatisticsMenuController}
import model.Session
import utils.DefaultCourseList
import view.MainMenuView.*
import view.SelectMenuView.*
import view.StatisticsMenuView.*
import view.SettingsMenuView.*

class TestApplicationController extends AnyFunSuite with BeforeAndAfterAll with BeforeAndAfterEach:

  override def beforeEach(): Unit = {
    super.beforeEach()
  }

  test("Application should start in main menu page") {
    assert(AppController.currentPage.isInstanceOf[Page[MainMenuController, MainMenuTerminalView]])
  }

  test("Application should go to main menu page when action is performed") {
    AppController.handle(AppController.MainMenu)
    assert(AppController.currentPage.isInstanceOf[Page[MainMenuController, MainMenuTerminalView]])
  }

  test("Going from main menu to select page when action is performed") {
    AppController.handle(AppController.SelectMenu)
    assert(AppController.currentPage.isInstanceOf[Page[SelectMenuController, SelectMenuView]])
  }

  test("Going from main menu to statistics page when action is performed") {
    AppController.handle(AppController.StatisticsMenu)
    assert(AppController.currentPage.isInstanceOf[Page[StatisticsMenuController, StatisticsMenuView]])
  }

  test("Going from main menu to settings page when action is performed") {
    AppController.handle(AppController.SettingsMenu)
    assert(AppController.currentPage.isInstanceOf[Page[SettingsMenuController, SettingsMenuView]])
  }

  test("Application should start with default Session value") {
    assert(AppController.session.isInstanceOf[Session])
  }

end TestApplicationController
