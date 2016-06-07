name := "scalajs_from_javascript_3A"

scalaVersion in ThisBuild := "2.11.7"

enablePlugins(ScalaJSPlugin)

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.0"

workbenchSettings

bootSnippet := "quintor.exercise2.MainApp().main();"

updateBrowsers <<= updateBrowsers.triggeredBy(fastOptJS in Compile)
