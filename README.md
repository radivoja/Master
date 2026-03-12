# XMI Code Generator for Spring Boot MVC

A code generation tool that parses **Eclipse Papyrus UML/XMI models** and automatically generates a complete **Java Spring Boot MVC** web application, including JPA entities, repositories, services, controllers, mappers, and Thymeleaf templates.

---

## Table of Contents
- [Overview](#overview)
- [Architecture](#architecture)
- [Technologies](#technologies)
- [How It Works](#how-it-works)
- [UML Profile & Stereotypes](#uml-profile--stereotypes)
- [Supported Relationships](#supported-relationships)
- [Generated Artifacts](#generated-artifacts)
- [How to Use](#how-to-use)
- [Project Structure](#project-structure)
- [SOLID Design Principles](#solid-design-principles)

---

## Overview

This project is part of a Master's thesis. The goal is to bridge the gap between UML design and implementation by automating the generation of boilerplate Spring Boot MVC code directly from a UML class diagram modeled in Eclipse Papyrus.

Instead of writing repetitive CRUD code by hand, the developer:
1. Designs a UML class diagram in Eclipse Papyrus
2. Applies custom stereotypes to mark classes and properties
3. Runs the generator
4. Gets a fully functional Spring Boot MVC application

---

## Architecture

```
Eclipse Papyrus (.uml / XMI)
        │
        ▼
   XMI SAX Parser  (Reader.java)
        │
        ▼
   Domain Model    (Model, Property, Stereotype)
        │
        ▼
 FreeMarker Templates (.ftlh)
        │
        ▼
 Generated Spring Boot MVC Application
```

---

## Technologies

| Technology | Version | Purpose |
|---|---|---|
| Java | 17 | Core language |
| Spring Boot | 3.0.2 | Application framework |
| FreeMarker | 2.3.32 | Code generation templates |
| SAX Parser | JDK built-in | XMI parsing |
| Eclipse Papyrus | Latest | UML modeling tool |
| Thymeleaf | Spring Boot managed | Generated frontend templates |
| Jakarta Validation | Spring Boot managed | Generated validation annotations |
| Jakarta Persistence | Spring Boot managed | Generated JPA annotations |

---

## How It Works

### 1. Parsing
The `Reader` class extends SAX `DefaultHandler` and parses the `.uml` XMI file exported from Eclipse Papyrus. It builds an in-memory model of all UML classes, properties, associations, and stereotypes.

### 2. Model Building
After parsing, the following post-processing steps occur:
- **Association resolution** — determines `OneToOne`, `OneToMany`, `ManyToOne`, `ManyToMany` from UML multiplicity
- **Stereotype sorting** — links stereotypes to their target classes and properties
- **Entity filtering** — only classes marked with `MyMetaModel:Entity` are included in generation

### 3. Code Generation
The `Loader` class uses **FreeMarker** to process `.ftlh` templates, injecting the parsed model data to produce Java and HTML files.

---

## UML Profile & Stereotypes

Apply these stereotypes in Eclipse Papyrus to control code generation:

### Class-level stereotypes

| Stereotype | Effect |
|---|---|
| `MyMetaModel:Entity` | Marks the class for generation — required |
| `MyMetaModel:Pageable` | Enables pagination in list view (tagged values: `pageNo`, `pageSize`) |

### Property-level stereotypes

| Stereotype | Effect |
|---|---|
| `MyMetaModel:Key` | Marks the primary key — generates `@Id`, `@NotNull` |
| `MyMetaModel:EntityProperty` | Generates validation annotations (`@NotBlank`, `@Size`, `@Min`, `@Max`) |
| `MyMetaModel:Unique` | Generates `@NotBlank` or `@NotNull` |
| `MyMetaModel:ToString` | Includes the property in the generated `toString()` method |
| `MyMetaModel:Common` | Marks a common/shared property |

### Tagged values for `MyMetaModel:EntityProperty`

| Tagged value | Type | Effect |
|---|---|---|
| `minLength` | String | Generates `@Size(min=...)` or `@Min(...)` |
| `maxLength` | String | Generates `@Size(max=...)` or `@Max(...)` |
| `nullable` | String | Controls nullability |

---

## Supported Relationships

Relationships are resolved automatically from UML association multiplicity:

| UML Multiplicity | Generated JPA Annotation |
|---|---|
| `1` to `1` | `@OneToOne` |
| `1` to `*` | `@OneToMany` / `@ManyToOne` |
| `*` to `*` | `@ManyToMany` |

---

## Generated Artifacts

For each `@Entity` class the following files are generated:

### Java (Spring Boot)
| File | Description |
|---|---|
| `entities/ClassName.java` | JPA Entity with annotations and relationships |
| `repository/ClassNameRepository.java` | Spring Data JPA Repository |
| `service/ClassNameService.java` | Service layer with CRUD operations |
| `controller/ClassNameController.java` | Spring MVC Controller with GET/POST mappings |
| `dao/ClassNameDao.java` | DAO/DTO with Jakarta validation annotations |
| `mapper/ClassNameMapper.java` | Mapper between Entity and DAO |

### Thymeleaf (Frontend)
| File | Description |
|---|---|
| `classNameList.html` | List view with edit/delete actions |
| `classNameForm.html` | Create/Update form with validation |
| `index.html` | Index page with links to all list views |

---

## How to Use

### Prerequisites
- Java 17
- Maven
- Eclipse Papyrus with the `MyMetaModel` UML profile applied

### Steps

**1. Clone the repository**
```bash
git clone https://github.com/radivoja/Master.git
cd Master/XMIParser
```

**2. Configure paths in `ConsoleApp.java`**
```java
public static final String PATH = "path/to/your/model.uml";
public static final String DESTINATION_JAVA = "path/to/output/java/";
public static final File TEMPLATE_DIRECTORY = new File("path/to/XMIParser/src/main/resources/static");
private static final String DESTINATION_THYMELEAF = "path/to/output/templates/";
```

**3. Run the generator**
```bash
mvn spring-boot:run
```
Or run `ConsoleApp.main()` directly from your IDE.

**4. Check generated output**
The generated Spring Boot MVC application will appear in your configured destination folders.

---

## Project Structure

```
XMIParser/
├── src/main/java/com/project/
│   ├── gui/
│   │   └── ConsoleApp.java          # Entry point
│   ├── model/
│   │   ├── Model.java               # UML class representation
│   │   ├── Property.java            # UML property representation
│   │   ├── Stereotype.java          # Stereotype representation
│   │   └── Pageable.java            # Pagination configuration
│   └── parser/
│       ├── Reader.java              # SAX parser / XMI handler
│       ├── Loader.java              # Code generation orchestrator
│       ├── Component.java           # Enum of generatable component types
│       ├── StereotypeName.java      # Enum of supported stereotype names
│       ├── XmiConstants.java        # XMI structural constants
│       └── XmiToJavaTypeMapper.java # Ecore to Java type mapping
└── src/main/resources/static/
    ├── entity.ftlh                  # JPA Entity template
    ├── repository.ftlh              # Repository template
    ├── service.ftlh                 # Service template
    ├── controller.ftlh              # Controller template
    ├── dao.ftlh                     # DAO template
    ├── mapper.ftlh                  # Mapper template
    ├── list.ftlh                    # Thymeleaf list template
    ├── form.ftlh                    # Thymeleaf form template
    └── index.ftlh                   # Thymeleaf index template
```

---

## SOLID Design Principles

This project was refactored to respect SOLID principles as part of the thesis:

**S — Single Responsibility**
Each class has one clearly defined responsibility. `Reader` handles only SAX events, `XmiToJavaTypeMapper` handles only type mapping, `Loader` handles only generation orchestration.

**O — Open/Closed Principle**
- Adding a new Ecore/UML primitive type → add one entry to `XmiToJavaTypeMapper`
- Adding a new stereotype → add one value to `StereotypeName` enum
- Adding a new generated component → add one value to `Component` enum

**L — Liskov Substitution**
`Reader` extends `DefaultHandler` following the standard SAX contract without violating expected behavior.

**I — Interface Segregation**
*(Planned — in progress)*

**D — Dependency Inversion**
*(Planned — in progress)*

---

## Author

Developed as part of a Master's thesis project.
