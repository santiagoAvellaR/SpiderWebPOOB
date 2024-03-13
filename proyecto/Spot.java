
/**
 * Write a description of class Spot here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Spot
{
    public static int size = 16;
    private Circle spotCircle;
    private int xPosition;
    private int yPosition;
    private String color;
    private int numberStrand;
    private boolean isVisible;

    /**
     * Constructor for objects of class Spot
     */
    public Spot(int xPos, int yPos, String color1, int numbStrand){
        spotCircle = new Circle();
        spotCircle.changeColor(color1);
        spotCircle.changeSize(size);
        xPosition = xPos;
        yPosition = yPos;
        spotCircle.setPosition(xPos, yPos, true);
        isVisible = false;
        numberStrand = numbStrand;
    }
    
    public void changeColor(String color){
        spotCircle.changeColor(color);
    }
    
    public void makeVisible(){
        spotCircle.makeVisible();
        isVisible = true;
    }
    
    public void makeInvisible(){
        spotCircle.makeInvisible();
        isVisible = false;
    }
    
    public void setPosition(int xPos, int yPos){
        xPosition = xPos;
        yPosition = yPos;
        spotCircle.setPosition(xPos, yPos, true);
    }
    
    public String getColor(){
        return color;
    }
    
    public int getNumberStrand(){
        return numberStrand;
    }
    
    public int getRaius(){
        return spotCircle.getRadius();
    }
    
    public int getSize(){
        return size;
    }
    
    public int getXPosition(){
        return xPosition;
    }
    
    public int getYPosition(){
        return yPosition;
    }
}
