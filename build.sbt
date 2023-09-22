import Dependencies.scalaTest

ThisBuild / scalaVersion     := "2.13.11"
ThisBuild / version          := "0.1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .settings(
    name := "scala-template",
    libraryDependencies += scalaTest
  )
