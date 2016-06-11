name := "javascript_from_scalajs_4A"

scalaVersion in ThisBuild := "2.11.7"

enablePlugins(ScalaJSPlugin)

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.0"

workbenchSettings

bootSnippet := "location.reload();"

updateBrowsers <<= updateBrowsers.triggeredBy(fastOptJS in Compile)
