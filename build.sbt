name := "exercisesBuild"

scalaVersion in ThisBuild := "2.11.7"

lazy val exercisesProjects = (project in file(".")).
  aggregate(exercises1,
            exercises2A, exercises2B, exercises2C,
            exercises3A, exercises4A, exercises4B, exercises4C,
            exercises5A, exercises5B)

lazy val exercises1 = project in file("1-standalone")
lazy val exercises2A = project in file("2A-from_javascript")
lazy val exercises2B = project in file("2B-from_javascript")
lazy val exercises2C = project in file("2C-from_javascript")
lazy val exercises3A = project in file("3A-scalajs_from_javascript")
lazy val exercises4A = project in file("4A-javascript_from_scalajs")
lazy val exercises4B = project in file("4B-javascript_from_scalajs")
lazy val exercises4C = project in file("4C-oscillo")
lazy val exercises5A = project in file("5A-angular_hello_world")
lazy val exercises5B = project in file("5B-start_todos")
