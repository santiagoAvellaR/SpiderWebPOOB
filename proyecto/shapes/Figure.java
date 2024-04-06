package shapes;


/**
 * Write a description of class Figura here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Figure
{
    protected int xPosition;
    protected int yPosition;
    protected String color;
    protected boolean isVisible;

    /**
     * Constructor for objects of class Figura
     */
    public Figure(){
        isVisible = false;
    }
    
    /**
     * Make this rectangle visible. If it was already visible, do nothing.
     */
    public void makeVisible(){
        isVisible = true;
        draw();
    }
    
    /**
     * Make this rectangle invisible. If it was already invisible, do nothing.
     */
    public void makeInvisible(){
        erase();
        isVisible = false;
    }
    
    /**
     * Moves the rectangle horizontally by a given distance.
     * 
     * @param distance the desired distance in pixels
     */
    public void moveHorizontal(int distance) {
        xPosition += distance;
        if(isVisible){
            erase();
            draw();
        }
    }

    /**
     * Moves the rectangle vertically by a given distance.
     * 
     * @param distance the desired distance in pixels
     */
    public void moveVertical(int distance) {
        yPosition += distance;
        if(isVisible){
            erase();
            draw();
        }
    }
    
    /**
     * Change the color. 
     * @param color the new color. Valid colors are "red", "yellow", "blue", "green",
     * "magenta" and "black".
     */
    public void changeColor(String newColor){
        color = newColor;
        if(isVisible){draw();}
    }

    /**
     * Erase the rectangle on screen.
     */
    protected void erase(){
        if(isVisible) {
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
     * Returns the color of the triangle.
     * 
     * @return the color of the triangle
     */
    public String getColor() {
        return color;
    }
    
    /**
     * Returns the y-coordinate of the triangle's position.
     * 
     * @return the y-coordinate of the triangle's position
     */
    public int getYPosition() {
        return yPosition;
    }
    
    protected abstract void draw();
    public abstract void rotate(double angleRotation);
}
