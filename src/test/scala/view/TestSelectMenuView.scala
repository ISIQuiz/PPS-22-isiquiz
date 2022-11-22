package view

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}
import controller.{AppController, Controller, MainMenuController, Page, SelectMenuController, StandardGameController}
import view.MainMenuView.*
import view.SelectMenuView.*
import view.StandardGameView.*

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
    AppController.handle(AppController.SelectMenu)
  }

  test("Selecting back should return to main menu") {
    input_("1")
    assert(AppController.currentPage.isInstanceOf[Page[MainMenuController, MainMenuView]])
  }

  test("Selecting start should start a quiz") {
    input_("2")
    assert(AppController.currentPage.isInstanceOf[Page[StandardGameController, StandardGameView]])
  }

end TestSelectMenuView
