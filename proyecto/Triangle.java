import java.awt.*;

/**
 * Represents a triangle that can be manipulated and drawn on a canvas.
 * 
 * This class provides methods to manipulate the position, size, color,
 * and visibility of the triangle, as well as methods for rotating it.
 * 
 * @author Michael Kolling and David J. Barnes
 * @version 1.0 (15 July 2000)
 */
public class Triangle {
    public static int VERTICES = 3;
    private int height;
    private int width;
    private int xPosition;
    private int yPosition;
    private String color;
    private boolean isVisible;

    /**
     * Constructs a new triangle at default position with default color.
     */
    public Triangle() {
        height = 30;
        width = 40;
        xPosition = 100;
        yPosition = 100;
        color = "green";
        isVisible = false;
    }

    /**
     * Makes this triangle visible. If it was already visible, does nothing.
     */
    public void makeVisible() {
        isVisible = true;
        draw();
    }
    
    /**
     * Makes this triangle invisible. If it was already invisible, does nothing.
     */
    public void makeInvisible() {
        erase();
        isVisible = false;
    }

    /**
     * Moves the triangle horizontally by a given distance.
     * 
     * @param distance the desired distance in pixels
     */
    public void moveHorizontal(int distance) {
        erase();
        xPosition += distance;
        draw();
    }

    /**
     * Moves the triangle vertically by a given distance.
     * 
     * @param distance the desired distance in pixels
     */
    public void moveVertical(int distance) {
        erase();
        yPosition += distance;
        draw();
    }

    /**
     * Slowly moves the triangle horizontally by a given distance.
     * 
     * @param distance the desired distance in pixels
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
     * Slowly moves the triangle vertically by a given distance.
     * 
     * @param distance the desired distance in pixels
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
     * Changes the size of the triangle to the new size.
     * 
     * @param newHeight the new height in pixels (must be >= 0)
     * @param newWidth the new width in pixels (must be >= 0)
     */
    public void changeSize(int newHeight, int newWidth) {
        height = newHeight;
        width = newWidth;
        if (isVisible) {
            erase();
            draw();
        }
    }
    
    /**
     * Changes the color of the triangle.
     * 
     * @param newColor the new color of the triangle
     */
    public void changeColor(String newColor) {
        color = newColor;
        if (isVisible) {
            makeInvisible();
            makeVisible();
        }
    }

    /**
     * Rotates the triangle by the specified angle.
     * 
     * @param angleRotation the angle of rotation in degrees
     */
    public void rotate(double angleRotation) {
        int lenTriangleSide = (int) (Math.sqrt(Math.pow(width/2, 2) + Math.pow(height, 2)));
        double angle = Math.toRadians(angleRotation) + Math.toRadians(180);
        double angleBetweenEdges = 2 * Math.atan2(width / 2.0, height);
        Canvas canvas = Canvas.getCanvas();
        int[] xpoints = { xPosition, xPosition + (int)(lenTriangleSide * Math.cos(angle - (angleBetweenEdges / 2))),
                xPosition + (int)(lenTriangleSide * Math.cos(angle + (angleBetweenEdges / 2))) };
        int[] ypoints = { yPosition, yPosition - (int) (lenTriangleSide * Math.sin(angle - (angleBetweenEdges / 2))),
                yPosition - (int) (lenTriangleSide * Math.sin(angle + (angleBetweenEdges / 2))) };
        canvas.draw(this, color, new Polygon(xpoints, ypoints, 3));
        canvas.wait(1);
        if (!isVisible) {
            makeInvisible();
        }
    }

    /**
     * Draws the triangle with its current specifications on the screen.
     */
    private void draw() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            int[] xpoints = { xPosition, xPosition + (width / 2), xPosition - (width / 2) };
            int[] ypoints = { yPosition, yPosition + height, yPosition + height };
            canvas.draw(this, color, new Polygon(xpoints, ypoints, 3));
            canvas.wait(1);
        }
    }
    
    /*
     * Erases the triangle from the screen.
     */
    private void erase() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
    
    /**
     * Sets the position of the triangle to the specified coordinates.
     * 
     * @param x the x-coordinate of the new position
     * @param y the y-coordinate of the new position
     * @param wantToShowChanges true if changes should be displayed, false otherwise
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
     * Returns the x-coordinate of the triangle's position.
     * 
     * @return the x-coordinate of the triangle's position
     */
    public int getXPosition() {
        return xPosition;
    }
    
    /**
     * Returns the y-coordinate of the triangle's position.
     * 
     * @return the y-coordinate of the triangle's position
     */
    public int getYPosition() {
        return yPosition;
    }
    
    /**
     * Returns the width of the triangle.
     * 
     * @return the width of the triangle
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * Returns the height of the triangle.
     * 
     * @return the height of the triangle
     */
    public int getHeight() {
        return height;
    }
}