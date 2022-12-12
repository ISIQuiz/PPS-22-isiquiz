package utils.storage

import java.io.File

/** Object containing some base configurations */
object Configuration:

  /** File separator */
  val FileSeparator: String = File.separator

  /** File name of the player courses */
  val PlayerCoursesFileName: String = "player_courses.json"

  /** File name of the player stats */
  private val PlayerStatsFileName: String = "player_stats.json"

  /** Directory name containing player data */
  private val PlayerDataDirectoryName: String = "ISIQuizData"

  /** Home directory path */
  val HomeDirectoryPath: String = System.getProperty("user.home")

  /** Current directory path */
  val CurrentDirectoryPath: String = System.getProperty("user.dir")

  /** Directory path of the player data */
  val PlayerDataDirectoryPath: String = CurrentDirectoryPath + FileSeparator + PlayerDataDirectoryName

  /** File path where player stats file is located */
  val PlayerStatsFilePath: String = PlayerDataDirectoryPath + FileSeparator + PlayerStatsFileName

  /** File path where saved course list file is located */
  val PlayerCoursesFilePath: String = PlayerDataDirectoryPath + FileSeparator + PlayerCoursesFileName

