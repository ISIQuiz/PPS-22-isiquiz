package controller

import controller.AppController.ReviewMenuAction
import model.Answer.Answer
import model.Quiz.Quiz
import model.{GameStage, QuizInGame, SavedCourse}

trait GameController:

  def nextQuiz(): Unit

  def chooseQuiz(course: SavedCourse): Quiz = course.quizList(randomNumberGenerator(1, course.quizList.size).head)

  def chooseAnswers(quiz: Quiz): List[Answer] =
    val allCorrectAnswers = quiz.answerList.filter(answer => answer.isCorrect)
    val allWrongAnswers = quiz.answerList.filter(answer => !answer.isCorrect)

    val correctAnswers = randomNumberGenerator(1, allCorrectAnswers.size).map(allCorrectAnswers)
    val wrongAnswers = randomNumberGenerator(3, allWrongAnswers.size).map(allWrongAnswers)

    scala.util.Random.shuffle(correctAnswers ::: wrongAnswers)


  def extractQuizInGame(gameStage: GameStage): QuizInGame =
    val selectedCourse = gameStage.coursesInGame(randomNumberGenerator(1, gameStage.coursesInGame.size).head)
    val selectedQuiz = chooseQuiz(selectedCourse)
    val selectedAnswers = chooseAnswers(selectedQuiz)
    val quizInGame = QuizInGame(selectedCourse, selectedQuiz, selectedAnswers)
    quizInGame

  def endGame(gameStage: GameStage): Unit = AppController.handle(ReviewMenuAction(Option(gameStage)))

  def randomNumberGenerator(quantity: Int, range: Int): List[Int] = scala.util.Random.shuffle(0 until range).take(quantity).toList
