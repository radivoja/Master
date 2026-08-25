# XMI Code Generator for Spring MVC

A code generation tool that parses **Eclipse Papyrus UML/XMI models** and automatically
generates a complete **Spring MVC web application (built on Spring Boot)** — including JPA
entities, repositories, services, controllers, DTOs, mappers, and Thymeleaf templates.

---

## Table of Contents

- [Overview](#overview)
- [Repository Layout](#repository-layout)
- [Architecture](#architecture)
- [Technologies](#technologies)
- [How It Works](#how-it-works)
- [UML Profile & Stereotypes](#uml-profile--stereotypes)
- [Supported Relationships](#supported-relationships)
- [Generated Artifacts](#generated-artifacts)
- [How to Use](#how-to-use)
- [Project Structure](#project-structure)
- [Demonstration Domain](#demonstration-domain)
- [Master's Thesis](#masters-thesis)

---

## Overview

This project was developed as part of a Master's thesis. The goal is to bridge the gap
between UML design and implementation by automating the generation of boilerplate Spring MVC
code directly from a UML class diagram modeled in Eclipse Papyrus.

Instead of writing repetitive CRUD code by hand, the developer:

1. Designs a UML class diagram in Eclipse Papyrus
2. Applies the **SpringMVCProfile** stereotypes to mark keys, list views, forms, and validation
3. Runs the generator
4. Gets a fully functional Spring MVC application

Every UML class in the model is turned into a generated entity — there is no separate "entity"
marker. The profile stereotypes only *refine* what is generated (primary keys, validation,
pagination, sorting, read-only forms).

---

## Repository Layout

| Folder             | Contents                                                                 |
| ------------------ | ------------------------------------------------------------------------ |
| `CodeGenerator/`   | The generator itself — Java sources and FreeMarker templates             |
| `SpringMVCProfile/`| The Eclipse Papyrus UML profile (`.uml` / `.di` / `.notation`)           |
| `CirculationModel/`| Example input model — the BISIS library circulation module               |

---

## Architecture

```
Eclipse Papyrus model  (CirculationModel.uml — XMI)
        │
        ▼
   Reader              SAX handler; builds UmlClass / UmlProperty
        │
        ▼
   UmlModelResolver    resolves associations (multiplicity → JPA relationship),
        │              maps UML → domain (DomainClass / DomainProperty),
        │              applies SpringMVCProfile stereotypes
        ▼
   FileGenerator       drives generation per Component
        │
        ▼
   Loader              FreeMarker engine (square-bracket syntax) + .ftlh templates
        │
        ▼
 Generated Spring MVC application
 (entities, repositories, services, controllers, DTOs, mappers,
  and Thymeleaf list / form / index views)
```

---

## Technologies

| Technology            | Version             | Purpose                                              |
| --------------------- | ------------------- | --------------------------------------------------- |
| Java                  | 17                  | Core language                                        |
| Spring Boot           | 3.0.2               | Framework of the generator and of the generated app |
| FreeMarker            | 2.3.32              | Code-generation templates (square-bracket syntax)   |
| SAX Parser            | JDK built-in        | XMI parsing                                          |
| Jakarta XML Binding   | 4.0                 | XML binding support                                  |
| Eclipse Papyrus       | Latest              | UML modeling + SpringMVCProfile authoring           |
| Thymeleaf             | Spring Boot managed | Generated frontend templates                        |
| Jakarta Validation    | Spring Boot managed | Generated validation annotations                    |
| Jakarta Persistence   | Spring Boot managed | Generated JPA annotations                           |

---

## How It Works

### 1. Parsing

The `Reader` class extends the SAX `DefaultHandler` and parses the `.uml` XMI file exported
from Eclipse Papyrus. It builds an in-memory representation of all UML classes, properties,
associations, and applied stereotypes (`UmlClass`, `UmlProperty`).

### 2. Model Resolution

`UmlModelResolver` post-processes the parsed model:

- **Association resolution** — for each association it inspects the multiplicity on both ends
  and assigns `OneToOne`, `OneToMany`, `ManyToOne`, or `ManyToMany` to both sides in a single pass.
- **UML → domain mapping** — each `UmlClass`/`UmlProperty` is mapped to a
  `DomainClass`/`DomainProperty` used by the templates.
- **Stereotype application** — `SpringMVCProfile` stereotypes are attached to their target
  classes and properties.

### 3. Code Generation

`FileGenerator` drives generation for each `Component`, and `Loader` runs **FreeMarker** over
the `.ftlh` templates, injecting the domain model to produce Java and HTML files.

> FreeMarker is configured with **square-bracket syntax** (`[= ]`, `[#if ]`, …) so that
> template directives do not clash with Thymeleaf's `${ }` expressions in the generated HTML.

---

## UML Profile & Stereotypes

Apply these stereotypes from the **SpringMVCProfile** in Eclipse Papyrus to control generation.

<p align="center">
  <img src="docs/springmvc-profile.png" alt="SpringMVCProfile stereotypes and enumerations" width="850">
</p>

`MVCForm` and `MVCList` specialize the abstract `MVCView` (which extends `Class`), while `Id`
and `SortBy` specialize `MVCProperty` (which extends `Property`). The applied stereotypes and
their tagged values are:

### Class-level stereotypes

| Stereotype | Effect                                                                                     |
| ---------- | ------------------------------------------------------------------------------------------ |
| `MVCList`  | Configures the class's list view. Tagged values: `pageSize`, `createEnabled`, `editEnabled`, `deleteEnabled` |
| `MVCForm`  | Configures the create/update form. Tagged value: `readonly`                                 |

### Property-level stereotypes

| Stereotype    | Effect                                                                                                      |
| ------------- | --------------------------------------------------------------------------------------------------------- |
| `Id`          | Marks the primary key → `@Id`. Tagged values: `generated`, `generationStrategy` (`StrategyType`)          |
| `MVCProperty` | Adds validation / column constraints. Tagged values: `unique`, `nullable`, `minLength`, `maxLength`, `toString` |
| `SortBy`      | Sets the default sort property for the list view. Tagged value: `sortDirection` (`SortDirection`)         |

### Enumerations

| Enumeration     | Literals                                       |
| --------------- | ---------------------------------------------- |
| `SortDirection` | `ASC`, `DESC`                                  |
| `StrategyType`  | `TABLE`, `SEQUENCE`, `IDENTITY`, `UUID`, `AUTO`|

> **Defaults:** Papyrus only serializes tagged values that differ from the profile default.
> Absent values therefore fall back to their profile defaults, which are applied in the Java
> model getters rather than in the templates.

---

## Supported Relationships

Relationships are resolved automatically from UML association multiplicity:

| UML Multiplicity | Generated JPA Annotation    |
| ---------------- | --------------------------- |
| `1` to `1`       | `@OneToOne`                 |
| `1` to `*`       | `@OneToMany` / `@ManyToOne` |
| `*` to `*`       | `@ManyToMany`               |

---

## Generated Artifacts

For each UML class the following files are generated:

### Java (Spring Boot)

| File                                  | Description                                    |
| ------------------------------------- | ---------------------------------------------- |
| `entities/ClassName.java`             | JPA entity with annotations and relationships  |
| `repository/ClassNameRepository.java` | Spring Data JPA repository                     |
| `service/ClassNameService.java`       | Service layer with CRUD operations             |
| `controller/ClassNameController.java` | Spring MVC controller with GET/POST mappings   |
| `dto/ClassNameDto.java`               | DTO with Jakarta validation annotations        |
| `mapper/ClassNameMapper.java`         | Mapper between entity and DTO                   |

### Thymeleaf (frontend)

| File                 | Description                                  |
| -------------------- | -------------------------------------------- |
| `classNameList.html` | List view with pagination and edit/delete    |
| `classNameForm.html` | Create/update form with validation           |
| `index.html`         | Shared landing page linking to all list views |

### Templates

The nine FreeMarker templates that drive this output live in
`CodeGenerator/src/main/resources/static/`:
`entity`, `repository`, `service`, `controller`, `dto`, `mapper`, `list`, `form`, `index`
(each `*.ftlh`).

Java files are written under the configured base package (the demo uses
`com.bisis.circulation`), and HTML files under `src/main/resources/templates/`.

---

## How to Use

### Prerequisites

- Java 17
- Maven
- Eclipse Papyrus with the **SpringMVCProfile** applied to your model

### Clone

```bash
git clone https://github.com/radivoja/Master.git
cd Master/CodeGenerator
```

### Option A — Console

Configure the paths in
`src/main/java/com/project/parser/TestConstants.java`:

```java
public static final String CIRCULATION_UML = "path/to/your/model.uml";  // input model
public static final String PROJECT_ROOT     = "path/to/target/project/"; // output root
public static final String JAVA_ROOT        = "src/main/java/com/your/pkg/"; // Java output
public static final String TEMPLATE_ROOT    = "src/main/resources/templates/"; // HTML output
```

Then run:

```bash
mvn spring-boot:run
```

or run `ConsoleApp.main()` directly from your IDE.

> The default paths use Windows-style separators — adjust them for your operating system.

### Option B — Graphical UI

Run `MainFrame` (Swing). Use the buttons to pick the `.uml` model and the output destination,
then click **Generate**.

### Output

The generated Spring MVC application appears in the configured destination folders.

---

## Project Structure

```
Master/
├── CodeGenerator/
│   └── src/main/
│       ├── java/com/project/
│       │   ├── gui/
│       │   │   ├── ConsoleApp.java          # Console entry point
│       │   │   └── MainFrame.java           # Swing GUI entry point
│       │   ├── model/
│       │   │   ├── UmlClass.java            # Parsed UML class
│       │   │   └── UmlProperty.java         # Parsed UML property
│       │   ├── domain/
│       │   │   ├── DomainClass.java         # Template-facing class model
│       │   │   └── DomainProperty.java      # Template-facing property model
│       │   ├── stereotype/
│       │   │   ├── Stereotype.java          # Base stereotype
│       │   │   ├── Id.java
│       │   │   ├── MVCList.java
│       │   │   ├── MVCForm.java
│       │   │   ├── MVCProperty.java
│       │   │   └── SortBy.java
│       │   └── parser/
│       │       ├── Reader.java              # SAX parser / XMI handler
│       │       ├── UmlModelResolver.java    # Association + stereotype resolution, UML→domain
│       │       ├── FileGenerator.java       # Generation orchestrator
│       │       ├── Loader.java              # FreeMarker engine wrapper
│       │       ├── Component.java           # Enum of generatable components
│       │       ├── StereotypeName.java      # Enum of supported stereotypes
│       │       ├── XmiConstants.java        # XMI structural constants
│       │       ├── XmiToJavaTypeMapper.java # Ecore → Java type mapping
│       │       └── TestConstants.java       # Configurable input/output paths
│       └── resources/static/                # FreeMarker templates (*.ftlh)
├── SpringMVCProfile/                        # Eclipse Papyrus UML profile
└── CirculationModel/                        # Example input model (BISIS circulation)
```

---

## Demonstration Domain

The `CirculationModel` folder holds the **circulation module of the BISIS library information
system**, modeled as **16 UML classes**. Running the generator over this model produces
**129 files** — six Java files and two HTML views per class, plus the shared `index.html`.

---

## Master's Thesis

Master's thesis, Faculty of Technical Sciences (FTN), University of Novi Sad.

- **Thesis (original title):** *Имплементација Spring MVC веб апликација уз ослонац на инжењерство вођено моделима*
  (Implementation of Spring MVC web applications supported based on model-driven engineering principles).
- **Defended:** July 10, 2026
- **Mentor:** Gordana Milosavljevic, PhD, Full Professor FTN Novi Sad
- **Author:** Aleksandar Radivojevic, M.Sc. in Electrical Engineering and Computer Science
