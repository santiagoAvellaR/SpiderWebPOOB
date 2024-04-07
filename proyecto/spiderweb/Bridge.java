package spiderweb;
import shapes.*;
import java.awt.*;

/**
 * Represents a bridge between two strands on a canvas.
 * 
 * A bridge is defined by its position, radius, color, and angles of rotation.
 * It can be made visible or invisible.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Bridge {
    private int xPosition;
    private int yPosition;
    private int radius;
    private boolean isVisible;
    private String color;
    private double tetha1;
    private double tetha2;
    private int strand1;
    private int strand2;

    /**
     * Constructs a new bridge with the specified properties.
     * 
     * @param angle1 the angle of rotation for the first end of the bridge in degrees
     * @param angle2 the angle of rotation for the second end of the bridge in degrees
     * @param xPos the x-coordinate of the bridge's center
     * @param yPos the y-coordinate of the bridge's center
     * @param rad the radius of the bridge
     * @param color1 the color of the bridge
     * @param str1 the index of the first strand connected to the bridge
     * @param str2 the index of the second strand connected to the bridge
     */
    public Bridge(double angle1, double angle2, int xPos, int yPos, int rad, String color1, int str1, int str2) {
        tetha1 = angle1;
        tetha2 = angle2;
        xPosition = xPos;
        yPosition = yPos;
        radius = rad;
        isVisible = false;
        color = color1;
        strand1 = str1;
        strand2 = str2;
    }
    
    /**
     * Gets the index of the first strand connected to the bridge.
     * 
     * @return the index of the first strand
     */
    public int getStrand1() {
        return strand1;
    }
    
    /**
     * Gets the index of the second strand connected to the bridge.
     * 
     * @return the index of the second strand
     */
    public int getStrand2() {
        return strand2;
    }
    
    /**
     * Sets the index of the second strand connected to the bridge.
     * 
     * @param nstrand the new index of the second strand
     */
    public void setStrand1(int nstrand) {
        strand1 = nstrand;
    }
    
    /**
     * Sets the index of the second strand connected to the bridge.
     * 
     * @param nstrand the new index of the second strand
     */
    public void setStrand2(int nstrand) {
        strand2 = nstrand;
    }
    
    /**
     * Gets the angle of rotation for the first end of the bridge.
     * 
     * @return the angle of rotation in degrees
     */
    public double getTetha1() {
        return tetha1;
    }
    
    /**
     * Gets the angle of rotation for the second end of the bridge.
     * 
     * @return the angle of rotation in degrees
     */
    public double getTetha2() {
        return tetha2;
    }
    
    /**
     * Sets the angle of rotation for the first end of the bridge.
     * 
     * @param ang the new angle of rotation in degrees
     */
    public void setTetha1(double ang) {
        tetha1 = ang;
    }
    
    /**
     * Sets the angle of rotation for the second end of the bridge.
     * 
     * @param ang the new angle of rotation in degrees
     */
    public void setTetha2(double ang) {
        tetha2 = ang;
    }
    
    /**
     * Makes the bridge visible. If it was already visible, does nothing.
     */
    public void makeVisible() {
        if (!isVisible) {
            isVisible = true;
            draw();
        }
    }
    
    /**
     * Makes the bridge invisible. If it was already invisible, does nothing.
     */
    public void makeInvisible() {
        if (isVisible) {
            erase();
            isVisible = false;
        }
    }
    
    /**
     * Draws the bridge with its current specifications on the screen.
     */
    private void draw() {
        shapes.Canvas canvas = shapes.Canvas.getCanvas();
        int[] xpoints = {xPosition + (int) (radius * Math.cos(tetha1)), xPosition + (int) (radius * Math.cos(tetha2))};
        int[] ypoints = {yPosition - (int) (radius * Math.sin(tetha1)), yPosition - (int) (radius * Math.sin(tetha2))};
        canvas.draw(this, color, new Polygon(xpoints, ypoints, 2));
        canvas.wait(2);
    }
    
    /**
     * Erases the bridge from the screen.
     */
    private void erase() {
        if (isVisible) {
            shapes.Canvas canvas = shapes.Canvas.getCanvas();
            canvas.erase(this);
        }
    }
    
    /**
     * Gets the radius of the bridge.
     * 
     * @return the radius of the bridge
     */
    public int getRadius() {
        return radius;
    }
    
    /**
     * Gets the color of the bridge.
     * 
     * @return the color of the bridge
     */
    public String getColor() {
        return color;
    }
}
