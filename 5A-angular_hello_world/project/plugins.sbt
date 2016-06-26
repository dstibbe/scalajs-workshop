//Resolvers required for the Workbench
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
resolvers += "spray repo" at "http://repo.spray.io"

addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.7") // scala js
addSbtPlugin("com.lihaoyi" % "workbench" % "0.2.3") //publishing during development
