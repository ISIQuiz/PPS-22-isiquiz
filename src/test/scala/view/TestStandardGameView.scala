package view

import controller.{AppController, Controller, MainMenuController, SelectMenuController, StandardGameController}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}
import view.MainMenuView.*
import view.SelectMenuView.*
import view.StandardGameView.*
import view.updates.ParameterlessViewUpdate

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
    AppController.handle(AppController.StandardGame)
  }

  test("Standard game view should draw standard game menu") {
    assert(standardGameView.draw(StandardGameView.DefaultUpdate) == "StandardGame")
  }

end TestStandardGameView

