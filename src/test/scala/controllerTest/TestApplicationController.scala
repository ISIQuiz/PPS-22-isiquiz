package controllerTest

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}
import controller.Controller.*
import controller.Controller.AppController.*
import controller.Controller.{AppController, SelectMenuController, SettingsMenuController, StatisticsMenuController}
import view.MainMenu.*
import view.SelectMenu.*
import view.StatisticsMenu.*
import view.SettingsMenu.*

class TestApplicationController extends AnyFunSuite with BeforeAndAfterAll with BeforeAndAfterEach:

  test("Application should start in main menu page") {
    assert(AppController.currentPage.isInstanceOf[Page[MainMenuController, MainMenuView]])
  }

  test("Application should go to main menu page when action is performed") {
    AppController.handle(AvailablePages.MainMenu, Option.empty)
    assert(AppController.currentPage.isInstanceOf[Page[MainMenuController, MainMenuView]])
  }

  test("Going from main menu to select page when action is performed") {
    AppController.handle(AvailablePages.Select, Option.empty)
    assert(AppController.currentPage.isInstanceOf[Page[SelectMenuController, SelectMenuView]])
  }

  test("Going from main menu to statistics page when action is performed") {
    AppController.handle(AvailablePages.Statistics, Option.empty)
    assert(AppController.currentPage.isInstanceOf[Page[StatisticsMenuController, StatisticsMenuView]])
  }

  test("Going from main menu to settings page when action is performed") {
    AppController.handle(AvailablePages.Settings, Option.empty)
    assert(AppController.currentPage.isInstanceOf[Page[SettingsMenuController, SettingsMenuView]])
  }

end TestApplicationController
