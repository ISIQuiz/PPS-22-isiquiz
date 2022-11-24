package view

import controller.{AppController, Controller, MainMenuController, SelectMenuController, StandardGameController}
import controller.Controller
import controller.actions.Action
import controller.{MainMenuController, SelectMenuController, StandardGameController}
import model.Answer.Answer
import model.CourseIdentifier.CourseIdentifierImpl
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}
import view.terminalUI.TerminalMainMenu.*
import view.terminalUI.TerminalSelectMenu.*
import view.terminalUI.TerminalStandardGameMenu.*
import view.updates.ParameterlessViewUpdate
import model.Quiz.Quiz
import model.SavedCourse.SavedCourseImpl
import model.GameStage

object TestTerminalStandardGameAction:
  private var _input: String = null
  def input: String = _input
  def input_(input: String): Unit = _input = input

class TestTerminalStandardGameAction extends AnyFunSuite with BeforeAndAfterAll with BeforeAndAfterEach:
  import TestTerminalStandardGameAction.*

  class StandardGameViewTest extends StandardGameViewImpl:
    override def inputReader() = input

  val course = SavedCourseImpl(
    courseId = CourseIdentifierImpl(
      courseName = "Paradigmi di Programmazione e Sviluppo",
      degreeName = "Laurea Magistrale in Ingegneria e Scienze Informatiche",
      universityName = "Università di Bologna"
    ),
    description = Option("Descrizione pps"),
    quizList = List(
      Quiz(
        question = "Domanda somma: 2+2 = ?",
        maxScore = 5,
        imagePath = Option.empty,
        answerList = List(
          Answer(text = "1", false),
          Answer(text = "2", false),
          Answer(text = "4", true),
          Answer(text = "8", false)
        )
      )
    )
  )

  var standardGameView: StandardGameView = new StandardGameViewTest

  override def beforeEach(): Unit = {
    super.beforeEach()
    AppController.handle(AppController.StandardGameAction(Option(GameStage(List(course)))))
  }

end TestTerminalStandardGameAction

