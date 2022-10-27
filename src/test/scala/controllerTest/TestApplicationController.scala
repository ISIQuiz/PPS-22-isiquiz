package controllerTest

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}
import controller.Controller.*
import view.MainMenu.*
import view.SettingsMenu.*


class TestApplicationController extends AnyFunSuite with BeforeAndAfterAll with BeforeAndAfterEach:

  var controller: ApplicationController = ApplicationControllerImpl()

  override def beforeEach(): Unit = {
    super.beforeEach()
    controller = ApplicationControllerImpl()
  }

  test("Application should start in main menu page") {
    assert(controller.currentPage.isInstanceOf[Page[MainMenuController, MainMenuViewImpl]])
  }

  test("Going from main menu to settings page when action is performed") {
    controller.handle(ApplicationControllerAction.SETTINGS)
    assert(controller.currentPage.isInstanceOf[Page[SettingsController, SettingsMenuViewImpl]])
  }

end TestApplicationController
