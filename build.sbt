name := "moneyweb"

version := "1.0"

lazy val `moneyweb` = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc ,
  javaEbean ,
  cache ,
  javaWs ,
  "mysql" % "mysql-connector-java" % "5.1.27",
  "org.webjars" % "jquery" % "2.1.4",
  "org.webjars" % "angularjs" % "1.4.3-1" exclude("org.webjars", "jquery")
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  