import java.awt.*;

/**
 * Write a description of class Strand here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Strand
{
    // instance variables - replace the example below with your own
    private int xPosition;
    private int yPosition;
    private  int length;
    private boolean isVisible;
    private String color;
    private double tetha1;
    private boolean hasSpot;

    /**
     * Constructor for objects of class Strand
     */
    public Strand(int xPos, int yPos, int rad, double angle)
    {
        // initialise instance variables
        tetha1 = Math.toRadians(angle);
        xPosition = xPos;
        yPosition = yPos;
        length = rad;
        isVisible = false;
        color = "black";
    }
    
    public void makeVisible(){
        isVisible = true;
        draw();
    }
    
    public void makeInvisible(){
        if (isVisible){
            erase();
            isVisible = false;
        }
    }
    
    public void rotate(double newTetha){
        tetha1 = newTetha;
        if (isVisible){
            erase();
            draw();
        }
    }
    
    public void enlarge(double percentage){
        length = (int) (length*percentage);
        if (isVisible){draw();}
    }
    
    private void draw(){
        Canvas canvas = Canvas.getCanvas();
        int[] xpoints = {xPosition, xPosition + (int) (length*(Math.cos(tetha1)))};
        int[] ypoints = {yPosition, yPosition - (int) (length*(Math.sin(tetha1)))};
        canvas.draw(this, color, new Polygon(xpoints, ypoints, 2));
        canvas.wait(2);
    }
    
    private void erase(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
    
    public double getTetha1(){
        return tetha1;
    }
    
    public int getLength(){
        return length;
    }
    
    public int[] getPositions(){
        int[] pos = {xPosition, yPosition};
        return pos;
    }
    
    public String getColor(){
        return color;
    }
    
    public void setTetha1(double tethanew){
        tetha1 =  Math.toRadians(tethanew);
    }
    
    public boolean hasSpot(){
        return hasSpot;
    }
    
    public void setHasSpot(boolean bool){
        hasSpot = bool;
    }
}
