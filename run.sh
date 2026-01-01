#!/bin/bash
# Run script for Java3D application with required JVM arguments for Java 25+

export MAVEN_OPTS="--enable-native-access=ALL-UNNAMED --add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.desktop/sun.awt=ALL-UNNAMED --add-opens=java.desktop/sun.awt.X11=ALL-UNNAMED --add-opens=java.desktop/sun.java2d.opengl=ALL-UNNAMED"

mvn exec:java
