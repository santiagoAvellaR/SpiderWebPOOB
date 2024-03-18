/**
 * Represents a spider on a canvas.
 * 
 * A spider is composed of various parts, including a body, face, and eyes.
 * It can move, rotate, change size, and interact with bridges and strands.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Spider {
    private int numberStrand;
    private int radiusFromCenter;
    private int xPosition;
    private int yPosition;
    private double visionAngle; 
    private Circle face;
    private Triangle eyeR;
    private Triangle eyeL;
    private Circle body;
    private boolean isVisible;
    private boolean isCentered;

    /**
     * Constructs a new spider with the specified position.
     * 
     * @param xPos the initial x-coordinate of the spider
     * @param yPos the initial y-coordinate of the spider
     */
    public Spider(int xPos, int yPos) {
        xPosition = xPos;
        yPosition = yPos;
        isVisible = false;
        isCentered = true;
        
        body = new Circle();
        body.changeSize(16);
        body.changeColor("black");
        body.setPosition(xPosition, yPosition, true);
        
        int faceSize = (int) (body.getDiameter() * 0.75); 
        face = new Circle();
        face.changeSize(faceSize);
        face.changeColor("black");
        
        int eyesSize = (int) (face.getRadius() * 0.5);
        eyeL = new Triangle();
        eyeL.changeSize(eyesSize, eyesSize);
        eyeL.changeColor("green");
        
        eyeR = new Triangle();
        eyeR.changeSize(eyesSize, eyesSize);
        eyeR.changeColor("red");
        
        numberStrand = 0;
        radiusFromCenter = 0;
        visionAngle = Math.toRadians(90);
        organizeParts();
    }
    
    /**
     * Makes the spider visible. If it was already visible, does nothing.
     */
    public void makeVisible() {
        if (!isVisible) {
            body.makeVisible();
            face.makeVisible();
            eyeR.makeVisible();
            eyeL.makeVisible();
            isVisible = true;
        }
    }
    
    /**
     * Makes the spider invisible. If it was already invisible, does nothing.
     */
    public void makeInvisible() {
        if (isVisible) {
            face.makeInvisible();
            body.makeInvisible();
            eyeR.makeInvisible();
            eyeL.makeInvisible();
            isVisible = false;
        }
    }
    
    /**
     * Gets the x-coordinate of the spider's center.
     * 
     * @return the x-coordinate of the spider
     */
    public int getXPosition() {
        return xPosition;
    }
    
    /**
     * Gets the y-coordinate of the spider's center.
     * 
     * @return the y-coordinate of the spider
     */
    public int getYPosition() {
        return yPosition;
    }
    
    /**
     * Sets the number of the strand the spider is on.
     * 
     * @param newNumberStrand the new strand number
     */
    public void setNumberStrand(int newNumberStrand) {
        numberStrand = newNumberStrand;
    }
    
    /**
     * Gets the distance of the spider from the center of the web.
     * 
     * @return the distance from the center of the web
     */
    public int getRadiusFromCenter() {
        return radiusFromCenter;
    }
    
    /**
     * Sets the distance of the spider from the center of the web.
     * 
     * @param newRadius the new radius from the center of the web
     */
    public void setRadiusFromCenter(int newRadius) {
        radiusFromCenter = newRadius;
    }
    
    /**
     * Changes the size of the spider.
     * 
     * @param newSize the new size of the spider
     */
    public void changeSize(int newSize) {
        body.changeSize(newSize);
        int faceSize = (int) (body.getDiameter() * 0.6);
        face.changeSize(faceSize);
        int eyesSize = (int) (face.getRadius() * 0.5);
        eyeR.changeSize(eyesSize, eyesSize);
        eyeL.changeSize(eyesSize, eyesSize);
        organizeParts();
        rotate(visionAngle);
    }
    
    /**
     * Organizes the parts of the spider relative to its body.
     */
    private void organizeParts() {
        face.setPosition(xPosition + body.getRadius() - face.getRadius(), yPosition + (int) (face.getDiameter() * 0.1) - face.getDiameter(), false);
        eyeR.setPosition(face.getXPosition() + face.getRadius() + eyeR.getWidth(), face.getYPosition() + face.getRadius() - eyeR.getHeight(), false);
        eyeL.setPosition(face.getXPosition() + face.getRadius() - eyeL.getWidth(), face.getYPosition() + face.getRadius() - eyeL.getHeight(), false);
    }
    
    /**
     * Sets the position of the spider.
     * 
     * @param x the new x-coordinate
     * @param y the new y-coordinate
     */
    public void setPosition(int x, int y) {
        setPosition(x, y, visionAngle);
    }
    
    /**
     * Sets the position and rotation of the spider.
     * 
     * @param x the new x-coordinate
     * @param y the new y-coordinate
     * @param tetha the new rotation angle in radians
     */
    public void setPosition(int x, int y, double tetha) {
        boolean wasVisible;
        if (isVisible) {
            wasVisible = true;
            makeInvisible();
        } else {
            wasVisible = false;
        }
        xPosition = x;
        yPosition = y;
        body.setPosition(x, y, true);
        organizeParts();
        rotate(tetha);
        if (wasVisible) {
            makeVisible();
        }
    }
    
    /**
     * Rotates the spider's face and eyes to the specified angle.
     * 
     * @param tetha the new rotation angle in radians
     */
    private void rotateFace(double tetha) {
        int x2 = body.getXCenterPosition();
        int y2 = body.getYCenterPosition();
        int x1 = face.getXCenterPosition();
        int y1 = face.getYCenterPosition();
        double lenBetweenCenters = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        int newXPosition = (int) (x2 + lenBetweenCenters * Math.cos(tetha));
        int newYPosition = (int) (y2 - lenBetweenCenters * Math.sin(tetha));
        face.setPosition(newXPosition - face.getRadius(), newYPosition - face.getRadius(), true);
    }
    
    /**
     * Rotates the spider's eyes to the specified angle.
     * 
     * @param tetha the new rotation angle in radians
     */
    private void rotateEyes(double tetha) {
        int x2 = body.getXCenterPosition();
        int y2 = body.getYCenterPosition();
        double lenBetweenCenters = Math.sqrt(Math.pow(body.getXCenterPosition() - eyeR.getXPosition(), 2) + Math.pow(body.getYCenterPosition() - eyeR.getYPosition(), 2));
        double angleBetweenEyes = Math.acos(((lenBetweenCenters * lenBetweenCenters) + (lenBetweenCenters * lenBetweenCenters) - (2 * eyeR.getWidth() * 2 * eyeR.getWidth())) / (2 * lenBetweenCenters * lenBetweenCenters));
        eyeR.setPosition(x2 + (int) (lenBetweenCenters * Math.cos(tetha + (double) (angleBetweenEyes / 2.0))), y2 - (int) (lenBetweenCenters * Math.sin(tetha + (double) (angleBetweenEyes / 2.0))), true);
        eyeL.setPosition(x2 + (int) (lenBetweenCenters * Math.cos(tetha - (double) (angleBetweenEyes / 2.0))), y2 - (int) (lenBetweenCenters * Math.sin(tetha - (double) (angleBetweenEyes / 2.0))), true);
        eyeR.rotate(Math.toDegrees(tetha));
        eyeL.rotate(Math.toDegrees(tetha));
    }
    
    /**
     * Rotates the spider to the specified angle.
     * 
     * @param tetha the new rotation angle in radians
     */
    public void rotate(double tetha) {
        rotateFace(tetha);
        rotateEyes(tetha);
        visionAngle = tetha;
    }
    
    /**
     * Makes the spider walk to the specified distance from the center of the web.
     * 
     * @param distance the distance to walk from the center of the web
     */
    public void walkToTheBridge(int distance) {
        if (distance > radiusFromCenter) {
            for (int i = radiusFromCenter; i < distance; i++) {
                setPosition(-body.getRadius() + SpiderWeb.xPosition + (int) (i * Math.cos(visionAngle)), -body.getRadius() + SpiderWeb.yPosition - (int) (i * Math.sin(visionAngle)));
            }
        } else {
            for (int i = radiusFromCenter; i > distance; i--) {
                setPosition(-body.getRadius() + SpiderWeb.xPosition + (int) (i * Math.cos(visionAngle)), -body.getRadius() + SpiderWeb.yPosition - (int) (i * Math.sin(visionAngle)));
            }
        }
        setPosition(-body.getRadius() + SpiderWeb.xPosition + (int) (distance * Math.cos(visionAngle)), -body.getRadius() + SpiderWeb.yPosition - (int) (distance * Math.sin(visionAngle)));
        rotate(visionAngle);
        setRadiusFromCenter(distance);
    }
    
    /**
     * Makes the spider cross the bridge at the specified angle and strand.
     * 
     * @param movementAngle the angle at which the spider moves across the bridge
     * @param newStrand the number of the new strand after crossing the bridge
     */
    public void crossTheBridge(double movementAngle, int newStrand) {
        boolean clockWise = movementAngle < visionAngle;
        rotate(movementAngle);
        if (clockWise) {
            for (double i = visionAngle; i > movementAngle; i -= 0.000000000001) {
                setPosition(-body.getRadius() + SpiderWeb.xPosition + (int) (radiusFromCenter * Math.cos(i)), -body.getRadius() + SpiderWeb.yPosition - (int) (radiusFromCenter * Math.sin(i)));
            }
        } else {
            for (double i = visionAngle; i < movementAngle; i += 0.000000000001) {
                setPosition(-body.getRadius() + SpiderWeb.xPosition + (int) (radiusFromCenter * Math.cos(i)), -body.getRadius() + SpiderWeb.yPosition - (int) (radiusFromCenter * Math.sin(i)));
            }
        }
        setPosition(-body.getRadius() + SpiderWeb.xPosition + (int) (radiusFromCenter * Math.cos(movementAngle)), -body.getRadius() + SpiderWeb.yPosition - (int) (radiusFromCenter * Math.sin(movementAngle)), movementAngle);
        rotate(movementAngle);
        setNumberStrand(newStrand);
    }
    
    /**
     * Gets the spider's vision angle.
     * 
     * @return the spider's vision angle in radians
     */
    public double getVisionAngle() {
        return visionAngle;
    }
    
    /**
     * Sets the spider's vision angle.
     * 
     * @param newAngle the new vision angle in radians
     */
    public void setVisionAngle(double newAngle) {
        visionAngle = newAngle;
        rotate(newAngle);
    }
    
    /**
     * Checks if the spider is centered.
     * 
     * @return true if the spider is centered, false otherwise
     */
    public boolean isCentered() {
        return isCentered;
    }
    
    /**
     * Sets whether the spider is centered.
     * 
     * @param bool true to set the spider as centered, false otherwise
     */
    public void setIsCentered(boolean bool) {
        isCentered = bool;
    }
    
    /**
     * Gets the number of the strand the spider is on.
     * 
     * @return the number of the strand
     */
    public int getNumberStrand() {
        return numberStrand;
    }
}