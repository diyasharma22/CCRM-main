# Campus Course & Records Manager (CCRM)

## Project Overview

**Campus Course & Records Manager (CCRM)** is a console-based Java application for managing student and course records at an educational institution.

It lets administrators:
- Handle student enrollments
- Track academic progress
- Manage course offerings
- Generate essential reports

The application follows a clean architecture, separating **data**, **business logic**, and **user interface** concerns.

> Requires **JDK 17** or higher.

---

## How to Run

### Compile
```bash
cd path/to/CCRM/src
javac App.java -d ../bin
```

### Run
```bash
cd ../bin
java App
```

### Run with assertions enabled
Assertions are disabled by default. To enable them:
```bash
java -ea -cp ../bin App
```

---

## Project Structure
CCRM-main/
├── bin/ # Compiled .class files
├── data/ # CSV data files (students, courses, enrollments)
├── src/ # Java source code
└── README.md


---

## Setting Up the Development Environment

### Install the JDK (Windows)
1. Download the latest JDK from the [official Oracle website](https://www.oracle.com/java/technologies/downloads/).
2. Run the installer (defaults to a path like `C:\Program Files\Java\jdk-17`).
3. Set environment variables:
   - Create a system variable `JAVA_HOME` pointing to your JDK path.
   - Add `%JAVA_HOME%\bin` to your `Path` variable.

### VS Code Setup
1. Install the **Extension Pack for Java** (Microsoft) from the VS Code Marketplace.
2. Open VS Code → **File > Open Folder** → select the `CCRM-main` folder.
3. Go to **File > Preferences > Settings**, search `Java: Home`, and set it to your JDK path.
4. Open `App.java`, then **Run > Start Debugging (F5)**.

---

## Syllabus-to-Code Mapping

| Syllabus Topic | Where It's Demonstrated |
|---|---|
| Object-Oriented Programming (OOP) | `edu.ccrm.domain` package (`Student`, `Course`, `Enrollment`) |
| Data Persistence & File I/O | `edu.ccrm.io` package |
| Collection Framework | `edu.ccrm.service` package (e.g. `StudentService` using `List<Student>`) |
| Exception Handling | `edu.ccrm.exceptions` (e.g. `DuplicateEnrollmentException`, `MaxCreditLimitExceededException`) |
| Inheritance & Polymorphism | `Person` base class extended by `Student` and `Instructor` |
| Console-based I/O | `edu.ccrm.cli` package (`MainMenu`) |

---

## Java Background

**Evolution of Java**
- 1995 — Released by Sun Microsystems.
- 2004 — Java 5 introduced generics, annotations, and autoboxing.
- 2014 — Java 8 introduced lambda expressions and the Streams API.
- Present — Java 17 is the current Long-Term Support (LTS) release.

**Java Editions**
- **Java SE** — core platform for desktop, server, and console apps.
- **Java EE** — superset of SE for large-scale, distributed, enterprise apps.
- **Java ME** — subset for resource-constrained/embedded environments.

**JDK vs JRE vs JVM**
- **JVM** — runs Java bytecode; makes Java "write once, run anywhere."
- **JRE** — JVM + core libraries, for running Java programs.
- **JDK** — JRE + development tools (`javac`, debugger); required to build this project.