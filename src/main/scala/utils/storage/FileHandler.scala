package utils.storage

import java.nio.file.{Files, Paths}
import scala.io.{Codec, Source}
import scala.util.{Try, Using}

/** Object of [[FileHandler]] */
object FileHandler:

  /**
   * Read file from path
   *
   * @param filePath a string with file path
   * @return a Try[String] with the file content
   */
  def readFile(filePath: String): Try[String] =
    Using(Source.fromFile(filePath)(Codec.UTF8))(_.mkString)

  /**
   * Write the string in a file
   *
   * @param filePath    a string with file path
   * @param fileContent a string with file content
   * @return a Try[String] with the file path
   */
  def writeFile(filePath: String, fileContent: String): Try[String] =
    Try {
      Files.writeString(Paths.get(filePath), fileContent)
      filePath
    }

  /**
   * Creates a directory if not exists
   *
   * @param filePath a string with directory path
   */
  def createDirectory(filePath: String): Unit =
    Files.createDirectories(Paths.get(filePath))

  /**
   * Check if file/directory exists
   *
   * @param filePath
   * @return a [[Boolean]]
   */
  def checkFileExists(filePath: String): Boolean =
    Files.exists(Paths.get(filePath))