package view

/**
 * A vocabulary of strings used in the GUI, changing LANGUAGE_SELECTED changes the string retrieved
 */
object Vocabulary:
  enum LANGUAGE_AVAILABLE:
    case EN
    case IT

  private var _language_selected: LANGUAGE_AVAILABLE = LANGUAGE_AVAILABLE.IT

  def LANGUAGE_SELECTED = _language_selected
  def LANGUAGE_SELECTED_=(lang: LANGUAGE_AVAILABLE) = _language_selected = lang

  import LANGUAGE_AVAILABLE.*

  def PLAY = LANGUAGE_SELECTED match
    case IT => "Gioca"
    case _ => "Play"

  def STATISTICS = LANGUAGE_SELECTED match
    case IT => "Statistiche"
    case _ => "Statistics"

  def SETTINGS = LANGUAGE_SELECTED match
    case IT => "Impostazioni"
    case _ => "Settings"

  def QUIT = LANGUAGE_SELECTED match
    case IT => "Esci"
    case _ => "Quit"

  def BACK = LANGUAGE_SELECTED match
    case IT => "Indietro"
    case _ => "Back"

  def NEXT = LANGUAGE_SELECTED match
    case IT => "Avanti"
    case _ => "Next"

  def YES = LANGUAGE_SELECTED match
    case IT => "Si"
    case _ => "Yes"

  def NO = LANGUAGE_SELECTED match
    case _ => "No"

  def CANCEL = LANGUAGE_SELECTED match
    case IT => "Annulla"
    case _ => "Cancel"

  def GAME_MODE = LANGUAGE_SELECTED match
    case IT => "Modalità di Gioco"
    case _ => "Game mode"

  def STANDARD_GAME = LANGUAGE_SELECTED match
    case IT => "Partita standard"
    case _ => "Standard Game"

  def BLITZ_GAME = LANGUAGE_SELECTED match
    case IT => "Partita Blitz"
    case _ => "Blitz Game"

  def CUSTOM_GAME = LANGUAGE_SELECTED match
    case IT => "Partita personalizzata"
    case _ => "Custom Game"

  def SELECT_AT_LEAST_A_COURSE = LANGUAGE_SELECTED match
    case IT => "Seleziona almeno un corso:"
    case _ => "Select at least one course:"

  def INVALID_COURSES_SELECTED = LANGUAGE_SELECTED match
    case IT => "Corsi selezionati invalidi per la modalità di gioco"
    case _ => "Courses selected invalid for this game mode"


  def QUESTION = LANGUAGE_SELECTED match
    case IT => "Domanda"
    case _ => "Question"

  def COURSE = LANGUAGE_SELECTED match
    case IT => "Corso"
    case _ => "Course"

  def POINTS = LANGUAGE_SELECTED match
    case IT => "Punti"
    case _ => "Points"

  def TOTAL_POINTS = LANGUAGE_SELECTED match
    case IT => "Punti totali"
    case _ => "Total points"

  def REVISION = LANGUAGE_SELECTED match
    case IT => "Revisione"
    case _ => "Revision"

  def TOT_RIGHT_ANSWERS = LANGUAGE_SELECTED match
    case IT => "Risposte corrette"
    case _ => "Total correct answers"

  def SHOW_ALL = LANGUAGE_SELECTED match
    case IT => "Mostra tutto"
    case _ => "Show all"

  def END = LANGUAGE_SELECTED match
    case IT => "Fine"
    case _ => "End"

  def TIME = LANGUAGE_SELECTED match
    case IT => "Tempo"
    case _ => "Time"

  def NUMBER_OF_QUESTIONS = LANGUAGE_SELECTED match
    case IT => "Numero di domande"
    case _ => "Number of questions"

  def MAX_TIME_FOR_QUESTION = LANGUAGE_SELECTED match
    case IT => "Tempo massimo per domanda"
    case _ => "Maximum time for question"


  def SELECT_COURSE = LANGUAGE_SELECTED match
    case IT => "Seleziona un corso:"
    case _ => "Select a course:"

  def SELECT_QUIZ = LANGUAGE_SELECTED match
    case IT => "Seleziona un quiz:"
    case _ => "Select a quiz:"

  def QUIZ_STATS = LANGUAGE_SELECTED match
    case IT => "Statistiche quiz"
    case _ => "Quiz stats"

  def QUIZ_ANSWERED = LANGUAGE_SELECTED match
    case IT => "Quiz risposti"
    case _ => "Quiz answered"

  def CORRECT_ANSWERS = LANGUAGE_SELECTED match
    case IT => "Risposte corrette"
    case _ => "Correct answers"

  def PRECISION = LANGUAGE_SELECTED match
    case IT => "Precisione"
    case _ => "Precision"

  def AVERAGE_TIME_ANSWER = LANGUAGE_SELECTED match
    case IT => "Tempo medio di risposta"
    case _ => "Average time for answer"

  def GLOBAL_STATS = LANGUAGE_SELECTED match
    case IT => "Statistiche globali"
    case _ => "Global stats"

  def RESET_STATS = LANGUAGE_SELECTED match
    case IT => "Reset statistiche"
    case _ => "Reset stats"