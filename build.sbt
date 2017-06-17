name := "akka"

version := "1.0"

scalaVersion := "2.12.2"

libraryDependencies := Seq(
    "com.typesafe.akka" % "akka-actor_2.12" % "2.5.2",
    "com.typesafe.akka" %% "akka-persistence" % "2.5.2",
"com.typesafe.akka" %% "akka-remote" % "2.5.2"


)

libraryDependencies += "org.iq80.leveldb" % "leveldb" % "0.9"

// https://mvnrepository.com/artifact/org.fusesource.leveldbjni/leveldbjni-all
libraryDependencies += "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8"

// https://mvnrepository.com/artifact/com.typesafe.akka/akka-stream_2.11
