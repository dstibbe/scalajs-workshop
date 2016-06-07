name := "oscillo_4C"

scalaVersion in ThisBuild := "2.11.7"

enablePlugins(ScalaJSPlugin)


//Do NOT use this during this exercise!
//libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.0"

workbenchSettings

bootSnippet := "nl.quintor.dstibbe.Oscillo().main();"

updateBrowsers <<= updateBrowsers.triggeredBy(fastOptJS in Compile)
