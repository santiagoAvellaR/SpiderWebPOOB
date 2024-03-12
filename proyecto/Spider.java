/**
 * Write a description of class Spider here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Spider{
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
     * Constructor for objects of class Spider
     */
    public Spider(int xPos, int yPos){
        xPosition = xPos;
        yPosition = yPos;
        isVisible = false;
        isCentered = true;
        
        body = new Circle();
        body.changeSize(16);
        body.changeColor("black");
        body.setPosition(xPosition, yPosition);
        
        int faceSize = (int) (body.getDiameter()*0.75); 
        face = new Circle();
        face.changeSize(faceSize);
        face.changeColor("black");
        
        int eyesSize = (int) (face.getRadius()*0.5);
        eyeL = new Triangle();
        eyeL.changeSize(eyesSize,eyesSize);
        eyeL.changeColor("green");
        
        eyeR = new Triangle();
        eyeR.changeSize(eyesSize,eyesSize);
        eyeR.changeColor("red");
        
        numberStrand = 0;
        radiusFromCenter = 0;
        visionAngle = Math.toRadians(90);
        organizeParts();
    }
    
    public void makeVisible(){
        if (!isVisible){
            body.makeVisible();
            face.makeVisible();
            eyeR.makeVisible();
            eyeL.makeVisible();
            isVisible = true;
        }
    }
    
    public void makeInvisible(){
        if (isVisible){
            face.makeInvisible();
            body.makeInvisible();
            eyeR.makeInvisible();
            eyeL.makeInvisible();
            isVisible = false;
        }
    }
    
    public int getXPosition(){
        return xPosition;
    }
    
    public int getYPosition(){
        return yPosition;
    }
    
    public void setNumberStrand(int newNumberStrand){
        numberStrand = newNumberStrand;
    }
    
    public int getRadiusFromCenter(){
        return radiusFromCenter;
    }
    
    public void setRadiusFromCenter(int newRadius){
        radiusFromCenter = newRadius;
    }
    
    public void changeSize(int newSize){
        body.changeSize(newSize);
        int faceSize = (int) (body.getDiameter()*0.6);
        face.changeSize(faceSize);
        int eyesSize = (int) (face.getRadius()*0.5);
        eyeR.changeSize(eyesSize,eyesSize);
        eyeL.changeSize(eyesSize,eyesSize);
        organizeParts();
        rotate(visionAngle);
    }
    
    private void organizeParts(){
        boolean wasVisible = isVisible?true:false;
        makeInvisible();
        face.setPosition(xPosition + body.getRadius() - face.getRadius(), yPosition + (int) (face.getDiameter()*0.1) - face.getDiameter());
        eyeR.setPosition(face.getXPosition() + face.getRadius() + eyeR.getWidth(), face.getYPosition() + face.getRadius() - eyeR.getHeight());
        eyeL.setPosition(face.getXPosition() + face.getRadius() - eyeL.getWidth(), face.getYPosition() + face.getRadius() - eyeL.getHeight());
        if(wasVisible){makeVisible();}
    }
    
    public void setPosition(int x, int y){
        setPosition(x, y, visionAngle);
    }
    
    public void setPosition(int x, int y, double tetha){
        boolean wasVisible;
        if(isVisible){
            wasVisible=true;
            makeInvisible();
        }
        else{wasVisible=false;}
        xPosition = x;
        yPosition = y;
        body.setPosition(x, y);
        organizeParts();
        if (wasVisible){
            makeVisible();
        }
        rotate(tetha);
    }
    
    private void rotateFace(double tetha){
        int x2 = body.getXCenterPosition();
        int y2 = body.getYCenterPosition();
        int x1 = face.getXCenterPosition();
        int y1 = face.getYCenterPosition();
        double lenBetweenCenters = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        int newXPosition = (int) (x2 + lenBetweenCenters*Math.cos(tetha));
        int newYPosition = (int) (y2 - lenBetweenCenters*Math.sin(tetha));
        face.setPosition(newXPosition - face.getRadius(), newYPosition - face.getRadius());
    }
    
    private void rotateEyes(double tetha){
        int x2 = body.getXCenterPosition();
        int y2 = body.getYCenterPosition();
        double lenBetweenCenters = Math.sqrt(Math.pow(body.getXCenterPosition() - eyeR.getXPosition(), 2) + Math.pow(body.getYCenterPosition() - eyeR.getYPosition(), 2));
        double angleBetweenEyes = Math.acos(((lenBetweenCenters * lenBetweenCenters) + (lenBetweenCenters * lenBetweenCenters) - (2*eyeR.getWidth() * 2*eyeR.getWidth())) / (2 * lenBetweenCenters * lenBetweenCenters));
        eyeR.setPosition(x2 + (int) (lenBetweenCenters*Math.cos(tetha+(double) (angleBetweenEyes/2.0))), y2 - (int) (lenBetweenCenters*Math.sin(tetha+(double) (angleBetweenEyes/2.0))));
        eyeL.setPosition(x2 + (int) (lenBetweenCenters*Math.cos(tetha-(double) (angleBetweenEyes/2.0))), y2 - (int) (lenBetweenCenters*Math.sin(tetha-(double) (angleBetweenEyes/2.0))));
        eyeR.rotate(Math.toDegrees(tetha));
        eyeL.rotate(Math.toDegrees(tetha));
    }
    
    public void rotate(double tetha){
        organizeParts();
        rotateFace(tetha);
        rotateEyes(tetha);
        visionAngle = tetha;
    }
    
    public void walkToTheBridge(int distance){
        if (distance > radiusFromCenter){
            for(int i = radiusFromCenter; i < distance; i++){
                setPosition(-body.getRadius() + SpiderWeb.xPosition + (int) (i*Math.cos(visionAngle)), -body.getRadius() + SpiderWeb.yPosition - (int) (i*Math.sin(visionAngle)));
            }
        }
        else{
            for(int i = radiusFromCenter; i > distance; i--){
                setPosition(-body.getRadius() + SpiderWeb.xPosition + (int) (i*Math.cos(visionAngle)), -body.getRadius() + SpiderWeb.yPosition - (int) (i*Math.sin(visionAngle)));
            }
        }
        setPosition(-body.getRadius() + SpiderWeb.xPosition + (int) (distance*Math.cos(visionAngle)), -body.getRadius() + SpiderWeb.yPosition - (int) (distance*Math.sin(visionAngle)));
        setRadiusFromCenter(distance);
    }
    
    public void crossTheBridge(double movementAngle, int newStrand){
        boolean clockWise = movementAngle < visionAngle;
        rotate(movementAngle);
        if(clockWise){
            for(double i = visionAngle; i < movementAngle; i-=0.00001){
                setPosition(-body.getRadius() + SpiderWeb.xPosition + (int) (radiusFromCenter*Math.cos(i)), -body.getRadius() + SpiderWeb.yPosition - (int) (radiusFromCenter*Math.sin(i)));
            }
        }
        else{
            for(double i = visionAngle; i < movementAngle; i+=0.00001){
                setPosition(-body.getRadius() + SpiderWeb.xPosition + (int) (radiusFromCenter*Math.cos(i)), -body.getRadius() + SpiderWeb.yPosition - (int) (radiusFromCenter*Math.sin(i)));
            }
        }
        setPosition(-body.getRadius() + SpiderWeb.xPosition + (int) (radiusFromCenter*Math.cos(movementAngle)), -body.getRadius() + SpiderWeb.yPosition - (int) (radiusFromCenter*Math.sin(movementAngle)), movementAngle);
        setNumberStrand(newStrand);
    }
    
    public double getVisionAngle(){
        return visionAngle;
    }
    
    public void setVisionAngle(double newAngle){
        visionAngle = newAngle;
        rotate(newAngle);
    }
    
    public boolean isCentered(){
        return isCentered;
    }
    
    public void setIsCentered(boolean bool){
        isCentered = bool;
    }
    
    public int getNumberStrand(){
        return numberStrand;
    }
}