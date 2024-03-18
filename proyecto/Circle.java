import java.awt.*;
import java.awt.geom.*;

/**
 * Represents a circle that can be manipulated and drawn on a canvas.
 * 
 * This class provides methods to manipulate the position, size, color,
 * and visibility of the circle.
 * 
 * @author Michael Kolling and David J. Barnes
 * @version 1.0 (15 July 2000)
 */
public class Circle {
    public static final double PI = 3.1416;
    
    private int diameter;
    private int xPosition;
    private int yPosition;
    private String color;
    private boolean isVisible;
    
    /**
     * Constructs a new Circle object with default values.
     */
    public Circle() {
        diameter = 60;
        xPosition = 20;
        yPosition = 15;
        color = "blue";
        isVisible = false;
    }

    /**
     * Makes the circle visible on the canvas.
     */
    public void makeVisible() {
        isVisible = true;
        draw();
    }

    /**
     * Makes the circle invisible on the canvas.
     */
    public void makeInvisible() {
        erase();
        isVisible = false;
    }

    /**
     * Draws the circle on the canvas.
     */
    private void draw() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color, new Ellipse2D.Double(xPosition, yPosition, diameter, diameter));
            canvas.wait(1);
        }
    }

    /**
     * Erases the circle from the canvas.
     */
    private void erase() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }

    /**
     * Moves the circle horizontally by a given distance.
     * 
     * @param distance the distance to move the circle horizontally
     */
    public void moveHorizontal(int distance) {
        erase();
        xPosition += distance;
        draw();
    }

    /**
     * Moves the circle vertically by a given distance.
     * 
     * @param distance the distance to move the circle vertically
     */
    public void moveVertical(int distance) {
        erase();
        yPosition += distance;
        draw();
    }

    /**
     * Slowly moves the circle horizontally by a given distance.
     * 
     * @param distance the distance to move the circle horizontally
     */
    public void slowMoveHorizontal(int distance) {
        int delta = distance < 0 ? -1 : 1;
        distance = Math.abs(distance);
        for (int i = 0; i < distance; i++) {
            xPosition += delta;
            draw();
        }
    }

    /**
     * Slowly moves the circle vertically by a given distance.
     * 
     * @param distance the distance to move the circle vertically
     */
    public void slowMoveVertical(int distance) {
        int delta = distance < 0 ? -1 : 1;
        distance = Math.abs(distance);
        for (int i = 0; i < distance; i++) {
            yPosition += delta;
            draw();
        }
    }

    /**
     * Changes the size of the circle.
     * 
     * @param newDiameter the new diameter of the circle (in pixels)
     */
    public void changeSize(int newDiameter) {
        diameter = newDiameter;
        if (isVisible) {
            erase();
            draw();
        }
    }

    /**
     * Changes the color of the circle.
     * 
     * @param newColor the new color of the circle
     */
    public void changeColor(String newColor) {
        color = newColor;
        if (isVisible) {
            draw();
        }
    }
    
    /**
     * Sets the position of the circle to the specified coordinates.
     * 
     * @param x the x-coordinate of the circle
     * @param y the y-coordinate of the circle
     * @param wantToShowChanges indicates whether to show changes immediately
     */
    public void setPosition(int x, int y, boolean wantToShowChanges) {
        xPosition = x;
        yPosition = y;
        if (isVisible && wantToShowChanges) {
            erase();
            draw();
        }
    }
    
    /**
     * Gets the color of the circle.
     * 
     * @return the color of the circle
     */
    public String getColor() {
        return color;
    }
    
    /**
     * Gets the radius of the circle.
     * 
     * @return the radius of the circle
     */
    public int getRadius() {
        return diameter / 2;
    }
    
    /**
     * Gets the diameter of the circle.
     * 
     * @return the diameter of the circle
     */
    public int getDiameter() {
        return diameter;
    }
    
    /**
     * Gets the x-coordinate of the circle's top-left corner.
     * 
     * @return the x-coordinate of the circle's top-left corner
     */
    public int getXPosition() {
        return xPosition;
    }
    
    /**
     * Gets the y-coordinate of the circle's top-left corner.
     * 
     * @return the y-coordinate of the circle's top-left corner
     */
    public int getYPosition() {
        return yPosition;
    }
    
    /**
     * Gets the x-coordinate of the circle's center.
     * 
     * @return the x-coordinate of the circle's center
     */
    public int getXCenterPosition() {
        return xPosition + (diameter / 2);
    }
    
    /**
     * Gets the y-coordinate of the circle's center.
     * 
     * @return the y-coordinate of the circle's center
     */
    public int getYCenterPosition() {
        return yPosition + (diameter / 2);
    }
}