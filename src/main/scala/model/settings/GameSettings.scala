package model.settings

/**
 * Trait for some generic game settings
 */
trait GameSettings

/**
 * Creates a new [[StandardGameSettings]]
 *
 * @param quizMaxTime the max time for each quiz
 * @param maxQuizzes the max number of quizzes in a game
 */
case class StandardGameSettings(quizMaxTime: Int = 15, maxQuizzes: Int = 10) extends GameSettings

/**
 * Creates a new [[BlitzGameSettings]]
 *
 * @param maxTime the max time of the whole game
 */
case class BlitzGameSettings(maxTime: Int = 120) extends GameSettings