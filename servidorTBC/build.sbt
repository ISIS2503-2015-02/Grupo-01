name := """tbcTransporte"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.6"

herokuAppName in Compile := "fast-taiga-5201"

herokuProcessTypes in Compile := Map(
  "web" -> "target/universal/stage/bin/tbctransporte -Dhttp.port=$PORT -Dplay.evolutions.db.default.autoApply=true -Ddb.default.url=${DATABASE_URL}"
)

herokuJdkVersion in Compile := "1.8"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "mysql" % "mysql-connector-java" % "5.1.18",
  "org.postgresql" % "postgresql" % "9.4-1201-jdbc41"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator


fork in run := false

