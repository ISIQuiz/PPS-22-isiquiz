ThisBuild / name := "ISIQuiz"
ThisBuild / version := "0.0.0"
ThisBuild / scalaVersion := "3.2.0"


lazy val root = (project in file("."))
  .settings(
    assembly / assemblyOption ~= { _.withCacheOutput(false) },
    assembly / assemblyJarName := "ISIQuiz.jar",
    assembly / assemblyOutputPath := file("target/ISIQuiz.jar"),
    assembly / assemblyMergeStrategy := {
      case "module-info.class" => MergeStrategy.discard
      case x =>
        val oldStrategy = (assembly / assemblyMergeStrategy).value
        oldStrategy(x)
    },
    libraryDependencies ++= Seq(
      //"org.scalactic" %% "scalactic" % "3.2.14",
      "org.scalatest" %% "scalatest" % "3.2.14" % "test",
      "com.typesafe.play" %% "play-json" % "2.10.0-RC7",
      "org.scalafx" %% "scalafx" % "19.0.0-R30"
    ),
  )
