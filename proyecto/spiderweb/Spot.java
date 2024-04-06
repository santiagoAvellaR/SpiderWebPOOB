package spiderweb;
import shapes.*;
/**
 * Represents a spot on a canvas.
 * 
 * A spot is a small circle with a specified color, size, and position.
 * It can be made visible or invisible, and its position and color can be changed.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Spot {
    public static int size = 16;
    private Circle spotCircle;
    private int xPosition;
    private int yPosition;
    private String color;
    private int numberStrand;
    private boolean isVisible;

    /**
     * Constructor for objects of class Spot.
     * 
     * @param xPos the x-coordinate of the spot
     * @param yPos the y-coordinate of the spot
     * @param color1 the color of the spot
     * @param numbStrand the number of the strand associated with the spot
     */
    public Spot(int xPos, int yPos, String color1, int numbStrand) {
        spotCircle = new Circle();
        spotCircle.changeColor(color1);
        spotCircle.changeSize(size);
        xPosition = xPos;
        yPosition = yPos;
        spotCircle.setPosition(xPos, yPos, true);
        isVisible = false;
        numberStrand = numbStrand;
    }
    
    /**
     * Changes the color of the spot.
     * 
     * @param color the new color of the spot
     */
    public void changeColor(String color) {
        spotCircle.changeColor(color);
    }
    
    /**
     * Makes the spot visible on the canvas.
     */
    public void makeVisible() {
        spotCircle.makeVisible();
        isVisible = true;
    }
    
    /**
     * Makes the spot invisible on the canvas.
     */
    public void makeInvisible() {
        spotCircle.makeInvisible();
        isVisible = false;
    }
    
    /**
     * Sets the position of the spot to the specified coordinates.
     * 
     * @param xPos the new x-coordinate of the spot
     * @param yPos the new y-coordinate of the spot
     */
    public void setPosition(int xPos, int yPos) {
        xPosition = xPos;
        yPosition = yPos;
        spotCircle.setPosition(xPos, yPos, true);
    }
    
    /**
     * Retrieves the color of the spot.
     * 
     * @return the color of the spot
     */
    public String getColor() {
        return color;
    }
    
    /**
     * Retrieves the number of the strand associated with the spot.
     * 
     * @return the number of the strand associated with the spot
     */
    public int getNumberStrand() {
        return numberStrand;
    }
    
    /**
     * Retrieves the radius of the spot.
     * 
     * @return the radius of the spot
     */
    public int getRaius() {
        return spotCircle.getRadius();
    }
    
    /**
     * Retrieves the size of the spot.
     * 
     * @return the size of the spot
     */
    public int getSize() {
        return size;
    }
    
    /**
     * Retrieves the x-coordinate of the spot.
     * 
     * @return the x-coordinate of the spot
     */
    public int getXPosition() {
        return xPosition;
    }
    
    /**
     * Retrieves the y-coordinate of the spot.
     * 
     * @return the y-coordinate of the spot
     */
    public int getYPosition() {
        return yPosition;
    }
}