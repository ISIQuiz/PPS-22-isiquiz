package viewTest

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}
import controller.Controller.*
import controller.{MainMenuController, SelectMenuController}
import view.MainMenuView.*
import view.SelectMenu.*

object TestSelectMenuView:
  private var _input: String = null
  def input: String = _input
  def input_(input: String): Unit = _input = input

class TestSelectMenuView extends AnyFunSuite with BeforeAndAfterAll with BeforeAndAfterEach:
  import TestSelectMenuView.*

  class SelectMenuViewTest extends SelectMenuViewImpl:
    override def inputReader() = input

  var selectMenuView: SelectMenuView = new SelectMenuViewTest

  override def beforeEach(): Unit = {
    super.beforeEach()
    AppController.handle(AppController.AvailablePages.SelectMenu, Option.empty)
  }

  test("Select menu view should draw select menu") {
    assert(selectMenuView.draw() == "SelectMenu")
  }

  test("Selecting back should return to main menu") {
    input_("1")
    assert(AppController.currentPage.isInstanceOf[Page[MainMenuController, MainMenuView]])
  }

end TestSelectMenuView
