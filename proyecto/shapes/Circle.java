package shapes;
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
public class Circle extends Figure{
    protected int diameter;
    
    /**
     * Constructs a new Circle object with default values.
     */
    public Circle() {
        super();
        diameter = 60;
        xPosition = 20;
        yPosition = 15;
        color = "blue";
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
     * Draws the circle on the canvas.
     */
    @Override
    protected void draw() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color, new Ellipse2D.Double(xPosition, yPosition, diameter, diameter));
            canvas.wait(1);
        }
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
    
    /**
     * Rotates the circle by the specified angle.
     * 
     * @param angleRotation the angle of rotation in degrees
     */
    @Override
    public void rotate(double angleRotation) {
    }
}