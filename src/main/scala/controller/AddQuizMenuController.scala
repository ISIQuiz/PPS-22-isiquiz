package controller

import controller.Controller.{AppController, PageController}

/** Companion object of add quiz menu controller */
object AddQuizMenuController:

  enum AvailableActions extends Enumeration :
    case Back

/** Defines the logic of the add course page */
class AddQuizMenuController extends PageController :

  import AddQuizMenuController.*
  import AppController.AvailablePages

  override def updateUI[T](update: Option[T]): Unit =
    AppController.currentPage.pageView.draw(update)
    AppController.currentPage.pageView.handleInput()

  override def nextIteration(): Unit = updateUI(Option.empty)
  
  override def handle[T](action: Enumeration, value: Option[T]): Unit = action match
    case AvailableActions.Back => back

  def back: Unit = AppController.handle(AvailablePages.SettingsMenu, Option.empty)