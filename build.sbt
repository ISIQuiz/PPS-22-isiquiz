ThisBuild / name := "ISIQuiz"
ThisBuild / version := "0.0.0"
ThisBuild / scalaVersion := "3.2.0"


lazy val root = (project in file("."))
  .settings(
    assembly / assemblyOption ~= { _.withCacheOutput(false) },
    assembly / assemblyJarName := "ISIQuiz.jar",
    assembly / assemblyOutputPath := file("target/ISIQuiz.jar"),
    libraryDependencies ++= Seq(
      //"org.scalactic" %% "scalactic" % "3.2.14",
      "org.scalatest" %% "scalatest" % "3.2.14" % "test",
      "com.typesafe.play" %% "play-json" % "2.10.0-RC7"
    ),
  )
