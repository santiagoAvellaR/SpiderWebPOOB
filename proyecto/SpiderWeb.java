import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Collections;
import java.util.Scanner;

/**
 * Write a description of class SpiderWeb here.
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
    private ArrayList<ArrayList<Object>> bridgesUsedColors = new ArrayList<>(); // {string (color), integer (numberStrand), Bridge}
    private Map<Integer, Map<Integer, Bridge>> strandsBridgesMap= new HashMap<>();  // numberStrand -> numeroHilo -> Bridge
    private ArrayList<String> usedBridges = new ArrayList<>();
    // spots
    private Map<String, Spot> spotsMap = new HashMap<>();
    private ArrayList<String> reachableSpots = new ArrayList<>();
    // spider
    private Spider spider;
    ArrayList<ArrayList<Integer>> spiderTracker = new ArrayList<>();

    /**
     * Constructor for objects of class SpiderWeb
     */
    public SpiderWeb(int strands, int radio) {
        numberStrands = strands;
        largeStrand = radio;
        xPosition = 500;
        yPosition = 350;
        isVisible = false;
        spider = new Spider(xPosition-8, yPosition-8);
        double angle = (double) 360 / (double) numberStrands;
        for (double i = 0; i < 360; i += angle) {
            Strand strand = new Strand(xPosition, yPosition, largeStrand, i);
            this.strands.add(strand);
            strandsBridgesMap.put(this.strands.indexOf(strand), new HashMap<>());
        }
        bridgesUsedColors.add(new ArrayList<>());
        bridgesUsedColors.add(new ArrayList<>());
        bridgesUsedColors.add(new ArrayList<>());
        ok = true;
    }
    
    public void addBridge(String color, int distance, int firstStrand){
        if(1 <= firstStrand && firstStrand <= numberStrands && distance <= largeStrand && !(bridgesUsedColors.get(0).contains(color)) && numberStrands>1){
            int strand1 = firstStrand-1;
            int strand2 = firstStrand == numberStrands?0:firstStrand;
            Bridge newBridge = new Bridge(strands.get(strand1).getTetha1(), strands.get(strand2).getTetha1(), xPosition, yPosition, distance, color, strand1, strand2);
            if(isVisible){newBridge.makeVisible();}
            strandsBridgesMap.get(strand1).put(distance, newBridge);
            strandsBridgesMap.get(strand2).put(distance, newBridge);
            bridgesUsedColors.get(0).add(color);
            bridgesUsedColors.get(1).add(strand1);
            bridgesUsedColors.get(2).add(newBridge);
            ok = true;
        }
        else {
            ok = false;
        }
    }
    
    public void delBridge(String color) {
        if (bridgesUsedColors.get(0).contains(color)){
            int index = bridgesUsedColors.get(0).indexOf(color);
            int numberFirstStrand = (int) (bridgesUsedColors.get(1).get(index));
            int keyBridge = findKeyBridge(numberFirstStrand, color);
            if (keyBridge != -500){
                bridgesUsedColors.get(0).remove(index);
                bridgesUsedColors.get(1).remove(index);
                bridgesUsedColors.get(2).remove(index);
                strandsBridgesMap.get(numberFirstStrand).get(keyBridge).makeInvisible();
                strandsBridgesMap.get(numberFirstStrand).remove(keyBridge);
                ok = true;
            }
            else{ok = false;}
        }
        else{ok = false;}
    }
    
    private int findKeyBridge(int numberFirstStrand,String color){
        int bridgeRadius = -500;
        for(int radius : strandsBridgesMap.get(numberFirstStrand).keySet().toArray(new Integer[0])){
            if(strandsBridgesMap.get(numberFirstStrand).get(radius).getColor().equals(color)){
                bridgeRadius = radius;
                break;
            }
        }
        return bridgeRadius;
    }

    public void relocateBridge(String color, int distance){
        if (bridgesUsedColors.get(0).contains(color) && distance <= largeStrand && distance > 0){
            int index = bridgesUsedColors.get(0).indexOf(color);
            int numberFirstStrand = (int) bridgesUsedColors.get(1).get(index);
            delBridge(color);
            addBridge(color, distance, numberFirstStrand+1);
        }
        else{ok = false;}
    }
    
    public int bridge(String color){
        if (bridgesUsedColors.get(0).contains(color)){
            int index = bridgesUsedColors.get(0).indexOf(color);
            int numberFirstStrand = (int) bridgesUsedColors.get(1).get(index);
            int numberStrand = -500;
            for (Integer radio : strandsBridgesMap.get(numberFirstStrand).keySet().toArray(new Integer[0])){
                if (strandsBridgesMap.get(numberFirstStrand).get(radio).getColor().equals(color)){
                    numberStrand = strandsBridgesMap.get(numberFirstStrand).get(radio).getStrand1();
                    break;
                }
            }
            if (numberStrand != -500){ 
                ok = true;
            }
            else{ok = false;}
            return numberStrand+1;
        }
        else{
            ok = false;
            return -500;
        }
    }
    
    public String[] bridges(){
        ok = true;
        return bridgesUsedColors.get(0).toArray(new String[0]);
    }
    
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
            for(int i = 0; i < bridgesUsedColors.get(2).size() ; i++){
                Bridge bridge = (Bridge) bridgesUsedColors.get(2).get(i);
                bridge.makeVisible();
            }
            spider.makeVisible();
            isVisible = true; 
        }
        ok = true;
    }
    
    public void makeInvisible(){
        if (isVisible){
            for(Strand strand : strands){
                strand.makeInvisible();
            }
            for(String color : spotsMap.keySet().toArray(new String[0])){
                spotsMap.get(color).makeInvisible();
            }
            for(int i = 0; i < bridgesUsedColors.get(2).size() ; i++){
                Bridge bridge = (Bridge) bridgesUsedColors.get(2).get(i);
                bridge.makeInvisible();
            }
            spider.makeInvisible();
            isVisible = false;
        }
        ok = true;
    }
    
    public void spiderSit(int strand){
        if(0<= strand && strand <= numberStrands-1){
            spider.rotate(strands.get(strand).getTetha1());
            ok = true;
        }
        else{ok = false;}
    }
    
    public void addSpot(String color, int strand){
        if (!spotsMap.containsKey(color) && 1 <= strand && strand <= numberStrands){
            int spotRadius = Spot.size/2;
            double angle = strands.get(strand-1).getTetha1();
            Spot spot = new Spot(xPosition - spotRadius + (int) (largeStrand*Math.cos(angle)), yPosition - spotRadius - (int) (largeStrand*Math.sin(angle)),color, strand-1);
            spotsMap.put(color, spot);
            if (isVisible){spot.makeVisible();}
            ok = true;
        }
        else{ok = false;}
    }
    
    public void delSpot(String color) {
        if (spotsMap.containsKey(color)) {
            spotsMap.get(color).makeInvisible();
            spotsMap.remove(color);
            ok = true;
        }
        else {ok = false;}
    }
    
    public int spot(String color){
        ok = true;
        return (int) (spotsMap.get(color).getNumberStrand())+1;
    }
    
    public String[] spots(){
        ok = true;
        return spotsMap.keySet().toArray(new String[0]);
    }
    
    private Spot verifyIfSpiderIsInASpot(int xCoordinate, int yCoordinate){
        Spot spot = null;
        for(String color : spotsMap.keySet().toArray(new String[0])){
            if(spotsMap.get(color).getXPosition()==xCoordinate && spotsMap.get(color).getYPosition()==yCoordinate){
                spot = spotsMap.get(color);
                break;
            }
        }
        return spot;
    }
    
    private void spiderWalksForward(int startStrand){
        boolean canKeepAdvancing = true;
        int actualStrand = startStrand;
        spider.setNumberStrand(startStrand);
        spiderSit(startStrand);
        while(canKeepAdvancing){
            System.out.println("esta en el hilo: " + actualStrand);
            List<Integer> bridgeKeys = new ArrayList<>(strandsBridgesMap.get(actualStrand).keySet());
            Collections.sort(bridgeKeys);
            for(Integer bridgeKey : bridgeKeys){
                if(bridgeKey > spider.getRadiusFromCenter()){
                    spider.walkToTheBridge(bridgeKey);
                    int newStrand = actualStrand == strandsBridgesMap.get(actualStrand).get(bridgeKey).getStrand1()?strandsBridgesMap.get(actualStrand).get(bridgeKey).getStrand2():strandsBridgesMap.get(actualStrand).get(bridgeKey).getStrand1();
                    double newAngle = spider.getVisionAngle()!=strandsBridgesMap.get(actualStrand).get(bridgeKey).getTetha1()?strandsBridgesMap.get(actualStrand).get(bridgeKey).getTetha1():strandsBridgesMap.get(actualStrand).get(bridgeKey).getTetha2();
                    if(!usedBridges.contains(strandsBridgesMap.get(actualStrand).get(bridgeKey).getColor())){usedBridges.add(strandsBridgesMap.get(actualStrand).get(bridgeKey).getColor());}
                    spider.crossTheBridge(newAngle, actualStrand);
                    actualStrand = newStrand;
                    break;
                }
                if(bridgeKeys.indexOf(bridgeKey)+1>=bridgeKeys.size()){
                    spider.walkToTheBridge(largeStrand);
                    spider.setIsCentered(false);
                    spider.setNumberStrand(actualStrand);
                    Spot spot = verifyIfSpiderIsInASpot(-8 + SpiderWeb.xPosition + (int) (largeStrand*Math.cos(spider.getVisionAngle())), -8 + SpiderWeb.yPosition - (int) (largeStrand*Math.sin(spider.getVisionAngle())));
                    if (spot != null && !reachableSpots.contains(spot.getColor())){reachableSpots.add(spot.getColor());}
                    canKeepAdvancing = false;
                    System.out.println("no encontro mas hilos: " + spider.getNumberStrand());
                }
            }
        }
    }
    
    private void moveSpider(boolean moveAndCross){
        
    }
    
    private void spiderWalksBackward(){
        boolean canKeepAdvancing = true;
        int actualStrand = spider.getNumberStrand();
        spiderSit(actualStrand);
        while(canKeepAdvancing){
            List<Integer> bridgeKeys = new ArrayList<>(strandsBridgesMap.get(actualStrand).keySet());
            Collections.sort(bridgeKeys, Collections.reverseOrder());
            for(Integer bridgeKey : bridgeKeys){
                if(bridgeKey < spider.getRadiusFromCenter()){
                    spider.walkToTheBridge(bridgeKey);
                    int newStrand = actualStrand == strandsBridgesMap.get(actualStrand).get(bridgeKey).getStrand1()?strandsBridgesMap.get(actualStrand).get(bridgeKey).getStrand2():strandsBridgesMap.get(actualStrand).get(bridgeKey).getStrand1();
                    double newAngle = spider.getVisionAngle()!=strandsBridgesMap.get(actualStrand).get(bridgeKey).getTetha1()?strandsBridgesMap.get(actualStrand).get(bridgeKey).getTetha1():strandsBridgesMap.get(actualStrand).get(bridgeKey).getTetha2();
                    spider.crossTheBridge(newAngle, actualStrand);
                    Scanner scan = new Scanner(System.in);
                    scan.nextLine();
                    actualStrand = newStrand;
                    break;
                }
                if(bridgeKeys.indexOf(bridgeKey)+1>=bridgeKeys.size()){
                    spider.walkToTheBridge(0);
                    spider.setIsCentered(true);
                    spider.setNumberStrand(actualStrand);
                    canKeepAdvancing = false;
                }
            }
        }
    }
    
    private int randomNumber(int number){
         Random random = new Random();
         int randomNumber = random.nextInt(number);
         return randomNumber;
    }
    
    public void spiderWalk(boolean advance){
        if (advance){
            List<String> colorsSpotsArray = new ArrayList<>(spotsMap.keySet());
            if(colorsSpotsArray.size() > 0){
                int selectedSpot = randomNumber(colorsSpotsArray.size());
                System.out.println((selectedSpot));
                int startStrand = spotsMap.get(colorsSpotsArray.get(selectedSpot)).getNumberStrand();
                System.out.println(startStrand + (colorsSpotsArray.get(selectedSpot)));
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
            else{
                ok = false;
            }
        }
    }
    
    public void spiderLastPath(){
    }
    
    public void finish(){
    }
    
    public boolean ok(){
        return ok;
    }
    
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
    
    public void cargarTelaraña(){
        addBridge("blue",20,1);
        addBridge("red",40,3);
        addBridge("green",60,3);
        addBridge("darkGreen",80,7);
        addBridge("cyan",100,5);
        addSpot("blue", 1);
        addSpot("red", 3);
        addSpot("green", 5);
        addSpot("cyan", 7);
        addBridge("magenta", 30, 4);
        addBridge("darkGray", 60, 7);
        addBridge("pink", 10, 7);
        addBridge("brown", 30, 7);
        addBridge("orange", 40, 2);
        addBridge("black", 70, 5);
        addBridge("gray", 50, 4);
        addBridge("lightGray", 20, 1);
        addBridge("purple", 70, 6);
        addBridge("golden", 80, 6);
        addBridge("burgundy", 40, 1);
        
        printBridgesInfo();
        System.out.println(bridgesUsedColors);
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
    }

    private void reOrganizeStrands(){
        double newangle = 360/numberStrands;
        Strand strand = new Strand(xPosition, yPosition, largeStrand, 0);
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
    
    private void reOrganizeBridges(){
        for (int a = 0; a < bridgesUsedColors.get(2).size(); a++){
            Bridge bridge = (Bridge) bridgesUsedColors.get(2).get(a);
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
    
    private void reOrganizeSpots(){
        ArrayList<Spot> llaves = new ArrayList<>(spotsMap.values());
        for (Spot valor : llaves) {
            int spotRadius = valor.size/2;
            int strand = valor.getNumberStrand();
            double angle = strands.get(strand).getTetha1();
            valor.setPosition(xPosition - spotRadius + (int) (largeStrand*Math.cos(angle)), yPosition - spotRadius - (int) (largeStrand*Math.sin(angle)));
        }
    }
    
    public int getLargeStrand(){
        return largeStrand;
    }
    
    public int getNumberStrands(){
        return numberStrands;
    }
    
    public String[] reachableSpots(){
        reachableSpots = new ArrayList<>();
        for(int i = 0; i < strands.size(); i++){
            spiderWalksForward(i);
        }
        return reachableSpots.toArray(new String[0]);
    }
    
    public String[] unUsedBridges(){
        ArrayList<String> unUsedBridges = new ArrayList<>();
        for(int i = 0; i < bridgesUsedColors.get(2).size() ; i++){
                Bridge bridge = (Bridge) bridgesUsedColors.get(2).get(i);
                if(!usedBridges.contains(bridge.getColor())){
                    unUsedBridges.add(bridge.getColor());
                }
        }
        return unUsedBridges.toArray(new String[0]);
    }
}
