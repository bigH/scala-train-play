name := "hot-drops"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "org.scalatest" % "scalatest_2.10" % "2.1.0" % "test"
)     

play.Project.playScalaSettings
