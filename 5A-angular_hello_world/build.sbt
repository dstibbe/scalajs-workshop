name := "5A-angular_hello_world"

scalaVersion in ThisBuild := "2.11.7"

enablePlugins(ScalaJSPlugin)

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.9.0",
  "com.greencatsoft" %%% "scalajs-angular" % "0.6" // scala.js interface for angular
)


jsDependencies ++= Seq(
  "org.webjars" % "angularjs" % "1.3.15" / "angular.js" // angular JavaScript library
)



//Settings WorkBench

workbenchSettings

bootSnippet := "nl.quintor.dstibbe.helloworld.HelloApp().main();"

updateBrowsers <<= updateBrowsers.triggeredBy(fastOptJS in Compile)
