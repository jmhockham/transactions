name := "transactions"
 
version := "1.0" 
      
lazy val `transactions` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"
      
scalaVersion := "2.12.2"

libraryDependencies ++= Seq( evolutions, jdbc , ehcache , ws , specs2 % Test , guice )
libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "3.0.0",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "org.ccil.cowan.tagsoup" % "tagsoup" % "1.2.1"
)
libraryDependencies += "com.h2database" % "h2" % "1.4.193"
libraryDependencies += "org.postgresql" % "postgresql" % "42.2.5"
libraryDependencies += "org.scalaj" %% "scalaj-http" % "2.4.1"

routesImport += "play.api.mvc.PathBindable.bindableUUID"

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

      