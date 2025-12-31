# WARP.md

This file provides guidance to WARP (warp.dev) when working with code in this repository.

## Project Overview

This is a Java3D graphics application demonstrating 3D rendering using the JOGL (Java OpenGL) implementation. The project uses Maven for build management and requires Java 11+.

## Essential Commands

### Build & Compile
```bash
mvn clean compile          # Compile the project
mvn clean package          # Package as JAR
```

### Run Application
```bash
mvn exec:java              # Run via Maven
java -jar target/java3d-project-1.0-SNAPSHOT.jar  # Run compiled JAR directly
```

### Testing
```bash
mvn test                   # Run all tests (currently no tests in project)
```

### Clean
```bash
mvn clean                  # Remove target directory and build artifacts
```

## Architecture

### Technology Stack
- **Java3D 1.7.1** (JOGL implementation) - 3D graphics API
- **JOGL 2.4.0** - Java OpenGL bindings for hardware-accelerated rendering
- **GlueGen Runtime** - Required for JOGL native library loading
- **Swing** - UI framework for windowing
- **Maven** - Build and dependency management

### Code Structure
The project follows a simple single-class architecture:

**`com.java3d.app.Java3DApp`** - Main application class that:
- Extends `JFrame` to provide the application window
- Creates a `SimpleUniverse` which manages the 3D viewing environment
- Builds a scene graph in `createSceneGraph()` containing:
  - **BranchGroup** (root): Top-level container for all scene elements
  - **TransformGroup nodes**: Enable positioning and animation of objects
  - **Geometry objects**: ColorCube and Sphere primitives
  - **Behavior nodes**: RotationInterpolator for cube animation
  - **Lighting**: DirectionalLight and AmbientLight for scene illumination
  - **BoundingSphere**: Defines the active region for behaviors and lighting

### Scene Graph Architecture
Java3D uses a scene graph paradigm where objects are organized in a tree structure:
```
BranchGroup (root)
├── TransformGroup (cube) [writable transform capability]
│   ├── ColorCube
│   └── RotationInterpolator [animated rotation behavior]
├── TransformGroup (sphere) [static position]
│   └── Sphere [with Material/Appearance]
├── DirectionalLight
└── AmbientLight
```

Key concepts:
- **Capabilities**: Must be set before compiling the scene graph (e.g., `ALLOW_TRANSFORM_WRITE` for animation)
- **Bounds**: Define spatial regions where behaviors and lights are active
- **Alpha**: Timing mechanism for animations (4000ms rotation cycle, infinite loops with `-1`)
- **Appearance/Material**: Define object surface properties (color, specularity, shininess)

### Platform Considerations

**Linux Display Issues**: If encountering graphics problems, software rendering can be forced:
```bash
export LIBGL_ALWAYS_SOFTWARE=1
```

**Native Libraries**: JOGL requires platform-specific native libraries. Maven dependencies (`jogl-all-main`, `gluegen-rt-main`) automatically include natives for all platforms (Linux, Windows, macOS).

## Development Patterns

### Adding New 3D Objects
To add objects to the scene, modify the `createSceneGraph()` method:
1. Create a `TransformGroup` for positioning/animation
2. Create the geometry (use `org.jogamp.java3d.utils.geometry.*` utilities)
3. Optionally set `Appearance` and `Material` for custom rendering
4. Add to the parent node before `root.compile()`

### Animation
Use `Alpha` (timing) + Interpolator (e.g., `RotationInterpolator`, `PositionInterpolator`) pattern:
- Alpha controls timing (duration, loop count)
- Interpolator modifies transform over time
- Must set `ALLOW_TRANSFORM_WRITE` capability on the TransformGroup
- Set scheduling bounds to activate the behavior

### Lighting
Scene requires proper lighting to visualize materials:
- `AmbientLight`: Provides base illumination (non-directional)
- `DirectionalLight`: Simulates distant light source (e.g., sun)
- Both require `setInfluencingBounds()` to define active region

## Main Class Location
The entry point is: `com.java3d.app.Java3DApp`

This is configured in pom.xml for both:
- `maven-jar-plugin` (manifest main class)
- `exec-maven-plugin` (mvn exec:java)
