name := "splitter"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "com.typesafe.slick" %% "slick" % "1.0.1",
  "ws.securesocial" %% "securesocial" % "2.1.3"
)     

play.Project.playScalaSettings
