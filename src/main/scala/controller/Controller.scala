package controller

import controller.actions.Action
import view.updates.ViewUpdate

/** Trait of a generic controller that should handle actions with their optional values
 * @param [T] type of the optional parameter associated with an action available
 * @param action the action to handle
 * */
trait Controller:
  def handle[T](action: Action[T]): Unit

/** PageController should include all behaviours common between different pages controllers */
trait PageController extends Controller:
  def nextIteration(): Unit
  def updateUI[T](update: ViewUpdate[T]): Unit

/** Provides a binder between the page logic and the relative page view */
case class Page[C, V](var pageController: C, var pageView: V)
