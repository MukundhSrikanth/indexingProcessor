# Index Processor

The **Index Processor** is a simple Java-based application that reads files (`.txt`, `.csv`, `.json`) and breaks their content into **tokens** (words).  
It can serve as a foundation for building indexing, searching, or text-processing systems.

## 🎯 Goal

- Support reading and tokenizing files in accordance with business rules:
  - **TXT** – split words and handle punctuation properly.
  - **CSV** – extract values from cells, ignoring empty ones.
  - **JSON** – recursively traverse objects/arrays and collect all string values.

## 📜 Business Rules

- Business rules are implemented in a configurable manner to allow future updates.
- A **RuleEngine** enforces the rules on extracted tokens.
- Currently implemented rules:
  - **LongWordsRule** – extracts words longer than 5 characters.
  - **StartsWithMRule** – counts words starting with "M" or "m".

## 🧩 Main Components

- **Tokenizer** – Processes input files (`.txt`, `.csv`, `.json`) and extracts tokens.
- **ConfigLoader** – Externalizes configuration and loads it from `application.properties`.
- **RuleEngine** – Enforces business rules on the extracted tokens.
- **BusinessRule** – Interface for creating new rules that can be applied via `RuleEngine`.

## 🧪 Testing

- The application is tested using **JUnit 5** with **AssertJ** for assertions.
- Current test coverage: **91%**
- Includes multiple test classes and sample files to validate functionality and edge cases.

## 📝 Logging

- The application uses **SLF4J** with **slf4j-simple** as the logging implementation.
- Provides informative messages for key events such as:
  - Unsupported file types
  - Missing or empty input files
  - Errors while reading or parsing files
  - Helps track application flow and debug issues during execution.
- Log messages appear on the console when running the application, giving real-time feedback on processing status.

## 📥 Cloning the Repository

Before building or running the application, you need to clone the repository to your local machine.
**Prerequisite**: Make sure Git is installed on your system. You can check by running:

```bash
git --version
```

Clone the repository:

```bash
git clone https://github.com/MukundhSrikanth/indexingProcessor.git
```

This will create a local copy of the project in a folder named indexingProcessor. You can then navigate into the project folder:

```bash
cd indexingProcessor
```

After this, you can follow the instructions to build or run the application locally or with Docker.

## ⚙️ Requirements

To build and run the project locally (without Docker), install the following:

- **Java 21** (JDK 21 or later)
- **Gradle 9** (or use the Gradle wrapper included with the project)

To run the application using Docker (without installing Java or Gradle):

**Docker** (Docker Desktop on Mac/Windows, or Docker Engine on Linux)
**Suggested method** : Using Docker allows the application to run in a platform-independent environment with all dependencies bundled.

## 🏗 Build and Packaging

- The application is built using **Gradle**.
- To build a **fat jar** (with all dependencies included), run (inside the rootDir):

```bash
./gradlew clean fatJar --refresh-dependencies
```

**Note**: If you want, you can skip building the jar; a pre-built fat jar is already included in ./build/libs/.

## 🚀 Running the Application

**Option 1: Run locally using Java**

Use the command:

```bash
java -jar ./build/libs/indexing-processor-all.jar <path-to-input-file>
```

Example :

```bash
java -jar ./build/libs/indexing-processor-all.jar src/test/resources/input.csv
java -jar ./build/libs/indexing-processor-all.jar src/test/resources/input.txt
java -jar ./build/libs/indexing-processor-all.jar src/test/resources/input.json
```

**NOTE**
Please make sure to provide the **exact path for the JAR file** and **input file** while executing the application. Run this command from the **root directory of the project (indexingProcessor)**.
Please modify the contexts of the input files under **src/test/resources/** for testing.

**Option 2: Run using Docker** (Suggested Method)

If you have Docker installed, you can run the application with any input file from any location using the provided script:

Build the Docker image (only once): Run the command from the **root directory of the project (indexingProcessor)**.

**Note** : Please ensure docker desktop is running. 

```bash
docker build -t index-processor .
```

Run the app with your custome input file ... or you could run the inputFiles present in the project. 
```bash
src/test/resources/input.txt
```

**Example** :
PowerShell (Windows)
```bash
.\run-docker.sh "C:\Users\YourName\Desktop\inputFile.txt"
.\run-docker.sh "D:\Projects\data.csv"
.\run-docker.sh "C:\path\to\your\file.json"
```
- Use double quotes around paths with spaces

**Example** :
CMD (Windows)
```bash
.\run-docker.sh %cd%\src\test\resources\input.txt
```

**Example** : 
macOS (Terminal)
```bash
./run-docker.sh $(pwd)/src/test/resources/input.txt
./run-docker.sh /Users/alice/Documents/input.json
```

**Example** :
Linux (Bash)
```bash
MSYS_NO_PATHCONV=1 ./run-docker.sh $(pwd)/src/test/resources/input.txt
```

**Note** : Taking the liberty here to say I really enjoyed working on this assignment. Thank you so much for your time and this opportunity! It really means a lot, if you have come to this point and are reading this. Hope you liked my effort. Thanking you once again.
Kind Regards,
Mukundh Srikanth
