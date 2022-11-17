package utils

import java.nio.file.{Files, Paths}
import scala.io.Source
import scala.util.{Try, Using}

/**
 * Manage reading and writing files
 */
trait FileHandler:

  /**
   * Read file from path
   * @param filePath a string with file path
   * @return a Try[String] with the file content
   */
  def readFile(filePath: String): Try[String]

  /**
   * Write the string in a file
   * @param filePath a string with file path
   * @param fileContent a string with file content
   * @return an exception if it fails
   */
  def writeFile(filePath: String, fileContent: String): Try[Unit]

/** Companion object for [[FileHandler]] trait */
object FileHandler:

  /** Creates an instance of [[FileHandler]] trait */
  def apply(): FileHandler =  FileHandlerImpl()

  // Implementation of FileHandler trait
  private class FileHandlerImpl extends FileHandler:

    override def readFile(filePath: String): Try[String] =
      Using(Source.fromFile(filePath))(_.mkString)
    
    override def writeFile(filePath: String, fileContent: String): Try[Unit] =
      Try(Files.writeString(Paths.get(filePath), fileContent))