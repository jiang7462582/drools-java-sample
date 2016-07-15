name := """drools-java-sample"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs
)

// https://mvnrepository.com/artifact/org.drools/drools-compiler
libraryDependencies += "org.drools" % "drools-compiler" % "6.4.0.Final"

// https://mvnrepository.com/artifact/org.drools/drools-core
libraryDependencies += "org.drools" % "drools-core" % "6.4.0.Final"

