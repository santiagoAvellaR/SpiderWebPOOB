import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Collections;
import java.util.Scanner;

/**
 * Represents a spider web consisting of strands, bridges, spots, and a spider.
 * Strands form the main structure of the web, bridges connect strands, spots mark specific locations,
 * and a spider can traverse the web.
 * 
 * The SpiderWeb class provides methods for managing the web, including adding and removing bridges and spots,
 * controlling the spider's movement, and visualizing the web.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class SpiderWeb{
    private int largeStrand;
    public static int xPosition;
    public static int yPosition;
    private int numberStrands;
    private boolean isVisible;
    private boolean ok;
    // strands
    private ArrayList<Strand> strands = new ArrayList<>();
    // bridges
    private ArrayList<String> bridgesColors = new ArrayList<>();
    private ArrayList<Integer> bridgesNumberStrand = new ArrayList<>();
    private ArrayList<Bridge> bridges = new ArrayList<>();
    private Map<Integer, Map<Integer, Bridge>> strandsBridgesMap= new HashMap<>();  // numberStrand -> radius -> Bridge
    private ArrayList<String> usedBridges = new ArrayList<>();
    // spots
    private Map<String, Spot> spotsMap = new HashMap<>();
    private ArrayList<String> reachableSpots = new ArrayList<>();
    // spider
    private Spider spider;
    private ArrayList<Integer> spiderTrackerRadius = new ArrayList<>();
    private ArrayList<Integer> spiderTrackerStrands = new ArrayList<>();
    private ArrayList<Strand> spiderLastPathStrand = new ArrayList<>();
    private ArrayList<Bridge> spiderLastPathBridge = new ArrayList<>();
    
    private String[] availableColors = {};
    
    /**
     * Constructor for objects of class SpiderWeb with a specified number of strands and radius.
     * 
     * @param strands the number of strands in the web
     * @param radio the radius of the strands
     */
    public SpiderWeb(int strands, int radio) {
        numberStrands = strands;
        largeStrand = radio;
        xPosition = 450;
        yPosition = 350;
        isVisible = false;
        spider = new Spider(xPosition-8, yPosition-8);
        double angle = (double) 360 / (double) numberStrands;
        for (double i = 0; i < 360; i += angle) {
            Strand strand = new Strand(xPosition, yPosition, largeStrand, i, "black");
            this.strands.add(strand);
            strandsBridgesMap.put(this.strands.indexOf(strand), new HashMap<>());
        }
        ok = true;
    }
    
    /**
     * Constructor for objects of class SpiderWeb with a specified number of strands, a favorite color, and bridge information.
     * 
     * @param strands the number of strands in the web
     * @param favorite a favorite color for the bridges
     * @param bridges a 2D array containing bridge information
     */
    public SpiderWeb(int strands, int favorite, int[][] bridges){
        numberStrands = strands;
        largeStrand = 120;
        xPosition = 450;
        yPosition = 350;
        isVisible = false;
        spider = new Spider(xPosition-8, yPosition-8);
        double angle = (double) 360 / (double) numberStrands;
        for (double i = 0; i < 360; i += angle) {
            Strand strand = new Strand(xPosition, yPosition, largeStrand, i, "black");
            this.strands.add(strand);
            strandsBridgesMap.put(this.strands.indexOf(strand), new HashMap<>());
        }
        ok = true;
        int indexColor = 0;
        for (int[] info : bridges){
            int numberStrand = info[1]-1;
            int radius = info[0]; 
            Bridge bridge = new Bridge(this.strands.get(numberStrand).getTetha1(), this.strands.get(numberStrand+1).getTetha1(), xPosition, yPosition, radius, availableColors[indexColor], numberStrand, numberStrand+1);
        }
    }
    
    /**
     * Adds a bridge to the spider web.
     * 
     * @param color the color of the bridge
     * @param distance the distance between the strands connected by the bridge
     * @param firstStrand the index of the first strand connected by the bridge
     */
    public void addBridge(String color, int distance, int firstStrand){
        if(1 <= firstStrand && firstStrand <= numberStrands && distance <= largeStrand && !(bridgesColors.contains(color)) && numberStrands>1 && !color.equals("green")){
            int strand1 = firstStrand-1;
            int strand0 = strand1 == 0? numberStrands-1:strand1-1;
            int strand2 = firstStrand == numberStrands?0:firstStrand;
            if(!strandsBridgesMap.get(strand0).containsKey(distance)&& !strandsBridgesMap.get(strand2).containsKey(distance)){
                Bridge newBridge = new Bridge(strands.get(strand1).getTetha1(), strands.get(strand2).getTetha1(), xPosition, yPosition, distance, color, strand1, strand2);
                if(isVisible){newBridge.makeVisible();}
                strandsBridgesMap.get(strand1).put(distance, newBridge);
                strandsBridgesMap.get(strand2).put(distance, newBridge);
                bridgesColors.add(color);
                bridgesNumberStrand.add(strand1);
                bridges.add(newBridge);
                ok = true;
            }
            else{ok = false;}
        }
        else {
            ok = false;
        }
    }
    
    /**
     * Removes a bridge from the spider web.
     * 
     * @param color the color of the bridge to be removed
     */
    public void delBridge(String color) {
        if (bridgesColors.contains(color)){
            int index = bridgesColors.indexOf(color);
            int numberFirstStrand = (int) (bridgesNumberStrand.get(index));
            int keyBridge = findKeyBridge(numberFirstStrand, color);
            if (keyBridge != -500){
                bridgesColors.remove(index);
                bridgesNumberStrand.remove(index);
                bridges.remove(index);
                strandsBridgesMap.get(numberFirstStrand).get(keyBridge).makeInvisible();
                strandsBridgesMap.get(numberFirstStrand).remove(keyBridge);
                ok = true;
            }
            else{ok = false;}
        }
        else{ok = false;}
    }
    
    private int findKeyBridge(int numberFirstStrand, String color){
        int bridgeRadius = -500;
        for(int radius : strandsBridgesMap.get(numberFirstStrand).keySet().toArray(new Integer[0])){
            if(strandsBridgesMap.get(numberFirstStrand).get(radius).getColor().equals(color)){
                bridgeRadius = radius;
                break;
            }
        }
        return bridgeRadius;
    }
    
    /**
     * Relocates a bridge to a new distance between strands.
     * 
     * @param color the color of the bridge to be relocated
     * @param distance the new distance between strands
     */
    public void relocateBridge(String color, int distance){
        if (bridgesColors.contains(color) && distance <= largeStrand && distance > 0){
            int index = bridgesColors.indexOf(color);
            int numberFirstStrand = bridgesNumberStrand.get(index);
            delBridge(color);
            addBridge(color, distance, numberFirstStrand+1);
            ok = true;
        }
        else{ok = false;}
    }
    
    /**
     * Returns information about a bridge of the specified color.
     * 
     * @param color the color of the bridge
     * @return an array containing the index of the first strand and the distance of the bridge
     */
    public int[] bridge(String color){
        ArrayList<Integer> bridgeAnswer = new ArrayList<>();
        if (bridgesColors.contains(color)){
            int index = bridgesColors.indexOf(color);
            int numberFirstStrand = bridgesNumberStrand.get(index);
            int numberStrand = -500;
            for (Integer radio : strandsBridgesMap.get(numberFirstStrand).keySet().toArray(new Integer[0])){
                if (strandsBridgesMap.get(numberFirstStrand).get(radio).getColor().equals(color)){
                    numberStrand = strandsBridgesMap.get(numberFirstStrand).get(radio).getStrand1();
                    bridgeAnswer.add(numberStrand+1);
                    bridgeAnswer.add(radio);
                    ok = true;
                    break;
                }
            }
        }
        else{ok = false;}
        return bridgeAnswer.stream().mapToInt(Integer::intValue).toArray();
    }
    
    /**
     * Returns an array of colors representing the bridges in the spider web.
     * 
     * @return an array of bridge colors
     */
    public String[] bridges(){
        ok = true;
        return bridgesColors.toArray(new String[0]);
    }
    
    /**
     * Makes the spider web visible.
     */
    public void makeVisible(){
        if (!isVisible){
            for(Strand strand : strands){
                strand.makeVisible();
            }
            for(String color : spotsMap.keySet().toArray(new String[0])){
                spotsMap.get(color).makeVisible();
            }
            for(Integer strandNumber : strandsBridgesMap.keySet().toArray(new Integer[0])){
                for(Integer radio : strandsBridgesMap.get(strandNumber).keySet().toArray(new Integer[0])){
                    strandsBridgesMap.get(strandNumber).get(radio).makeVisible();
                }
            }
            for(int i = 0; i < bridges.size() ; i++){
                Bridge bridge = bridges.get(i);
                bridge.makeVisible();
            }
            spider.makeVisible();
            isVisible = true; 
        }
        ok = true;
    }
    
    /**
     * Makes the spider web invisible.
     */
    public void makeInvisible(){
        if (isVisible){
            for(Strand strand : strands){
                strand.makeInvisible();
            }
            for(String color : spotsMap.keySet().toArray(new String[0])){
                spotsMap.get(color).makeInvisible();
            }
            for(int i = 0; i < bridges.size() ; i++){
                Bridge bridge = bridges.get(i);
                bridge.makeInvisible();
            }
            makeInvisibleLastPath();
            spider.makeInvisible();
            isVisible = false;
        }
        ok = true;
    }
    
    /**
     * Positions the spider on a specific strand.
     * 
     * @param strand the index of the target strand
     */
    public void spiderSit(int strand){
        int numbStrand = strand - 1;
        if(0<= numbStrand && numbStrand <= numberStrands-1){
            spider.rotate(strands.get(numbStrand).getTetha1());
            spider.setNumberStrand(numbStrand);
            ok = true;
        }
        else{ok = false;}
    }
    
    /**
     * Adds a spot to the spider web on a specified strand.
     * 
     * @param color the color of the spot
     * @param strand the index of the target strand
     */
    public void addSpot(String color, int strand){
        if (!spotsMap.containsKey(color) && 1 <= strand && strand <= numberStrands  && !strands.get(strand-1).hasSpot()){
            int spotRadius = Spot.size/2;
            double angle = strands.get(strand-1).getTetha1();
            Spot spot = new Spot(xPosition - spotRadius + (int) (largeStrand*Math.cos(angle)), yPosition - spotRadius - (int) (largeStrand*Math.sin(angle)),color, strand-1);
            spotsMap.put(color, spot);
            strands.get(strand-1).setHasSpot(true);
            if (isVisible){spot.makeVisible();}
            ok = true;
        }
        else{ok = false;}
    }
    
    /**
     * Removes a spot from the spider web.
     * 
     * @param color the color of the spot to be removed
     */
    public void delSpot(String color) {
        if (spotsMap.containsKey(color)) {
            spotsMap.get(color).makeInvisible();
            spotsMap.remove(color);
            for (Strand strand : strands){
                if(strand.getColor().equals(color)){
                    strand.setHasSpot(false);
                    break;
                }
            }
            ok = true;
        }
        else {ok = false;}
    }
    
    /**
     * Returns the index of the strand where a spot of the specified color is located.
     * 
     * @param color the color of the spot
     * @return the index of the strand containing the spot, or -500 if the spot is not found
     */
    public int spot(String color){
        int numberStrand = -500;
        if(spotsMap.containsKey(color)){
            ok = true;
            numberStrand = (spotsMap.get(color).getNumberStrand())+1;
        }
        else{ok = false;}
        return numberStrand;
    }
    
    /**
     * Returns an array of colors representing the spots in the spider web.
     * 
     * @return an array of spot colors
     */
    public String[] spots(){
        ok = true;
        return spotsMap.keySet().toArray(new String[0]);
    }
    
    private void spiderWalksForward(int startStrand){
        boolean canKeepAdvancing = true;
        int actualStrand = startStrand;
        while(canKeepAdvancing){
            List<Integer> bridgeKeys = new ArrayList<>(strandsBridgesMap.get(actualStrand).keySet());
            Collections.sort(bridgeKeys);
            if(bridgeKeys.size() > 0){
                for(Integer bridgeKey : bridgeKeys){
                    double newAngle = spider.getVisionAngle()!=strandsBridgesMap.get(actualStrand).get(bridgeKey).getTetha1()?strandsBridgesMap.get(actualStrand).get(bridgeKey).getTetha1():strandsBridgesMap.get(actualStrand).get(bridgeKey).getTetha2();
                    String color = strandsBridgesMap.get(actualStrand).get(bridgeKey).getColor();
                    if(bridgeKey > spider.getRadiusFromCenter()){
                        int newStrand = actualStrand == strandsBridgesMap.get(actualStrand).get(bridgeKey).getStrand1()?strandsBridgesMap.get(actualStrand).get(bridgeKey).getStrand2():strandsBridgesMap.get(actualStrand).get(bridgeKey).getStrand1();
                        spiderMoveAndCross(bridgeKey, newAngle, newStrand, color);
                        actualStrand = newStrand;
                        break;
                    }
                    if(bridgeKeys.indexOf(bridgeKey)+1>=bridgeKeys.size()){
                        canKeepAdvancing = false;
                    }
                }
            }
            else{canKeepAdvancing = false;}
        }
        spiderMoveToFinalPosition(true);
    }
    
    private void spiderMoveAndCross(int radius, double newAngle, int newStrand, String bridgeColor){ 
        spider.walkToTheBridge(radius);
        //Scanner scanner = new Scanner(System.in);
        //scanner.nextLine();
        spiderTrackerRadius.add(radius);
        spiderTrackerStrands.add(spider.getNumberStrand());
        spider.crossTheBridge(newAngle, newStrand);
        spiderTrackerRadius.add(radius);
        spiderTrackerStrands.add(newStrand);
        if(!usedBridges.contains(bridgeColor)){
            usedBridges.add(bridgeColor);
        }
    }
    
    private void spiderMoveToFinalPosition(boolean forward){
        int newStrand = spider.getNumberStrand();
        int radius = forward?largeStrand:0;
        spider.walkToTheBridge(radius);
        spiderTrackerRadius.add(radius);
        spiderTrackerStrands.add(newStrand);
        spider.setIsCentered(!forward);
        spider.setNumberStrand(newStrand);
        if(strands.get(newStrand).hasSpot()){
            for (String spotColor : spotsMap.keySet().toArray(new String[0])){
                if(spotsMap.get(spotColor).getNumberStrand()==newStrand && !reachableSpots.contains(spotColor)){
                    reachableSpots.add(spotColor);
                    break;
                }
            }
        }
    }
    
    private void spiderWalksBackward(){
        boolean canKeepAdvancing = true;
        int actualStrand = spider.getNumberStrand();
        spiderSit(actualStrand+1);
        while(canKeepAdvancing){
            List<Integer> bridgeKeys = new ArrayList<>(strandsBridgesMap.get(actualStrand).keySet());
            Collections.sort(bridgeKeys, Collections.reverseOrder());
            if(bridgeKeys.size() > 0){
                for(Integer bridgeKey : bridgeKeys){
                    double newAngle = spider.getVisionAngle()!=strandsBridgesMap.get(actualStrand).get(bridgeKey).getTetha1()?strandsBridgesMap.get(actualStrand).get(bridgeKey).getTetha1():strandsBridgesMap.get(actualStrand).get(bridgeKey).getTetha2();
                    String color = strandsBridgesMap.get(actualStrand).get(bridgeKey).getColor();
                    if(bridgeKey < spider.getRadiusFromCenter()){
                        int newStrand = actualStrand == strandsBridgesMap.get(actualStrand).get(bridgeKey).getStrand1()?strandsBridgesMap.get(actualStrand).get(bridgeKey).getStrand2():strandsBridgesMap.get(actualStrand).get(bridgeKey).getStrand1();
                        spiderMoveAndCross(bridgeKey, newAngle, newStrand, color);
                        actualStrand = newStrand;
                        break;
                    }
                    if(bridgeKeys.indexOf(bridgeKey)+1>=bridgeKeys.size()){
                        canKeepAdvancing = false;
                    }
                }
            }
            else{canKeepAdvancing = false;}
        }
        spiderMoveToFinalPosition(false);
    }
    
    /**
     * Moves the spider forward or backward along the strands.
     * 
     * @param advance true to move the spider forward, false to move it backward
     */
    public void spiderWalk(boolean advance){
        makeInvisibleLastPath();
        if (advance){
            if(spider.isCentered()){
                int startStrand = spider.getNumberStrand();
                spiderWalksForward(startStrand);
                ok = true;
            }
            else{ok = false;}
        }
        else{
            if(!spider.isCentered()){
                spiderWalksBackward();
                ok = true;
            }
            else{ok = false;}
        }
    }
    
    private void makeInvisibleLastPath(){
        for(int i = 0; i < spiderLastPathStrand.size(); i++){
            spiderLastPathStrand.get(i).makeInvisible();
        }
        for(int j = 0; j < spiderLastPathBridge.size(); j++){
            spiderLastPathBridge.get(j).makeInvisible();
        }
    }
    
    private void paintLastPath(ArrayList<Integer> coordinates){
        if(coordinates.get(0)==coordinates.get(2)){
            int xPos = (int)(Math.cos(strands.get(coordinates.get(0)).getTetha1()) * coordinates.get(1)) + xPosition;
            int yPos = (int)(-Math.sin(strands.get(coordinates.get(0)).getTetha1()) * coordinates.get(1)) + yPosition;
            int radius = Math.abs(coordinates.get(3)-coordinates.get(1));
            double angle = coordinates.get(1)>coordinates.get(3)?strands.get(coordinates.get(0)).getTetha1()+Math.toRadians(180):strands.get(coordinates.get(0)).getTetha1();
            angle = Math.toDegrees(angle);
            Strand strand = new Strand(xPos, yPos, radius, angle, "green");
            if(isVisible){strand.makeVisible();}
            spiderLastPathStrand.add(strand);
            
        }
        else{
            Bridge bridge = new Bridge(strands.get(coordinates.get(0)).getTetha1(), strands.get(coordinates.get(2)).getTetha1(), xPosition, yPosition, coordinates.get(1), "blueGray", coordinates.get(0), coordinates.get(2));
            if(isVisible){bridge.makeVisible();}
            spiderLastPathBridge.add(bridge);
        }
    }
    
    /**
     * Returns information about the last path taken by the spider.
     * 
     * @return an array containing the index of the last two strands and the corresponding radii
     */
    public int[] spiderLastPath() {
        ArrayList<Integer> spiderLastPath = new ArrayList<>();
        if (spiderTrackerStrands.size() >= 2 && spiderTrackerRadius.size() >= 2) {
            int size = spiderTrackerStrands.size();
            spiderLastPath.add(spiderTrackerStrands.get(size - 2));
            spiderLastPath.add(spiderTrackerRadius.get(size - 2));
            spiderLastPath.add(spiderTrackerStrands.get(size - 1));
            spiderLastPath.add(spiderTrackerRadius.get(size - 1));
            paintLastPath(spiderLastPath);
            spider.makeInvisible();
            spider.makeVisible();
            ok = true;
        }
        else{ok = false;}
        return spiderLastPath.stream().mapToInt(Integer::intValue).toArray();
    }
    
    /**
     * Finishes operations on the spider web.
     */
    public void finish(){
        makeInvisible();
        isVisible = true;
    }
    
    /**
     * Checks if the last operation on the spider web was successful.
     * 
     * @return true if the last operation was successful, false otherwise
     */
    public boolean ok(){
        return ok;
    }
    
    // Helper method for printing information about bridges
    private void printBridgesInfo() {
        for (Map.Entry<Integer, Map<Integer, Bridge>> entry : strandsBridgesMap.entrySet()) {
            int strandNumber = entry.getKey();
            Map<Integer, Bridge> bridgeMap = entry.getValue();
            System.out.println("Strand Number: " + (strandNumber + 1));
            for (Map.Entry<Integer, Bridge> bridgeEntry : bridgeMap.entrySet()) {
                int radius = bridgeEntry.getKey();
                Bridge bridge = bridgeEntry.getValue();
                System.out.println("  Radius: " + radius);
                System.out.println("    Color: " + bridge.getColor());
                System.out.println("    Strand 1: " + (bridge.getStrand1() + 1));
                System.out.println("    Strand 2: " + (bridge.getStrand2() + 1));
                System.out.println("    Theta 1: " + bridge.getTetha1());
                System.out.println("    Theta 2: " + bridge.getTetha2());
            }
        }
    }
    
    // Helper method for loading a predefined spider web
    public void cargarTelaraña(){
        addBridge("blue",90,1);
        addBridge("red",40,3);
        addBridge("blueGray",60,3);
        addBridge("darkGreen",80,7);
        addBridge("cyan",100,5);
        addSpot("blue", 1);
        addSpot("red", 3);
        addSpot("green", 5);
        addSpot("cyan", 7);
        addBridge("magenta", 30, 4);
        addBridge("darkGray", 60, 7);
        addBridge("pink", 110, 7);
        addBridge("brown", 30, 7);
        addBridge("black", 70, 5);
        addBridge("gray", 50, 4);
        addBridge("burgundy", 40, 1);
        
        addBridge("orange", 30, 2);
        addBridge("lightGray", 40, 6);
        addBridge("purple", 70, 2);
        addBridge("golden", 100, 2);
        printBridgesInfo();
        System.out.println(bridgesColors);
        System.out.println(bridgesNumberStrand);
        System.out.println(bridges);
    }
    
    public void enlarge(double percentage){
        if(percentage>0){
            largeStrand = (int) (largeStrand*percentage);
            for(Strand r: strands){
                r.enlarge(percentage);
            }
            reOrganizeSpots();
            ok = true;
        }
        else{ok = false;}
    }
    
    public void addStrand(){
        numberStrands += 1;
        reOrganizeStrands(); 
        if(strandsBridgesMap.size() >= 1){
            reOrganizeBridges();
        }
        if(spotsMap.size() >= 1){
            reOrganizeSpots();
        }
        ok = true;
    }
    
    // Helper method for reorganizing strands after adding a new one
    private void reOrganizeStrands(){
        double newangle = 360/numberStrands;
        Strand strand = new Strand(xPosition, yPosition, largeStrand, 0, "black");
        this.strands.add(strand);
        int i = 0;
        strandsBridgesMap.put(this.strands.indexOf(strand), new HashMap<>());
        for(Strand s: strands){  
            s.setTetha1(i); 
            i += newangle;
            if(isVisible){
                s.makeVisible();
            }
        }
    }
    
    // Helper method for reorganizing bridges after adding a new strand
    private void reOrganizeBridges(){
        for (int a = 0; a < bridges.size(); a++){
            Bridge bridge = bridges.get(a);
            bridge.setTetha1(strands.get(bridge.getStrand1()).getTetha1());
            if(bridge.getStrand2()==0){
                bridge.setStrand2(numberStrands-1);
            }
            bridge.setTetha2(strands.get(bridge.getStrand2()).getTetha1());
            if(isVisible){
            bridge.makeVisible();
            }
        }
    }
    
    // Helper method for reorganizing spots after adding a new strand
    private void reOrganizeSpots(){
        ArrayList<Spot> llaves = new ArrayList<>(spotsMap.values());
        for (Spot valor : llaves) {
            int spotRadius = valor.size/2;
            int strand = valor.getNumberStrand();
            double angle = strands.get(strand).getTetha1();
            valor.setPosition(xPosition - spotRadius + (int) (largeStrand*Math.cos(angle)), yPosition - spotRadius - (int) (largeStrand*Math.sin(angle)));
        }
    }
    
    /**
     * Returns the radius of the strands in the spider web.
     * 
     * @return the radius of the strands
     */
    public int getLargeStrand(){
        return largeStrand;
    }
    
    /**
     * Returns the number of strands in the spider web.
     * 
     * @return the number of strands
     */
    public int getNumberStrands(){
        return numberStrands;
    }
    
    /**
     * Returns an array of colors representing the reachable spots in the spider web.
     * 
     * @return an array of reachable spot colors
     */
    public String[] reachableSpots(){
        reachableSpots = new ArrayList<>();
        for(int i = 0; i < strands.size(); i++){
            spiderSit(i+1);
            spiderWalksForward(i);
            spiderWalksBackward();
        }
        ok = true;
        return reachableSpots.toArray(new String[0]);
    }
    
    /**
     * Returns an array of colors representing the unused bridges in the spider web.
     * 
     * @return an array of unused bridge colors
     */
    public String[] unUsedBridges(){
        ArrayList<String> unUsedBridges = new ArrayList<>();
        for(int i = 0; i < bridges.size() ; i++){
                Bridge bridge = bridges.get(i);
                if(!usedBridges.contains(bridge.getColor())){
                    unUsedBridges.add(bridge.getColor());
                }
        }
        ok = true;
        return unUsedBridges.toArray(new String[0]);
    }
}