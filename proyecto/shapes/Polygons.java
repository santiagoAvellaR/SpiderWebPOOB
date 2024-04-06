package shapes;


/**
 * Write a description of class Polygon here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Polygons extends Figure
{
    protected static int EDGES;
    protected int height;
    protected int width;
    /**
     * Constructor for objects of class Polygon
     */
    public Polygons()
    {
        super();
    }
    
    /**
     * Change the size to the new size
     * @param newHeight the new height in pixels. newHeight must be >=0.
     * @param newWidht the new width in pixels. newWidth must be >=0.
     */
    public void changeSize(int newHeight, int newWidth) {
        if(newHeight>0 && newWidth>0){
            height = newHeight;
            width = newWidth;
            if(isVisible){
                erase();
                draw();
            }
        }
    }
    
    /**
     * Returns the width of the polygon.
     * 
     * @return the width of the polygon
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * Returns the height of the polygon.
     * 
     * @return the height of the polygon
     */
    public int getHeight() {
        return height;
    }
}
