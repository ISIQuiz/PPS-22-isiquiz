package controllerTest

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}
import controller.Controller.*
import controller.Controller.AppController.*

import controller.{MainMenuController, SelectMenuController, StatisticsMenuController, SettingsMenuController}
import view.MainMenuView.*
import view.SelectMenu.*
import view.StatisticsMenu.*
import view.SettingsMenu.*

class TestApplicationController extends AnyFunSuite with BeforeAndAfterAll with BeforeAndAfterEach:

  override def beforeEach(): Unit = {
    super.beforeEach()
  }

  test("Application should start in main menu page") {
    assert(AppController.currentPage.isInstanceOf[Page[MainMenuController, MainMenuView]])
  }

  test("Application should go to main menu page when action is performed") {
    AppController.handle(AvailablePages.MainMenu, Option.empty)
    assert(AppController.currentPage.isInstanceOf[Page[MainMenuController, MainMenuView]])
  }

  test("Going from main menu to select page when action is performed") {
    AppController.handle(AvailablePages.SelectMenu, Option.empty)
    assert(AppController.currentPage.isInstanceOf[Page[SelectMenuController, SelectMenuView]])
  }

  test("Going from main menu to statistics page when action is performed") {
    AppController.handle(AvailablePages.StatisticsMenu, Option.empty)
    assert(AppController.currentPage.isInstanceOf[Page[StatisticsMenuController, StatisticsMenuView]])
  }

  test("Going from main menu to settings page when action is performed") {
    AppController.handle(AvailablePages.SettingsMenu, Option.empty)
    assert(AppController.currentPage.isInstanceOf[Page[SettingsMenuController, SettingsMenuView]])
  }

end TestApplicationController
