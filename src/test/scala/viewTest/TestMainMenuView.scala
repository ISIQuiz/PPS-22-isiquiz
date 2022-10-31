package viewTest

import controller.Controller
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}
import view.View.*
import view.MainMenu.*
import controller.Controller.*
import view.SettingsMenu.*

class TestMainMenuView extends AnyFunSuite with BeforeAndAfterAll with BeforeAndAfterEach:

  var mainMenuView: MainMenuView= new MainMenuViewImpl

  override def beforeEach(): Unit = {
    super.beforeEach()
    mainMenuView = MainMenuViewImpl()
  }

  test("Main menu view should draw main menu") {
    // TODO: Not actually asserting that System.out is expected output
    assert(mainMenuView.draw() == {
      println(
        "Menu principale:\n" +
          "1) Gioca\n" +
          "2) Statistiche\n" +
          "3) Impostazioni\n" +
          "4) Esci"
      )
    })
  }

  test("Should change to settings menu when settings is selected") {
    mainMenuView.sendEvent(Action("Settings", Option.empty))
    assert(ApplicationControllerImpl.currentPage.isInstanceOf[Page[SettingsController, SettingsMenuViewImpl]])
  }

end TestMainMenuView
