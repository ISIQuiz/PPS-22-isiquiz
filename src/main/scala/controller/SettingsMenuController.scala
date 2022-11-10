package controller

import controller.Controller.{AppController, PageController}

/** Companion object of settings menu controller */
object SettingsMenuController:

  enum AvailableActions extends Enumeration :
    case Back
    case AddCourse
    case AddQuiz

/** Defines the logic of the settings page */
class SettingsMenuController extends PageController :

  import AppController.AvailablePages
  import SettingsMenuController.*

  override def updateUI[T](update: Option[T]): Unit =
    AppController.currentPage.pageView.draw(update)
    AppController.currentPage.pageView.handleInput()

  override def nextIteration(): Unit = updateUI(Option.empty)
  
  override def handle[T](action: Enumeration, value: Option[T]): Unit = action match
    case AvailableActions.Back => back
    case AvailableActions.AddCourse => addCourse
    case AvailableActions.AddQuiz => addQuiz

  def back: Unit = AppController.handle(AvailablePages.MainMenu, Option.empty)

  def addCourse: Unit = AppController.handle(AvailablePages.AddCourseMenu, Option.empty)

  def addQuiz: Unit = AppController.handle(AvailablePages.AddQuizMenu, Option.empty)