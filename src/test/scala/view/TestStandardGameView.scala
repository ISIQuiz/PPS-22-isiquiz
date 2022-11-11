package view

import controller.Controller.*
import controller.{MainMenuController, SelectMenuController, StandardGameController}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}
import view.MainMenuView.*
import view.SelectMenuView.*
import view.StandardGameView.*

object TestStandardGameView:
  private var _input: String = null
  def input: String = _input
  def input_(input: String): Unit = _input = input

class TestStandardGameView extends AnyFunSuite with BeforeAndAfterAll with BeforeAndAfterEach:
  import TestStandardGameView.*

  class StandardGameViewTest extends StandardGameViewImpl:
    override def inputReader() = input

  var standardGameView: StandardGameView = new StandardGameViewTest

  override def beforeEach(): Unit = {
    super.beforeEach()
    AppController.handle(AppController.AvailablePages.StandardGame, Option.empty)
  }

  test("Standard game view should draw standard game menu") {
    assert(standardGameView.draw(Option.empty) == "StandardGame")
  }

end TestStandardGameView

