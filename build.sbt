ThisBuild / name := "ISIQuiz"
ThisBuild / version := "0.0.0"
ThisBuild / scalaVersion := "3.2.0"

//Compile / run / test / mainClass := Some("myPackage.aMainClass")

lazy val osNames = Seq("linux", "mac", "win")

lazy val root = (project in file("."))
  .settings(
    assembly / mainClass := Some("Main"),
    assembly / assemblyJarName := "ISIQuiz.jar",
    assembly / assemblyOutputPath := file("target/ISIQuiz.jar"),
    assembly / assemblyMergeStrategy := {
      case PathList("META-INF", _*) => MergeStrategy.discard
      case _ => MergeStrategy.first
    },
    libraryDependencies ++= Seq(
      //"org.scalactic" %% "scalactic" % "3.2.14",
      "org.scalatest" %% "scalatest" % "3.2.14" % "test",
      "com.typesafe.play" %% "play-json" % "2.10.0-RC7",
      "org.scalafx" %% "scalafx" % "19.0.0-R30"
    ) ++ osNames.flatMap(os =>
      Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
        .map(m => "org.openjfx" % s"javafx-$m" % "16" classifier os))
  )
