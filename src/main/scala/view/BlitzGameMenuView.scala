package view

import model.GameStage
import utils.Timer
import view.updates.{DefaultUpdate, ParameterlessViewUpdate, ViewUpdate}

object BlitzGameMenuView extends DefaultUpdate :

  /** Update for the view containing the current GameStage */
  case class CurrentGameUpdate(override val updateParameter: Option[GameStage]) extends ViewUpdate(updateParameter)

  /** Update for the view containing the timer, used to get the remaining time and the percentage */
  case class TimerUpdate(override val updateParameter: Option[Timer]) extends ViewUpdate(updateParameter)

  /** Update for the view for the feedback when the timer is expired */
  case object TimeExpiredUpdate extends ParameterlessViewUpdate
