package model.settings

trait GameSettings

case class StandardGameSettings(quizMaxTime: Int = 15, maxQuizzes: Int = 10, helpsNumber: Int = 3) extends GameSettings