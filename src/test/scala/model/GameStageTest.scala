
import model.Answer.*
import model.Quiz.Quiz
import model.SavedCourse.SavedCourse
import model.CourseIdentifier.CourseIdentifier
import model.Review.*
import model.settings.StandardGameSettings
import model.stats.PlayerStats
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class GameStageTest extends AnyFunSuite with Matchers:

  val course: Course = Course(CourseIdentifier("courseName", "s", "s"))
  val quizInGame: QuizInGame = QuizInGame(course,Quiz(question = "question", List(Answer("ans", false)), maxScore = 10), List(Answer("ansSelected", true)))
  val quizAnswered: QuizAnswered = QuizAnswered(quizInGame,None)
  val quizAnswered2: QuizAnswered = QuizAnswered(quizInGame, Option(Answer("ans", true)))
  val review: Review = Review()
  review.addQuizAnswered(quizAnswered)
  review.addQuizAnswered(quizAnswered2)

  val gameStage = GameStage()

  test("Creation quizInGame") {
    quizInGame.quiz.question shouldEqual "question"
    quizInGame.quiz.answerList.head.text shouldEqual "ans"
    quizInGame.quiz.maxScore shouldEqual 10
    quizInGame.answers shouldEqual List(Answer("ansSelected", true))
    quizInGame.course.courseId.courseName shouldEqual "courseName"
  }

  test("Creation gameStage") {
    gameStage.coursesInGame shouldEqual Nil
    gameStage.quizInGame shouldEqual null
    gameStage.quizInGamePresent shouldEqual false
    gameStage.review shouldEqual Review()
    gameStage.currentQuizNumber shouldEqual 1
    gameStage.gameSettings shouldEqual StandardGameSettings()
  }

  test("Add quizInGame in GameStage") {
    gameStage.quizInGame_(quizInGame)
    gameStage.quizInGamePresent shouldEqual true
    gameStage.addReviewQuizNotAnswered()
    gameStage.review.quizAnsweredList shouldEqual List(QuizAnswered(quizInGame, None))
    gameStage.currentQuizNumber shouldEqual 2
    gameStage.maxQuizzesReached shouldEqual false
    val answer: Answer = Answer("ans", true)
    gameStage.addReviewQuizAnswer(answer, 0, 0)
    gameStage.review.quizAnsweredList shouldEqual List(QuizAnswered(quizInGame, None), QuizAnswered(quizInGame, Option(answer)))
    gameStage.currentQuizNumber shouldEqual 3
    gameStage.maxQuizzesReached shouldEqual false
  }

  test("Add playerStatsInGame"){
    gameStage.quizInGame_(quizInGame)
    gameStage.addQuizToStats(true,12, 10)
    println(gameStage.playerStatsInGame)
    gameStage.playerStatsInGame.totalScore shouldEqual 12 // 12 punti
    gameStage.playerStatsInGame.totalAnsweredQuestions shouldEqual 1 // 1 risposta data
    gameStage.playerStatsInGame.totalCorrectAnswers shouldEqual 1 // 1 risposta corretta
    gameStage.playerStatsInGame.totalAnswerPrecision shouldEqual 100 // 100% precisione
    gameStage.playerStatsInGame.totalAverageTimeAnswer shouldEqual 10 // 10sec tempo di risposta
  }
