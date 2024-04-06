package shapes;
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
public class Triangle extends Polygons{
    /**
     * Constructs a new triangle at default position with default color.
     */
    public Triangle() {
        super();
        height = 30;
        width = 40;
        xPosition = 100;
        yPosition = 100;
        color = "green";
        EDGES = 3;
    }
    
    /**
     * Draws the triangle with its current specifications on the screen.
     */
    @Override
    protected void draw() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            int[] xpoints = { xPosition, xPosition + (width / 2), xPosition - (width / 2) };
            int[] ypoints = { yPosition, yPosition + height, yPosition + height };
            canvas.draw(this, color, new Polygon(xpoints, ypoints, 3));
            canvas.wait(1);
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
}