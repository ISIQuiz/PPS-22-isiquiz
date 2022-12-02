package utils

import model.Answer.Answer
import model.stats.PlayerStats
import model.Quiz.Quiz
import model.stats.CourseInStats
import model.stats.PlayerStats.defaultPlayerStatsFromSavedCourseList
import model.stats.QuizInStats
import model.{Course, CourseIdentifier, SavedCourse}

object DefaultDataList:

  def defaultCourseList = List(
    // Corso: Paradigmi di Programmazione e Sviluppo
    SavedCourse(
      courseId = CourseIdentifier(
        courseName = "Paradigmi di Programmazione e Sviluppo",
        degreeName = "Laurea Magistrale in Ingegneria e Scienze Informatiche",
        universityName = "Università di Bologna"
      ),
      description = Option("Descrizione pps"),
      quizList = List(
        // Domanda Somma
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
        ),
        // Domanda sottrazione
        Quiz(
          question = "Domanda sottrazione: 2-2 = ?",
          maxScore = 6,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "0", true),
            Answer(text = "2", false),
            Answer(text = "1", false),
            Answer(text = "6", false)
          )
        ),
        // Domanda moltiplicazione
        Quiz(
          question = "Domanda moltiplicazione: 2x2 = ?",
          maxScore = 14,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "1", false),
            Answer(text = "2", false),
            Answer(text = "4", true),
            Answer(text = "8", false)
          )
        ),
        // Domanda divisione
        Quiz(
          question = "Domanda divisione: 2:2 = ?",
          maxScore = 12,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "1", true),
            Answer(text = "2", false),
            Answer(text = "4", false),
            Answer(text = "8", false)
          )
        ),
      )
    ),
    // Sistemi Operativi
    SavedCourse(
      courseId = CourseIdentifier(
        courseName = "Sistemi Operativi",
        degreeName = "Laurea in Ingegneria e Scienze Informatiche",
        universityName = "Università di Bologna"
      ),
      description = Option("Descrizione SO"),
      quizList = List(
        // Domanda Somma
        Quiz(
          question = "Domanda somma: 3+3 = ?",
          maxScore = 7,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "1", false),
            Answer(text = "2", false),
            Answer(text = "6", true),
            Answer(text = "8", false)
          )
        ),
        // Domanda sottrazione
        Quiz(
          question = "Domanda sottrazione: 3-3 = ?",
          maxScore = 4,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "0", true),
            Answer(text = "2", false),
            Answer(text = "1", false),
            Answer(text = "6", false)
          )
        ),
        // Domanda moltiplicazione
        Quiz(
          question = "Domanda moltiplicazione: 3x3 = ?",
          maxScore = 12,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "1", false),
            Answer(text = "2", false),
            Answer(text = "9", true),
            Answer(text = "8", false)
          )
        ),
        // Domanda divisione
        Quiz(
          question = "Domanda divisione: 3:3 = ?",
          maxScore = 13,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "1", true),
            Answer(text = "2", false),
            Answer(text = "4", false),
            Answer(text = "8", false)
          )
        ),
      )
    ),
    // Sistemi Informativi
    /*SavedCourse(
      courseId = CourseIdentifier(
        courseName = "Sistemi Informativi",
        degreeName = "Laurea Magistrale in Ingegneria e Scienze Informatiche",
        universityName = "Università di Bologna"
      ),
      description = Option("Descrizione SI"),
      quizList = List(
        // Domanda Somma
        Quiz(
          question = "Domanda somma: 4+4 = ?",
          maxScore = 7,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "1", false),
              Answer(text = "2", false),
                Answer(text = "6", false),
                  Answer(text = "8", true)
            )
        ),
        // Domanda sottrazione
        Quiz(
          question = "Domanda sottrazione: 4-4 = ?",
          maxScore = 4,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "0", true),
              Answer(text = "2", false),
                Answer(text = "1", false),
                  Answer(text = "6", false)
            )
        ),
        // Domanda moltiplicazione
        Quiz(
          question = "Domanda moltiplicazione: 4x4 = ?",
          maxScore = 12,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "1", false),
              Answer(text = "2", false),
                Answer(text = "16", true),
                  Answer(text = "8", false)
            )
        ),
        // Domanda divisione
        Quiz(
          question = "Domanda divisione: 4:4 = ?",
          maxScore = 13,
          imagePath = Option.empty,
          answerList = List(
            Answer(text = "1", true),
              Answer(text = "2", false),
                Answer(text = "4", false),
                  Answer(text = "8", false)
            )
        ),
      )
    )*/
  )

  def defaultPlayerStats = defaultPlayerStatsFromSavedCourseList(defaultCourseList)


  def defaultPlayerStatsTest =
    PlayerStats(
      totalScore = 11, totalAnsweredQuestions = 12, totalCorrectAnswers = 13,
      courseInStatsList = List(
        CourseInStats(
          course = Course(
            CourseIdentifier(
              courseName = "Paradigmi di Programmazione e Sviluppo",
              degreeName = "Laurea Magistrale in Ingegneria e Scienze Informatiche",
              universityName = "Università di Bologna"
            )
          ),
          quizInStatsList = List(QuizInStats(1, 2, 3), QuizInStats(4, 5, 6), QuizInStats(7, 8, 9), QuizInStats(10, 11, 12))
        ),
        CourseInStats(
          course = Course(
            CourseIdentifier(
              courseName = "Sistemi Operativi",
              degreeName = "Laurea in Ingegneria e Scienze Informatiche",
              universityName = "Università di Bologna"
            )
          ),
          quizInStatsList = List(QuizInStats(11, 22, 33), QuizInStats(44, 55, 66), QuizInStats(77, 88, 99), QuizInStats(1010, 1111, 1212))
        )
      )
    )