package utils

import java.io.File

/** Object containing some base configurations */
object Configuration:

  /** Saved courses JSON file path */
  val SavedCoursesFilePath: String = "sample_saved_courses.json"
  
  /** File name of the player data */
  val PlayerDataFileName: String = "player_data.json"
  
  /** Home directory path */
  val HomeDirectoryPath: String = System.getProperty("user.home") + File.separator

  /** Current directory path */
  val CurrentDirectoryPath: String = System.getProperty("user.dir") + File.separator