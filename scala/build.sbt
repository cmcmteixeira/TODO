import java.util.Calendar

name := "TODO"
version := "1.0"
scalaVersion := "2.11.8"
pollInterval := 1000

libraryDependencies += "org.scalactic" %% "scalactic" % "2.2.6"
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test"
libraryDependencies += "org.scaldi" %% "scaldi" % "0.5.7"
libraryDependencies += "org.scalamock" %% "scalamock-scalatest-support" % "3.2.2" % "test"
libraryDependencies += "com.github.scopt" %% "scopt" % "3.5.0"
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.4.4"

resolvers += Resolver.sonatypeRepo("public")

val sampleTask = TaskKey[Unit]("print-separator")
sampleTask := {
  println("______________________________\n")
  printf("Project: %s \nPrinted at : %s\n","TODO",Calendar.getInstance().getTime)
  println("------------------------------\n")
}