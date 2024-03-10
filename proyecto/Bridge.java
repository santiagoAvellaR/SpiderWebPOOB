import java.awt.*;

/**
 * Write a description of class bridge here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Bridge
{
    // instance variables - replace the example below with your own
    private int xPosition;
    private int yPosition;
    private  int radius;
    private boolean isVisible;
    private String color;
    private double tetha1;
    private double tetha2;
    private int strand1;
    private int strand2;

    /**
     * Constructor for objects of class bridge
     */
    public Bridge(double angle1, double angle2, int xPos, int yPos, int rad, String color1, int str1, int str2){
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
    
    public int getStrand1(){
        return strand1;
    }
    
    public int getStrand2(){
        return strand2;
    }
   
    public void setStrand2(int nstrand){
        strand2 = nstrand;
     }

    public void setTetha1(double ang){
        tetha1 = ang;
    }
    
    public void setTetha2(double ang){
        tetha2 = ang;
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
    
    private void draw(){
        Canvas canvas = Canvas.getCanvas();
        int[] xpoints = {xPosition + (int) (radius*(Math.cos(tetha1))), xPosition + (int) (radius*(Math.cos(tetha2)))};
        int[] ypoints = {yPosition - (int) (radius*(Math.sin(tetha1))), yPosition - (int) (radius*(Math.sin(tetha2)))};
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
    
    public double getTetha2(){
        return tetha2;
    }
    
    public int getRadius(){
        return radius;
    }
    
    public String getColor(){
        return color;
    }

}