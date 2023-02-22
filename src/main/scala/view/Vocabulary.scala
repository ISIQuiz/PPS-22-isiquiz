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

  def ANSWER = LANGUAGE_SELECTED match
    case IT => "Risposta"
    case _ => "Answer"

  def CORRECT = LANGUAGE_SELECTED match
    case IT => "Corretta"
    case _ => "Correct"

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
    case IT => "Seleziona un corso"
    case _ => "Select a course"

  def SELECT_QUIZ = LANGUAGE_SELECTED match
    case IT => "Seleziona un quiz"
    case _ => "Select a quiz"

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

  def IMPORT_QUIZ = LANGUAGE_SELECTED match
    case IT => "Importa quiz"
    case _ => "Import quiz"

  def EXPORT_QUIZ = LANGUAGE_SELECTED match
    case IT => "Esporta quiz"
    case _ => "Export quiz"

  def ADD_COURSE = LANGUAGE_SELECTED match
    case IT => "Aggiungi corso"
    case _ => "Add course"

  def COURSE_ADDED = LANGUAGE_SELECTED match
    case IT => "Corso aggiunto"
    case _ => "Course added"

  def ADD_QUIZ = LANGUAGE_SELECTED match
    case IT => "Aggiungi quiz"
    case _ => "Add quiz"

  def QUIZ_ADDED = LANGUAGE_SELECTED match
    case IT => "Quiz aggiunto"
    case _ => "Quiz added"

  def EDIT_COURSE = LANGUAGE_SELECTED match
    case IT => "Modifica corso"
    case _ => "Edit course"

  def COURSE_EDITED = LANGUAGE_SELECTED match
    case IT => "Corso modificato"
    case _ => "Course edited"

  def EDIT_QUIZ = LANGUAGE_SELECTED match
    case IT => "Modifica quiz"
    case _ => "Edit quiz"

  def QUIZ_EDITED = LANGUAGE_SELECTED match
    case IT => "Quiz modificato"
    case _ => "Quiz edited"

  def DELETE_COURSE = LANGUAGE_SELECTED match
    case IT => "Cancella corso"
    case _ => "Delete course"

  def COURSE_DELETED = LANGUAGE_SELECTED match
    case IT => "Corso cancellato"
    case _ => "Course deleted"

  def DELETE_QUIZ = LANGUAGE_SELECTED match
    case IT => "Cancella quiz"
    case _ => "Delete quiz"

  def QUIZ_DELETED = LANGUAGE_SELECTED match
    case IT => "Quiz cancellato"
    case _ => "Quiz deleted"

  def OPERATION_SUCCEEDED = LANGUAGE_SELECTED match
    case IT => "Operazione avvenuta con successo"
    case _ => "Operation succeeded"

  def ERROR_INVALID_FILE = LANGUAGE_SELECTED match
    case IT => "Errore, file invalido."
    case _ => "Error, invalid file."

  def COURSE_NAME = LANGUAGE_SELECTED match
    case IT => "Nome corso"
    case _ => "Course name"

  def DEGREE_NAME = LANGUAGE_SELECTED match
    case IT => "Nome corso di laurea"
    case _ => "Degree name"

  def UNIVERSITY_NAME = LANGUAGE_SELECTED match
    case IT => "Nome università"
    case _ => "University name"

  def DESCRIPTION_COURSE = LANGUAGE_SELECTED match
    case IT => "Descrizione corso"
    case _ => "Course description"

  def NAME_MISSING = LANGUAGE_SELECTED match
    case IT => "Nome mancante"
    case _ => "Name missing"

  def INVALID_CONFIGURATION = LANGUAGE_SELECTED match
    case IT => "Configurazione Invalida"
    case _ => "Invalid configuration"

  def INVALID_SELECTION = LANGUAGE_SELECTED match
    case IT => "Selezione invalida"
    case _ => "Invalid selection"

  def IMAGE_PATH = LANGUAGE_SELECTED match
    case IT => "Percorso immagine"
    case _ => "Image path"

  def ADD_ANSWER = LANGUAGE_SELECTED match
    case IT => "Aggiungi risposta"
    case _ => "Add answer"

  def REMOVE_ANSWER = LANGUAGE_SELECTED match
    case IT => "Rimuovi risposta"
    case _ => "Remove answer"
