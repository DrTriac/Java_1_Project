package be.kdg.demo_raycaster.engine.view;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;

import java.util.HashSet;
import java.util.Set;

public class GameView {
    //set height and width of window size
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;


    //initiate canvas node from javaFx to draw on the screen. make canvas the size of the window.
    //only this class needs access to it
    //if this would not be private, we would break the MVP structure. this could be package final.
    //final because we dont want no part of the program to be able to draw another one.
    /* The graphicsContext is the object that actually draws on the screen. */
    private final Canvas canvas = new Canvas(WIDTH,HEIGHT);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();
    //keycodes stored in a set for handeling keypresses.
    private final Set<KeyCode> keys = new HashSet<KeyCode>();
    private final Scene scene;



    public GameView()
    {
        /*initialising stackpane template. this is used in our engine because its centered by default,
        and because every child node is placed in the same space. its a transparent set of layers

        Stage (window)
        ↓
        Scene (UI container)
        ↓
        StackPane (root layout)
        ↓
        Canvas, Buttons, Images, etc.

         */
        StackPane root = new StackPane(canvas);
        scene = new Scene(root);

        //Keystroke handeling using Lambdas
        scene.setOnKeyPressed(e -> keys.add(e.getCode()));
        scene.setOnKeyReleased(e -> keys.remove(e.getCode()));
    }
    public Scene getScene() {
        return scene;
    }

    public GraphicsContext getGc() {
        return gc;
    }
    // see what keys are pressed
    public Boolean isKeyPressed(KeyCode key)
    {
        return keys.contains(key);
    }
}
