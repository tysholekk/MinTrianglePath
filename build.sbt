ThisBuild / scalaVersion     := "2.13.13"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "MinTrianglePath",
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core" % "2.6.1",
      "org.typelevel" %% "cats-effect" % "3.2.8",
      "org.typelevel" %% "cats-free" % "2.6.1",
      "dev.zio" %% "zio" % "2.1.1",
      "dev.zio" %% "zio-test" % "2.1.1"
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )
