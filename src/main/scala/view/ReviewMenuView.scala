package view

import model.Review
import view.updates.{DefaultUpdate, ParameterlessViewUpdate, ViewUpdate}

object ReviewMenuView extends DefaultUpdate :

  /** Update for the view containing the review of the questions answered */
  case class CurrentReviewUpdate(override val updateParameter: Option[Review]) extends ViewUpdate(updateParameter)

  /** Update for the view containing the total points earned */
  case class TotalPointsUpdate(override val updateParameter: Option[Int]) extends ViewUpdate(updateParameter)

  /** Update for the view containing the total correct answers */
  case class TotalCorrectAnswersUpdate(override val updateParameter: Option[Int]) extends ViewUpdate(updateParameter)
