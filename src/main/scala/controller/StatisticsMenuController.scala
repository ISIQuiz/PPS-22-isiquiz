package controller

import controller.Controller.{AppController, PageController}

/** Companion object of statistics menu controller */
object StatisticsMenuController:

  enum AvailableActions extends Enumeration :
    case Back

/** Defines the logic of the statistics page */
class StatisticsMenuController extends PageController :

  import AppController.AvailablePages
  import StatisticsMenuController.*

  override def updateUI[T](update: Option[T]): Unit =
    AppController.currentPage.pageView.draw(update)
    AppController.currentPage.pageView.handleInput()

  override def nextIteration(): Unit = updateUI(Option.empty)

  override def handle[T](action: Enumeration, value: Option[T]): Unit = action match
    case AvailableActions.Back => back

  def back: Unit = AppController.handle(AvailablePages.MainMenu, Option.empty)