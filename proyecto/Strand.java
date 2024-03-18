import java.awt.*;

/**
 * Represents a strand that can be manipulated and drawn on a canvas.
 * 
 * A strand is defined by its position, length, color, and angle.
 * It can be made visible or invisible, rotated, and enlarged.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Strand {
    private int xPosition;
    private int yPosition;
    private int length;
    private boolean isVisible;
    private String color;
    private double tetha1;
    private boolean hasSpot;

    /**
     * Constructs a new strand with the specified properties.
     * 
     * @param xPos the x-coordinate of the starting position
     * @param yPos the y-coordinate of the starting position
     * @param rad the length of the strand
     * @param angle the angle of rotation in degrees
     * @param color the color of the strand
     */
    public Strand(int xPos, int yPos, int rad, double angle, String color) {
        tetha1 = Math.toRadians(angle);
        xPosition = xPos;
        yPosition = yPos;
        length = rad;
        isVisible = false;
        this.color = color;
    }
    
    /**
     * Makes this strand visible. If it was already visible, does nothing.
     */
    public void makeVisible() {
        isVisible = true;
        draw();
    }
    
    /**
     * Makes this strand invisible. If it was already invisible, does nothing.
     */
    public void makeInvisible() {
        if (isVisible) {
            erase();
            isVisible = false;
        }
    }
    
    /**
     * Rotates the strand to the specified angle.
     * 
     * @param newTetha the new angle of rotation in degrees
     */
    public void rotate(double newTetha) {
        tetha1 = Math.toRadians(newTetha);
        if (isVisible) {
            erase();
            draw();
        }
    }
    
    /**
     * Enlarges or reduces the length of the strand by a percentage.
     * 
     * @param percentage the percentage by which to change the length
     */
    public void enlarge(double percentage) {
        length = (int) (length * percentage);
        if (isVisible) {
            draw();
        }
    }
    
    /**
     * Draws the strand with its current specifications on the screen.
     */
    private void draw() {
        Canvas canvas = Canvas.getCanvas();
        int[] xpoints = {xPosition, xPosition + (int) (length * Math.cos(tetha1))};
        int[] ypoints = {yPosition, yPosition - (int) (length * Math.sin(tetha1))};
        canvas.draw(this, color, new Polygon(xpoints, ypoints, 2));
        canvas.wait(2);
    }
    
    /**
     * Erases the strand from the screen.
     */
    private void erase() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
    
    /**
     * Gets the angle of rotation of the strand.
     * 
     * @return the angle of rotation in radians
     */
    public double getTetha1() {
        return tetha1;
    }
    
    /**
     * Gets the length of the strand.
     * 
     * @return the length of the strand
     */
    public int getLength() {
        return length;
    }
    
    /**
     * Gets the position of the strand.
     * 
     * @return an array containing the x and y coordinates of the strand's position
     */
    public int[] getPositions() {
        int[] pos = {xPosition, yPosition};
        return pos;
    }
    
    /**
     * Gets the color of the strand.
     * 
     * @return the color of the strand
     */
    public String getColor() {
        return color;
    }
    
    /**
     * Sets the angle of rotation of the strand.
     * 
     * @param tethanew the new angle of rotation in degrees
     */
    public void setTetha1(double tethanew) {
        tetha1 = Math.toRadians(tethanew);
    }
    
    /**
     * Checks if the strand has a spot.
     * 
     * @return true if the strand has a spot, false otherwise
     */
    public boolean hasSpot() {
        return hasSpot;
    }
    
    /**
     * Sets whether the strand has a spot.
     * 
     * @param bool true if the strand has a spot, false otherwise
     */
    public void setHasSpot(boolean bool) {
        hasSpot = bool;
    }
}