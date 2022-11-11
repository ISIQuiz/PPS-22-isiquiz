package resources

import model.Answer.Answer
import model.CourseIdentifier.CourseIdentifierImpl
import model.Quiz.AnswerList.{cons, empty}
import model.Quiz.Quiz
import model.SavedCourse

object SampleCourseListForTest:

  def courseList = List(
    // Corso: Paradigmi di Programmazione e Sviluppo
    SavedCourse(
      courseId = CourseIdentifierImpl(
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
          answerList =
            cons(Answer(text = "1", false),
              cons(Answer(text = "2", false),
                cons(Answer(text = "4", true),
                  cons(Answer(text = "8", false),
                    empty()
                  )
                )
              )
            )
        ),
        // Domanda sottrazione
        Quiz( 
          question = "Domanda sottrazione: 2-2 = ?",
          maxScore = 6,
          imagePath = Option.empty,
          answerList =
            cons(Answer(text = "0", true),
              cons(Answer(text = "2", false),
                cons(Answer(text = "1", false),
                  cons(Answer(text = "6", false),
                    empty()
                  )
                )
              )
            )
        ),
        // Domanda moltiplicazione
        Quiz( 
          question = "Domanda moltiplicazione: 2x2 = ?",
          maxScore = 14,
          imagePath = Option.empty,
          answerList =
            cons(Answer(text = "1", false),
              cons(Answer(text = "2", false),
                cons(Answer(text = "4", true),
                  cons(Answer(text = "8", false),
                    empty()
                  )
                )
              )
            )
        ),
        // Domanda divisione
        Quiz( 
          question = "Domanda divisione: 2:2 = ?",
          maxScore = 12,
          imagePath = Option.empty,
          answerList =
            cons(Answer(text = "1", true),
              cons(Answer(text = "2", false),
                cons(Answer(text = "4", false),
                  cons(Answer(text = "8", false),
                    empty()
                  )
                )
              )
            )
        ),
      )
    ),
    // Sistemi Operativi
    SavedCourse(
      courseId = CourseIdentifierImpl(
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
          answerList =
            cons(Answer(text = "1", false),
              cons(Answer(text = "2", false),
                cons(Answer(text = "6", true),
                  cons(Answer(text = "8", false),
                    empty()
                  )
                )
              )
            )
        ),
        // Domanda sottrazione
        Quiz(
          question = "Domanda sottrazione: 3-3 = ?",
          maxScore = 4,
          imagePath = Option.empty,
          answerList =
            cons(Answer(text = "0", true),
              cons(Answer(text = "2", false),
                cons(Answer(text = "1", false),
                  cons(Answer(text = "6", false),
                    empty()
                  )
                )
              )
            )
        ),
        // Domanda moltiplicazione
        Quiz(
          question = "Domanda moltiplicazione: 3x3 = ?",
          maxScore = 12,
          imagePath = Option.empty,
          answerList =
            cons(Answer(text = "1", false),
              cons(Answer(text = "2", false),
                cons(Answer(text = "9", true),
                  cons(Answer(text = "8", false),
                    empty()
                  )
                )
              )
            )
        ),
        // Domanda divisione
        Quiz(
          question = "Domanda divisione: 3:3 = ?",
          maxScore = 13,
          imagePath = Option.empty,
          answerList =
            cons(Answer(text = "1", true),
              cons(Answer(text = "2", false),
                cons(Answer(text = "4", false),
                  cons(Answer(text = "8", false),
                    empty()
                  )
                )
              )
            )
        ),
      )
    ),
    // Sistemi Informativi
    SavedCourse( 
      courseId = CourseIdentifierImpl(
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
          answerList =
            cons(Answer(text = "1", false),
              cons(Answer(text = "2", false),
                cons(Answer(text = "6", false),
                  cons(Answer(text = "8", true),
                    empty()
                  )
                )
              )
            )
        ),
        // Domanda sottrazione
        Quiz(
          question = "Domanda sottrazione: 4-4 = ?",
          maxScore = 4,
          imagePath = Option.empty,
          answerList =
            cons(Answer(text = "0", true),
              cons(Answer(text = "2", false),
                cons(Answer(text = "1", false),
                  cons(Answer(text = "6", false),
                    empty()
                  )
                )
              )
            )
        ),
        // Domanda moltiplicazione
        Quiz(
          question = "Domanda moltiplicazione: 4x4 = ?",
          maxScore = 12,
          imagePath = Option.empty,
          answerList =
            cons(Answer(text = "1", false),
              cons(Answer(text = "2", false),
                cons(Answer(text = "16", true),
                  cons(Answer(text = "8", false),
                    empty()
                  )
                )
              )
            )
        ),
        // Domanda divisione
        Quiz(
          question = "Domanda divisione: 4:4 = ?",
          maxScore = 13,
          imagePath = Option.empty,
          answerList =
            cons(Answer(text = "1", true),
              cons(Answer(text = "2", false),
                cons(Answer(text = "4", false),
                  cons(Answer(text = "8", false),
                    empty()
                  )
                )
              )
            )
        ),
      )
    )
  )
