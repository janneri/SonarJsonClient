name := "Sonar Client"

version := "1.0"

scalaVersion := "2.9.1"

libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.1.2"

// this is not compiled against scala 2.9, so it does not work
// libraryDependencies += "net.liftweb" % "lift-json" % "2.0"

// this is compiled against 2.9
libraryDependencies ++= {
  val liftVersion = "2.4-M4"
  Seq(
    "net.liftweb" %% "lift-json" % liftVersion % "compile->default")
}