# Scala.js Workshop


## Requirements
* Required knwoledge: scala and SBT (minimal)
* Tooling:
    * JDK 8
	* Scala 2.11.7
    * SBT 0.13.8
    * Git
    * Terminal
    * IDE (Eclipse, Intellij)
    * Browser


# Evening 1: Basics and frontend
 

 
 
## 1- Standalone App

In this first exercise we will create a 'Hello World' app that will run from the command line.

**Purpose** 

* Getting first hands-on experience with scala-js, which will not differ from scala
* Setting up the project structure

**Exercise 1**

* Open the following file in your editor:

```
    src/main/scala/quintor/exercise1/MainApp
```

and add the following in the ```main()``` method

```
    println("Hello world")
```

* Then run from the project root:
 ```
    cd 1-standalone
    sbt run
 ```

   . This produce the following output:

```
Java HotSpot(TM) 64-Bit Server VM warning: ignoring option MaxPermSize=256m; support was removed in 8.0
[info] Loading project definition from D:\dstibbe\projects\workshop\scalajs\exercises\1-standalone\project
[info] Set current project to standalone (in build file:/D:/dstibbe/projects/workshop/scalajs/exercises/1-standalone/)
[info] Updating {file:/D:/dstibbe/projects/workshop/scalajs/exercises/1-standalone/}root-1-standalone...
[info] Resolving org.eclipse.jetty#jetty-continuation;8.1.16.v20140903 ...
[info] Done updating.
[info] Compiling 1 Scala source to D:\dstibbe\projects\workshop\scalajs\exercises\1-standalone\target\scala-2.11\classes...
[info] Running quintor.exercise1.MainApp
Hello world
[success] Total time: 4 s, completed 3-mrt-2016 14:48:58
```

As you can see, it prints 'Hello World' to the command line.

**Explanation**

The app you just modified extends JSApp. The sbt plugin recognizes top-level objects extending JSApp, and runs
their ```main``` method when ```sbt run``` is executed.

The app is actually run in a **JavaScript** engine (Rhino). You can check this by executing

```
sbt "last run"
```
*(don't forget the quotes)*
which shows the logging of the last sbt run command. At the end of the log, you'll see: 

```
...
[info] Running quintor.exercise1.MainApp
[debug] with JSEnv RhinoJSEnv
```
Indicating that it was run in the Rhino JavaScript environment.


In the ```target\scala-2.11\classes\quintor\exercise1``` directory, we find the compilation results. 
There are **.sjsir** and **.class** files for each class in your project (just like for Scala JVM).
The **.class** files are used by the Scala.js compiler for symbol lookup during separate compilation. 
**Do not use these .class files to run your project on a JVM.**

The **.sjsir** files are a binary representation of (extended) JavaScript code which can be linked to actual JavaScript.


## 2 - Calling the App from JavaScript

In these exercises we will call the scala.js code from javascript code.


### 2.1 - First encounter with the Third kind

In this exercise, we shall call the same app we created before, but then from JavaScript

**Purpose** 

* Show the user how to compile to javascript
* illustrate that the same code that was compiled to ```.class``` is now compiled to ```.js```
* Show that the class is accessible from JavaScript

**Exercise 2.A**

* Open ```src/main/resources/hello.html```, it contains the following:

```html
<script type="text/javascript" src="../frontend-fastopt.js"></script>

<script type="text/javascript">
    quintor.exercise2.MainApp().main();
</script>
```

The first script tag includes the  javascript that will be generated from the scala.js code.
The second script tag calls the main method of the MainApp, which executes

```scala
println("Hello World")
```

* From the project root, run 
```
cd 2A-from_javascript
sbt fastOptJS
```
This will first execute the compile task, and then build the javascript file ```from_javascript-fastopt.js``` that
is included in the html page.


* Open the html page in ```target\scala-2.11\classes\hello.html```
  This will tell you to check the browser console (usually: F12). 
  In the javascript console, you'll find the message 'hello world!'.
* Depending on the browser, you will also see where it is produced.
    * Chrome uses the supplied js map and reports that it is produced by ```System.scala```,
    which is the scala.js library that causes println to write to console.log.
    * Firefox reports it is produced by ```from_javascript_2A-fastopt.js```, the
    generated javascript file.

**Explanation**

The sbt task `fastOptJS` generates, based on the compiled sjsir files, javascript code. Its
focus is on speed and meant for development. For production, one should use `fullOptJS`,
which is slower, but produces a smaller javascript file.

Try it:
```
sbt fullOptJS
```

However, with our application, you won't notice any performance decrease, because it is so small.
The size differences however, is noticable, 88KB vs 13KB.


### 2.2 - Accessing the DOM from scalajs

In this exercise we will access the DOM through a scala.js facade.
Even though facades will be discussed later, this gives a nice sense of reward

**Purpose**
* This application will show you how to manipulate the DOM.

**Exercise 2.B**

To be able to use the standard scala.js dom facade, we've added a dependency on 'scalajs-dom', which you can find in the ```build.sbt```

```scala
libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.0"
```

This library provides an interface from scalajs to the JavaScript DOM object.
(The ```%%%``` indicates that the dependency is a webjar (<http://www.webjars.org/>).)

> Now we we'll modify our html, by adding a div where text can be placed:. Add the following to
> the html page in `src/main/resources/hello.html`, as following

```html
<div id="fillme">I am empty</div>
```


> Change the main app to fill the div with 'hello world' as shown below.

```scala
document.getElementById("fillme").innerHTML = "Hello World"
```

Then build it, and open the ```hello.html``` in the target directory.

You see the message appear in the page.


> To make it a little more interesting, try the following scala code:

```scala

object MainApp extends JSApp {
  override def main() = {
	val random = scala.util.Random.nextInt(1000)
	val msg = s"Hello World. Now for a random number: $random"
	
    document.getElementById("fillme").innerHTML = msg
    println("I fllled the div with hello world")
  }
}

```

This should update with a new number at each refresh.


**Explanation**
This approach uses the ```dom.document``` facade to access the JavaScript DOM object.
It also stil performs a println after performing the insert.

* When opening ```hello.html``` in the browser, you'll see `Hello World. Now for a random number: {some number}` printed.
  The random number should change on each request.


### 2.2.doItYourself - Blink tag


> Use the dom.window facade to create a blink tag, that
> changes the css background color of the div each 100ms.

Hints:
```window.setInterval``` and ```setAttribute``` on the div element.


### 2.3 - Continuous building


**Exercise 2.C** 


* In this project we added a dependency to aid in our frontend development, we added the sbt workbench plugin from lihaoyi.
This provides a simple Akka-based webserver to make development easier.

The workbench plugin was configured as following:

*Adding the plugin*

For adding the plugin, we added in ```project/plugins.sbt``` :

```scala
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
resolvers += "spray repo" at "http://repo.spray.io"

addSbtPlugin("com.lihaoyi" % "workbench" % "0.2.3")
```

*Configuring the plugin*

For configuring the plugin, we added in ```./build.sbt``` :

```scala
workbenchSettings
bootSnippet := "quintor.exercise2.MainApp().main();"
```

The first line loads the settings for the workbench. The second line, tells the workbench how to restart your application through javascript.
Here we enter the script we previously had in ```hello.html``` to start our application.


*Using the plugin*

To use the workbench we configure a single script tag in ```hello.html```

```html
<script type="text/javascript" src="/workbench.js"></script>
```
and remove the old ones.

This will bootstrap the workbench, including the created fastOptJS javascript, after which it will execute the bootSnippet, configured above.


*Run with the plugin*

We will then run the application with
```
sbt ~fastOptJS
```

The ```~``` indicates that it will keep a watch on the files and recompile (and run) when they are changed.


Check the result by opening the brower to
<http://localhost:12345/target/scala-2.11/classes/hello.html>


> Change some code in hello.html; the text for example.
> The page will automatically reload (Firefox doesn't ) and show the changes.

Check the developer console what do you see?

It shows console logging from server.




 
 
## 3 - Access Scalajs code from JavaScript

### 3.1 - Simple Introduction

**Purpose**
* Purpose of this exercise is to show how ScalaJS objects can be exposed to JavaScript

**Exercies 3.A**

To make Scala.JS methods and classes visible in JavaScript, you add the ```@JSExport```
  annotation to each. Not adding this annotation to the class, makes the methods in it, only
  visible via an anonymous object.

The class, when exported, is visible as a method in JavaScript, eg.

```JavaScript
var instance = foo.bar.ClassName();
```

> We have a Scala.JS Calculator class and a html page that wants to use it.
> * modify the Calculator in such a way that it is visible in JavaScrit.
> * modify the webpage to use the class.


You can modify the name with which an item is exposed to JavaScript by providing an argument to JsExport, eg.
```Scala
@JSExport("AnotherName")
```



> Try it yourself, modify the name of Calculator to AbsCalculator. You might notice something.


Another annotation to export elements is ```@JSExportAll```. It is an annotation on class level that exposes all
fields and methods in a class to JavaScript, but also *only* that. It does not export the class itself.


> Remove the JSExport from the method and replace it by using ```@JSExportAll```



## 4 -  Access JavaScript code from Scalajs

### 4.1 - Accessing a JavaScript field from ScalaJS
**Purpose**
* Learn to access a simple value from a javascript object

**Exercise 4.A**

This exercise provides a Calculator that gets it input by reading two JavaScript objects, called
```Input``` and ```Result``` (instead of getting it as argument for the method and simply returning the result).

To represent a JavaScript object as a Scala.JS trait, it has to extend (direct, or indirectly) ```js.Any``` (usually it extends ```js.Object```) and be annotated with ```@js.native```.
In such a trait, all concrete definitios require ```=native``` as body and are mapped
to their javascript counterparts.

Example of a small part of a facade for the JavaScript window object:

```Scala
@js.native
trait Window extends js.Object {
  var location: String = js.native
  ...
```


The var, val and def definitions without parentheses all map to field access
 in JavaScript, whereas def definitions with parentheses (even empty) map
 to method calls in JavaScript.


> Make this exercise work by implementing the facades for Input and Result.

The ScalaJS website provides a list of how the different data types are mapped from
ScalaJS to JavaScript and vice versa.


Note:
The ```@js.native``` annotation is meant for native JavaScript classes. It is also possible
to define Scala.JS defined JavaScript classes:

<https://www.scala-js.org/doc/interoperability/sjs-defined-js-classes.html>

### 4.2 - Accessing a JavaScript function from ScalaJS
**Purpose**
* Learn to access a method from a javascript object )

**Exercise 4.B**

As mentioned previously, vals and vars map to JavaScript fields. Scala def definitions
with parentheses however, map toJavaScript method calls.

> In this exercise, make the Calculator work, by using the JavaScript AbsCalculation execute
> method.



### 4.3 - Into the deep end
**Purpose**
* Learn to create a facade on your own ;-)

**Exercise 4.C**

The site <http://www.scala-js-fiddle.com/> shows a very nice oscillator, to
entice you to start with scala.js.

Now, we will run this locally.


> In this exercise, you find the oscillator code, with some slight adjustments,
> in Oscillo.scala. Create the facades in such a way , that te oscillator works.


Note: some type-hints are provided in the scala code.

You can reference the scala.js website all you like, suggestions:

* <https://www.scala-js.org/doc/interoperability/facade-types.html>
* <https://www.scala-js.org/doc/interoperability/types.html>



# Sources:

* Old presentation progress scala.js: <http://lampwww.epfl.ch/~doeraene/presentations/scala-js-scaladays2014/#/>
* Good hands-on: <http://www.lihaoyi.com/hands-on-scala-js/>
* Existing facades: <http://www.scala-js.org/libraries/facades.html>
* JavaScript <-> Scala.JS types: <http://www.scala-js.org/doc/interoperability/types.html>
