package controllerTest

import org.scalatest.funsuite.AnyFunSuite
import view.View.*
import controller.Controller.*
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}


class TestApplicationController extends AnyFunSuite with BeforeAndAfterAll with BeforeAndAfterEach:

  var controller: ApplicationControllerImpl = ApplicationControllerImpl()

  override def beforeEach(): Unit = {
    super.beforeEach()
    controller = ApplicationControllerImpl()
  }

  test("Application should start in main menu page") {
    assert(controller.currentPage.isInstanceOf[Page[MainMenuController, MainMenuView]])
  }

  test("Going from main menu to settings page when action is performed") {
    controller.handle(ApplicationControllerAction.SETTINGS)
    assert(controller.currentPage.isInstanceOf[Page[SettingsController, SettingsView]])
  }

end TestApplicationController
