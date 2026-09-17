# Campus Course & Records Manager (CCRM)

## Project Overview & How to Run
The *Campus Course & Records Manager (CCRM)* is a comprehensive, console-based Java application designed to manage student and course records for an educational institution.  

It provides administrators with a simple and efficient way to:
* Handle student enrollments  
* Track academic progress  
* Manage course offerings  
* Generate essential reports  

The application is built with a focus on *clean architecture, separating **data, **business logic, and **user interface* concerns.  

> The project requires *JDK 17* or higher to compile and run.

---

## Compile the Project
Navigate to the project's src directory and use the javac command:

bash
cd path/to/CCRM/src
javac App.java
Run the Application
From the src directory, use the java command, specifying the compiled class files' location:

java -cp ../bin App


## Evolution of Java
1995: Released by Sun Microsystems as an object-oriented language for consumer electronics.

2004: The release of Java 5 introduced generics, annotations, and autoboxing, significantly modernizing the language.

2014: Java 8 was a major release, introducing lambda expressions and the Streams API, which are essential for modern functional programming in Java.

Present: Java continues to evolve with a faster release cadence, with Java 17 being the latest Long-Term Support (LTS) version.

## Java Editions: ME, SE, and EE
Java SE (Standard Edition): The core platform for general-purpose desktop, server, and console applications. This is the foundation for all other editions.

Java EE (Enterprise Edition): A superset of Java SE, providing APIs for developing large-scale, distributed, and multi-tiered applications, such as web services and enterprise applications.

Java ME (Micro Edition): A subset of Java SE, designed for resource-constrained environments like embedded systems and older mobile phones.

JDK, JRE, and JVM Explained
JVM (Java Virtual Machine): The core of the Java platform. It's an abstract machine that provides a runtime environment for executing Java bytecode. The JVM is what makes Java write once, run anywhere.

JRE (Java Runtime Environment): Includes the JVM, plus the core libraries and other files needed to run a Java application. If you only want to run Java programs, you need the JRE.

JDK (Java Development Kit): Includes the JRE, plus the development tools needed to create Java applications, such as the compiler (javac), debugger, and archiver. The JDK is required to compile this project.

Setting up the Development Environment
JDK Installation on Windows
Download the JDK: Download the latest JDK installer from the official Oracle website.

## Run the Installer: Run the .exe file. The installer guides you through the process of installing the JDK to a default location like:


C:\Program Files\Java\jdk-17
Set Environment Variables:

Create a new system variable named JAVA_HOME and set its value to your JDK installation path (e.g., C:\Program Files\Java\jdk-17).

Edit the Path system variable, and add %JAVA_HOME%\bin to it. This allows you to run java and javac from any directory in the command prompt.

VS Code Setup
Install Extensions:
Install the official Java Extension Pack from the VS Code Marketplace. It includes:

Language Support for Java (by Red Hat)

Debugger for Java

Java Test Runner

Maven/Gradle support

Open the Project:
Open VS Code → File > Open Folder... → Select the CCRM root directory.

Configure JDK:
Go to File > Preferences > Settings and search for Java: Home.
Set it to your JDK installation path (e.g., C:\Program Files\Java\jdk-17).

Set Main Class & Run:

Open App.java.

Click Run > Start Debugging (F5).

Ensure the main class is set to:


edu.ccrm.App
VS Code will launch the application in the integrated terminal.

Syllabus to Code Mapping
This table maps key academic topics to where they are practically demonstrated in the project's codebase.

Syllabus Topic	File/Class/Method Where It's Demonstrated
Object-Oriented Programming (OOP)	edu.ccrm.domain package (e.g., Student.java, Course.java, Enrollment.java)
Data Persistence & File I/O	edu.ccrm.io package (e.g., CSVLoader.java, CSVWriter.java)
Collection Framework	edu.ccrm.service package (e.g., StudentService.java using List<Student>)
Exception Handling	Various classes, particularly in edu.ccrm.io (file errors) or in edu.ccrm.service (student not found)
Inheritance & Polymorphism	The Person base class (if implemented) or shared methods between data models
Console-based I/O	edu.ccrm.cli package (e.g., CLIHandler.java, Menu.java)
Export to Sheets	Note: Details for exporting to sheets are not provided in the input. Please clarify if you need specific instructions for this feature.

Enabling Assertions
To ensure certain conditions hold true during development and testing, this project uses assertions. They are disabled by default.

To enable them when running the application, use the -ea (enable assertions) flag:

bash

java -ea -cp ../bin App
pgsql
