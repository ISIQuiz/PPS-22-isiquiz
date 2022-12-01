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
      "org.scalatest" %% "scalatest" % "3.2.14" % Test,
      "com.typesafe.play" %% "play-json" % "2.10.0-RC7",
      "org.scalafx" %% "scalafx" % "19.0.0-R30",
      "org.junit.jupiter" % "junit-jupiter" % "5.9.1" % Test,
      "org.testfx" % "testfx-core" % "4.0.16-alpha" % Test,
      "org.testfx" % "testfx-junit5" % "4.0.16-alpha" % Test,
      "org.testfx" % "openjfx-monocle" % "jdk-12.0.1+2" % Test,
//      "org.assertj" % "assertj-core" % "3.23.1" % Test,
    ) ++ osNames.flatMap(os =>
      Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
        .map(m => "org.openjfx" % s"javafx-$m" % "16" classifier os))
  )
