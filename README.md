# Scala.js Workshop

#### A workshop to get you introduced to the basic concepts of Scala.js

###### by [David Stibbe](mailto:dstibbe@gmail.com).


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

*The plugin was add*

For adding the plugin, we added in ```project/plugins.sbt``` :

```scala
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
resolvers += "spray repo" at "http://repo.spray.io"

addSbtPlugin("com.lihaoyi" % "workbench" % "0.2.3")
```

*The plugin was configured*

For configuring the plugin, we added in ```./build.sbt``` :

```scala
workbenchSettings
bootSnippet := "location.reload();"
```

The first line loads the settings for the workbench. The second line, tells the workbench how to restart your application through javascript.
Here we enter the script we previously had in ```hello.html``` to start our application.


*The plugin is used*

To use the workbench we configured a single script tag in ```hello.html```

```html
<script type="text/javascript" src="/workbench.js"></script>
```

This bootstraps the workbench, including the created fastOptJS javascript, after which it will execute the bootSnippet, configured above.


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

The class, when exported, is visible with the full qualified name as a method in JavaScript, eg.

```JavaScript
var instance = foo.bar.ClassName();
```

> We have a Scala.JS Calculator class and a html page that wants to use it.
> * modify the Calculator in such a way that it is visible in JavaScript.
> * modify the webpage to use the class.


You can modify the name with which an item is exposed to JavaScript by providing an argument to JsExport, eg.
```Scala
@JSExport("AnotherName")
```



> Try it yourself, modify the name of Calculator to AbsCalculator. You might notice something.


Another annotation to export elements is ```@JSExportAll```. It is an annotation on class level that exposes all
fields and methods in a class to JavaScript, but also *only* that. It does not export the class itself.


> Remove the JSExport from the method and replace it by using ```@JSExportAll``` on class level.



## 4 -  Access JavaScript code from Scalajs

### 4.1 - Accessing a JavaScript field from ScalaJS
**Purpose**
* Learn to access a simple value from a javascript object

**Exercise 4.A**

This exercise provides a Calculator that gets its input by reading two JavaScript objects, called
```Input``` and ```Result``` (instead of getting it as argument for the method and simply returning the result).

To represent a JavaScript object as a Scala.JS trait, it has to extend (direct, or indirectly) ```js.Any``` (usually it extends ```js.Object```) and be annotated with ```@js.native```.
In such a trait, all concrete definitions require ```=native``` as body and are mapped
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


# Evening 2: An app

## 5 - Angular introduction

In short, Angular is a JavaScript-based MVC framework.

Its main components are
* Templates - the html setup of a page
* Controllers - when the templates want to use funcationality, they call the controllers
* Scopes - in these objects we store scoped data
* Services - these are used for communicating to backend/third-party
* Directives - can be seen as definitions for custom tags and/or arguments

So, that was the extremely short introduction of Angular. We will revisit most of
these elements later on in more detail.


### 5.1 - Hello world

**Purpose**

* Here we will get acquainted with the controller-scope setup in angular


**Exercise 5.B**

* Add a dependency on the Angular JavaScript library in sb by adding:

```scala
jsDependencies ++= Seq(
  "org.webjars" % "angularjs" % "1.3.15" / "angular.js" // angular JavaScript library
)
```



* Add a dependency to the Scala.js angular facade in the build.sbt by adding to ```libraryDependencies```

```scala
"com.greencatsoft" %%% "scalajs-angular" % "0.6" // scala.js interface for angular
```

* The Scala.js sbt  will bundle the **jsDependencies** in a ```*-jsdeps.js``` file
which has already been added as source in ```hello.html```. You can see it above the inclusion of
the ```-fastopt.js``` file.

* Our template is ```hello.html``` .  In the body tag you see that two arguments:
   1. ```ng-app="myHelloApp"``` - the angular module to use .
   2. ```ng-controller="HelloCtrl"``` - the Controller bound to this tag.


   In the body tag, we access the corresponding scope in several ways:
   1. By arguments of ```ng-```directives, such as ```ng-click```, e.g.  ```<button ng-click="controller.submit()">```
   2. By ```{{...}}``` expressions, e.g. ```{{directMessage}}```

   The variables and methods you access through such expressions, all come from the scope, with the exception
   of ```controller```, which is a special variable that provides access to the controller, via which you
   can access the controller variables/methods (usually you only access methods).

* If we open ```HelloController``` we see that it extends ```Controller``` . Each controller in the angular facade should
extend this trait.
  1. The type of Controller is ```HelloScope```. This binds our scope to this controller. It is injected through ```@inject```
  2. The controller overwrites the method ```initialize``` which is called after the injection has been completed.
  3. The controller is configured with ```@injectable("HelloCtrl")```, making it injectable within Angular using that name.

* ```HelloScope``` extends the ```Scope``` trait. This is required for scope objects.
  1. The scope is basically is a mapping to the javascript scope object ```js.Any```, whose lifecycle is managed by Angular.js

* In the ```HelloApp``` the Angular module ```myHelloApp``` is registered (loaded in ng-app).
  1. The controller also gets registered there
  2. The HelloApp itself is designated as the Config object for this module.


* Run the application:
```scala
sbt clean ~fastOptJS
```

You see that variable ```directMessage``` is mutated immediatly. This is due to the fact, that it mutates
the exact same variable on the scope that you read from.

> Expand the controller and html in  such a way
that submitting causes the scope variable 'submittingMessage' is copied to 'submittedMessage'. Which
in turn is shown in the html.


### 5.2 - Todo list

**Purpose**

* Start the basics of a todo list

**Exercise 5.B**

We will create, a page showing a list of todo's from the scope using the following steps.
Feel free to use internet as a reference.

> Create a model of a case class called ```Todo``` with a field ```label```

> Expand the scope with a ```js.Array``` of Todo's

> Have the controller initialize the scope with a list of todos. Hint: ```.toJSArray```

> Create an ordered list of todo's in the template ```using ```ng-repeat```


### 5.3 - Logging

**Purpose**

* No more print(ln)

**Exercise 5.B**

Well, also in Scala, using println is not done.

A good logging framework is the **slogging** framework.

Add
```scala
"biz.enef" %%% "slogging" % "0.4.0"
 ```
To your dependencies (regular, not jsDependencies).

Now you can start logging by using the ```LazyLogging``` trait. This provides the ```logger``` field, which
can be used to log at various levels (trace, debug, info, warn, error).

> Replace all println's with logging.

### 5.4 - Introducting material

For looks. Requires very little extra. Assignment involves finding and adding the dependency.

Caveat:
    val myModule = Angular.module("theTodoApp", Seq("ngMaterial"))

## 6 - Services and testing

### 6.1 - Testing

Setup a test in scala test to verify that the proper list is being returned.

    "org.scalatest" %%% "scalatest" % "3.0.0-RC3"

    scala js version 0.6.8

Create src/test/scala (if it isn't seen as a test dir, add it)

Disable Rhina, add phantomjs. Use angular.min.js


### 6.2 - Introduce the service

Introduce the the dependency on the service and mock it in scala test.

### 6.3 - Implement the service

Implement the todo service with testing

### 6.4 - Add new todo functionality

Add test-driven adding and delete functionality to the controller and test.

## 7 - Extend the frontend

### 7.1 - MdDialog

Use mdDialog for popping up an add box. Requires a scala.js facade for mdDialog.




# Sources:

* Old presentation progress scala.js: <http://lampwww.epfl.ch/~doeraene/presentations/scala-js-scaladays2014/#/>
* Good hands-on: <http://www.lihaoyi.com/hands-on-scala-js/>
* Existing facades: <http://www.scala-js.org/libraries/facades.html>
* JavaScript <-> Scala.JS types: <http://www.scala-js.org/doc/interoperability/types.html>
