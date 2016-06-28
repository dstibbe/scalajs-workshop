name := "5D-angular_material"

scalaVersion in ThisBuild := "2.11.7"

enablePlugins(ScalaJSPlugin)
enablePlugins(SbtWeb) //make resources (other than js) from webjars available

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.9.0",
  "com.greencatsoft" %%% "scalajs-angular" % "0.6", // scala.js interface for angular
  "biz.enef" %%% "slogging" % "0.4.0" // logging library
)


jsDependencies ++= Seq(
  "org.webjars" % "angularjs" % "1.3.15" / "angular.js", // angular JavaScript library
  "org.webjars" % "angularjs" % "1.3.15" / "angular-route.js" dependsOn "angular.js",
  "org.webjars" % "angularjs" % "1.3.15" / "angular-animate.js" dependsOn "angular.js",
  "org.webjars" % "angularjs" % "1.3.15" / "angular-aria.js" dependsOn "angular.js",
  "org.webjars" % "angularjs" % "1.3.15" / "angular-locale_ko.js" dependsOn "angular.js",
  "org.webjars" % "angular-material" % "1.0.6" / "angular-material.js" dependsOn "angular.js"

)



//Settings WorkBench

workbenchSettings

bootSnippet := "nl.quintor.dstibbe.todos.TodoApp().main();"

updateBrowsers <<= updateBrowsers.triggeredBy(fastOptJS in Compile)
