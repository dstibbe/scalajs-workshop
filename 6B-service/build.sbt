name := "6B-service"

scalaVersion in ThisBuild := "2.11.7"

enablePlugins(ScalaJSPlugin)
enablePlugins(SbtWeb) //make resources (other than js) from webjars available

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.9.0",
  "com.greencatsoft" %%% "scalajs-angular" % "0.6", // scala.js interface for angular
  "biz.enef" %%% "slogging" % "0.4.0", // logging library
  "com.lihaoyi" %%% "utest" % "0.3.0" % "test" // utest
)
scalaJSUseRhino in Global := false
testFrameworks += new TestFramework("utest.runner.Framework")

jsDependencies ++= Seq(
  RuntimeDOM,
  "org.webjars" % "angularjs" % "1.3.15" / "angular.min.js", // angular JavaScript library
  "org.webjars" % "angularjs" % "1.3.15" / "angular-route.js" dependsOn "angular.min.js",
  "org.webjars" % "angularjs" % "1.3.15" / "angular-animate.js" dependsOn "angular.min.js",
  "org.webjars" % "angularjs" % "1.3.15" / "angular-aria.js" dependsOn "angular.min.js",
  "org.webjars" % "angularjs" % "1.3.15" / "angular-locale_ko.js" dependsOn "angular.min.js",
  "org.webjars" % "angular-material" % "1.0.6" / "angular-material.js" dependsOn "angular.min.js"

)



//Settings WorkBench

workbenchSettings

bootSnippet := "nl.quintor.dstibbe.todos.TodoApp().main();"

updateBrowsers <<= updateBrowsers.triggeredBy(fastOptJS in Compile)
