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

## 🏗 Build and Packaging

- The application is built using **Gradle**.  
- To build a **fat jar** (with all dependencies included), run (inside the rootDir):

```bash
./gradlew clean fatJar --refresh-dependencies
```

## ⚙️ Requirements

To build and run the project, install the following:

- **Java 21** (JDK 21 or later)  
- **Gradle 9** (or use the Gradle wrapper included with the project)

## 🚀 Running the Application

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