package controller

import controller.actions.Action
import view.updates.ViewUpdate

import scala.concurrent.Promise
import scala.util.Try

/** Trait of a generic controller that should handle actions with their optional values
 * @param [T] type of the optional parameter associated with an action available
 * @param action the action to handle
 * */
trait Controller:
  def handle[T](action: Action[T]): Unit

/** PageController should include all behaviours common between different pages controllers */
trait PageController extends Controller:

  /** Defines the logic of an iteration (step) in the corresponding page controller */
  def nextIteration(): Unit

  /** Promise to await a performed action */
  var actionPromise: Promise[Unit] = Promise[Unit]

  /** Handles and action matching it with all the actions available in the page controller and completes the promise on the action
   * @param action the action performed
   * @tparam T the type of optional parameter of the action
   */
  override def handle[T](action: Action[T]): Unit =
    matchAction(action)
    actionPromise.complete(Try {})

  /** Should match the action with all the available action and execute the corresponding logic
   * @param action the action performed
   * @tparam T the type of optional parameter of the action
   */
  def matchAction[T](action: Action[T]): Unit

/** Provides a binder between the page logic and the relative page view */
case class Page[C, V](var pageController: C, var pageView: V)
