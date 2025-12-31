package com.java3d.app;

import org.scijava.java3d.*;
import org.scijava.java3d.utils.geometry.ColorCube;
import org.scijava.java3d.utils.geometry.Sphere;
import org.scijava.java3d.utils.universe.SimpleUniverse;
import org.scijava.vecmath.*;

import javax.swing.*;
import java.awt.*;

/**
 * A simple Java3D application that displays a rotating colored cube and sphere.
 */
public class Java3DApp extends JFrame {
    
    private SimpleUniverse universe;
    private BranchGroup scene;
    
    public Java3DApp() {
        super("Java3D Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        
        // Create the canvas and add it to the frame
        Canvas3D canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        getContentPane().add(canvas, BorderLayout.CENTER);
        
        // Create the universe
        universe = new SimpleUniverse(canvas);
        
        // Position the viewer
        universe.getViewingPlatform().setNominalViewingTransform();
        
        // Create and add the scene
        scene = createSceneGraph();
        universe.addBranchGraph(scene);
        
        setVisible(true);
    }
    
    /**
     * Creates the scene graph with a rotating cube and sphere.
     */
    private BranchGroup createSceneGraph() {
        BranchGroup root = new BranchGroup();
        
        // Create a transform group for the cube
        TransformGroup cubeTransform = new TransformGroup();
        cubeTransform.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        
        // Add a colored cube
        ColorCube cube = new ColorCube(0.4);
        cubeTransform.addChild(cube);
        
        // Create rotation behavior for the cube
        Transform3D yAxis = new Transform3D();
        Alpha rotationAlpha = new Alpha(-1, 4000);
        RotationInterpolator rotator = new RotationInterpolator(
            rotationAlpha, cubeTransform, yAxis, 0.0f, (float) Math.PI * 2.0f
        );
        
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        rotator.setSchedulingBounds(bounds);
        cubeTransform.addChild(rotator);
        
        // Add the cube transform to the root
        root.addChild(cubeTransform);
        
        // Create a sphere
        TransformGroup sphereTransform = new TransformGroup();
        Transform3D spherePosition = new Transform3D();
        spherePosition.setTranslation(new Vector3f(1.5f, 0.0f, 0.0f));
        sphereTransform.setTransform(spherePosition);
        
        Appearance sphereAppearance = new Appearance();
        Material sphereMaterial = new Material();
        sphereMaterial.setDiffuseColor(new Color3f(0.2f, 0.8f, 0.2f));
        sphereMaterial.setSpecularColor(new Color3f(1.0f, 1.0f, 1.0f));
        sphereMaterial.setShininess(100.0f);
        sphereAppearance.setMaterial(sphereMaterial);
        
        Sphere sphere = new Sphere(0.3f, sphereAppearance);
        sphereTransform.addChild(sphere);
        root.addChild(sphereTransform);
        
        // Add lighting
        DirectionalLight light = new DirectionalLight(
            new Color3f(1.0f, 1.0f, 1.0f),
            new Vector3f(-1.0f, -1.0f, -1.0f)
        );
        light.setInfluencingBounds(bounds);
        root.addChild(light);
        
        AmbientLight ambientLight = new AmbientLight(new Color3f(0.3f, 0.3f, 0.3f));
        ambientLight.setInfluencingBounds(bounds);
        root.addChild(ambientLight);
        
        // Compile the scene
        root.compile();
        
        return root;
    }
    
    public static void main(String[] args) {
        try {
            if (GraphicsEnvironment.isHeadless()) {
                System.err.println("No DISPLAY found: running in headless environment. Start an X server or use Xvfb, or run with a real display.");
                System.exit(1);
            }
        } catch (HeadlessException he) {
            System.err.println("No DISPLAY found: running in headless environment. Start an X server or use Xvfb, or run with a real display.");
            System.exit(1);
        }

        try {
            Toolkit.getDefaultToolkit();
        } catch (HeadlessException he) {
            System.err.println("No DISPLAY found: running in headless environment. Start an X server or use Xvfb, or run with a real display.");
            System.exit(1);
        }

        SwingUtilities.invokeLater(() -> {
            new Java3DApp();
        });
    }
}
