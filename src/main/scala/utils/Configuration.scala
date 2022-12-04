package utils

import java.io.File

/** Object containing some base configurations */
object Configuration:

  /** Saved courses JSON file path */
  val PlayerCoursesFileResource: String = "sample_saved_courses.json"
  
  /** File separator */
  val FileSeparator: String = File.separator 
  
  /** File name of the player data */
  val PlayerSessionFileName: String = "player_session.json"

  /** File name of the player courses */
  val PlayerCoursesFileName: String = "player_courses.json"

  /** File name of the player stats */
  val PlayerStatsFileName: String = "player_stats.json"

  /** Directory name containing player data */
  val PlayerDataDirectoryName: String = "ISIQuizData"

  /** Home directory path */
  val HomeDirectoryPath: String = System.getProperty("user.home")

  /** Current directory path */
  val CurrentDirectoryPath: String = System.getProperty("user.dir")


  val SessionFilePath: String = CurrentDirectoryPath + FileSeparator + PlayerDataDirectoryName + FileSeparator + PlayerSessionFileName

  val PlayerStatsFilePath: String = CurrentDirectoryPath + FileSeparator + PlayerDataDirectoryName + FileSeparator + PlayerStatsFileName

  val PlayerCoursesFilePath: String = CurrentDirectoryPath + FileSeparator + PlayerDataDirectoryName + FileSeparator + PlayerCoursesFileName

  val PlayerDataDirectoryPath: String = CurrentDirectoryPath + FileSeparator + PlayerDataDirectoryName

