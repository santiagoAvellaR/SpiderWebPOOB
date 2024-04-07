package spiderweb;
import java.util.ArrayList;
import java.util.Random;

/**
 * Write a description of class Thick here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Thick extends Bridge
{
    private ArrayList<Bridge> bridges = new ArrayList<>();
    private int thick;
    /**
     * Constructor for objects of class Thick
     */
    public Thick(double angle1, double angle2, int xPos, int yPos, int rad, String color1, int str1, int str2){
        super(angle1, angle2, xPos, yPos, rad, color1, str1, str2);
        thick = getRandomNumberInRange(2,8);
        int i = 0;
        int extraThick = 1;
        while(i <= (int) (thick/2)){
            Bridge bridge = new Bridge(angle1, angle2, xPos, yPos, rad + i, color1, str1, str2);
            bridges.add(bridge);
            i++;
        }
        i*=-1;
        while(i > (int) (thick*-1)){
            Bridge bridge = new Bridge(angle1, angle2, xPos, yPos, rad + i, color1, str1, str2);
            bridges.add(bridge);
            i--;
        }
    }
    
    @Override
    public void makeVisible(){
        if(!isVisible){
            for(Bridge bridge : bridges){
                bridge.makeVisible();
            }
            isVisible = true;
        }
    }
    
    @Override
    public void makeInvisible(){
        if(isVisible){
            for(Bridge bridge : bridges){
                bridge.makeInvisible();
            }
            isVisible = false;
        }
    }
    
    public void makeThinner(){
        if(bridges.size() >= 1){
            Bridge bridge = bridges.get(bridges.size()-1);
            bridge.makeInvisible();
            bridges.remove(bridge);
            thick-=1;
        }
    }
    
    public int getThick(){
        return thick;
    }
    
    private static int getRandomNumberInRange(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
}
