package controller

import controller.actions.Action
import view.updates.ViewUpdate

import concurrent.ExecutionContext.Implicits.global
import scala.collection.mutable.ListBuffer
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

/** Provides a binder between the page logic and the relative page view */
case class Page[C, V](var pageController: C, var pageView: V)
