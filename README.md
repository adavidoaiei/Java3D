# Java3D Project

A simple Java3D application that demonstrates basic 3D graphics rendering with a rotating colored cube and a sphere.

## Features

- Rotating multicolored cube
- Static green sphere with material properties
- Directional and ambient lighting
- Built with Java3D (JOGL implementation)

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- OpenGL-capable graphics card

## Dependencies

This project uses:
- **Java3D 1.7.1** (JOGL implementation)
- **JOGL 2.4.0** (Java OpenGL bindings)
- **JUnit 5.9.3** (for testing)

All dependencies are managed through Maven and will be automatically downloaded.

## Building the Project

To compile the project:

```bash
mvn clean compile
```

To package the project as a JAR:

```bash
mvn clean package
```

## Running the Application

Run the application using Maven:

```bash
mvn exec:java
```

Or run the compiled JAR directly:

```bash
java -jar target/java3d-project-1.0-SNAPSHOT.jar
```

## Project Structure

```
java3d-project/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── java3d/
│   │   │           └── app/
│   │   │               └── Java3DApp.java
│   │   └── resources/
│   └── test/
│       ├── java/
│       └── resources/
├── pom.xml
└── README.md
```

## What the Application Does

When you run the application, you'll see:
1. A window displaying a 3D scene
2. A multicolored cube rotating around its center
3. A green sphere positioned to the right of the cube
4. Proper lighting that illuminates the objects

## Troubleshooting

### Display Issues
If you encounter display issues on Linux, you may need to set:
```bash
export LIBGL_ALWAYS_SOFTWARE=1
```

### Native Library Errors
Java3D requires native libraries. Maven should handle this automatically through the `jogl-all-main` and `gluegen-rt-main` dependencies which include platform-specific natives.

## Development

To add new 3D objects or modify the scene, edit the `createSceneGraph()` method in `Java3DApp.java`.

## License

This is a sample project for learning purposes.
