package spiderweb;
import shapes.*;
import java.util.*;

/**
 * The SpiderwebContest class represents a contest involving spider webs.
 * It allows simulating and solving spider web-related problems.
 * 
 * This class manages multiple SpiderWeb instances.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class SpiderwebContest{
    // instance variables - replace the example below with your own
    private SpiderWeb actualSpiderWeb;
    private ArrayList<SpiderWeb> spiderWebs;
    private String[] colors = {"red", "black", "blue", "yellow", "green", "magenta", "white", "cyan", 
                   "darkGray", "gray", "lightGray", "orange", "pink", "darkGreen", 
                   "brown", "darkBrown", "purple", "golden", "burgundy", "blueGray"};
    /**
     * Constructs a SpiderwebContest object.
     */
    public SpiderwebContest(){
        spiderWebs = new ArrayList<>();
    }
    
    /**
     * Updates the minimum number of bridges needed for each strand based on the given bridges and a specific strand.
     * 
     * @param bridges the array containing information about bridges
     * @param strand the index of the strand to update
     */
    private void updateMinimunNumberOfBridges(int[] strands, int strand, int size){
        int copy = strands[(strand+1)%size];
        strands[(strand+1)%size] = strands[strand];
        strands[strand] = copy;
        if(strands[strand] < strands[(strand+1)%size]){
            for(int i = 0; i < size; i++){
                int pos2 = strand>=i?strand-i:size-(i-strand);
                int pos = pos2==0?size-1:pos2-1;
                strands[pos2]=Math.min(strands[(pos2+1)%size]+1,Math.min(strands[pos]+1, strands[pos2]));
            }
        }
        else if(strands[strand] > strands[(strand+1)%size]){
            for(int i = 0; i < size; i++){
                int pos2 = (strand+i)%size;
                int pos = pos2==0?size-1:pos2-1;
                strands[pos2]=Math.min(strands[(pos2+1)%size]+1,Math.min(strands[pos]+1, strands[pos2]));
            }   
        }
    }
    
    /**
     * Solves the spider web problem for a given number of strands, a favorite strand, and bridge configurations.
     * 
     * @param strands the total number of strands in the spider web
     * @param favorite the index of the spider's favorite strand
     * @param bridges the 2D array containing information about bridges
     * @return an array representing the minimum number of bridges needed for each strand
     */
    public int[] solve(int strands, int favorite, int[][] bridges){     
        Comparator<int[]> comparator = (a,b)->b[0]-a[0];
        Arrays.sort(bridges,comparator);
        int[] result = new int[strands];
        int pos = favorite -1;
        for(int i= -1; i < strands-1;i++){
            result[pos]=Math.min(i+1,strands-i-1);
            pos = (pos+1)%strands;
        }
        for(int[] bridge : bridges){
           updateMinimunNumberOfBridges(result,bridge[1]-1,strands);
        }
        return result;
    }
    
    /**
     * Simulates a spider web contest using the provided parameters.
     * 
     * @param strands the total number of strands in the spider web
     * @param favorite the index of the spider's favorite strand
     * @param bridges the 2D array containing information about bridges
     */
    public void simulate(int strands, int favorite, int[][] bridges){
        int[] minimunBridges = solve(strands, favorite, bridges);
        if(spiderWebs.size()>=1){spiderWebs.get(spiderWebs.size() - 1).makeInvisible();}
        SpiderWeb spiderWeb = new SpiderWeb(strands, favorite, bridges);
        actualSpiderWeb = spiderWeb;
        spiderWebs.add(spiderWeb);
        spiderWeb.addSpot("blue", favorite);
        spiderWeb.makeVisible();
        int[][] maxTwoBridgesOfEachStrand = getTheTwoMaximunBridgesOfEachStrand(strands, bridges);
        int cont = 0;
        for(int minNumberBridges : minimunBridges){
            if(minNumberBridges > 0){
                int rightSide = cont == minimunBridges.length ? 0 : cont+1;
                int leftSide = cont == 0 ? minimunBridges.length-1 : cont-1;
                int selectedPath;
                if(minimunBridges[rightSide]==minimunBridges[leftSide]){
                    selectedPath = Math.abs(rightSide-favorite)<Math.abs(leftSide-favorite)?rightSide:leftSide;
                    selectedPath = minimunBridges[selectedPath];
                }
                else{selectedPath = Math.min(minimunBridges[leftSide], minimunBridges[rightSide]);}
                int delta;
                int apuntador1 = cont;
                int apuntador2;
                int constructedBridges = 0;
                if(selectedPath == minimunBridges[rightSide]){
                    apuntador1 = cont;
                    apuntador2 = (cont+strands-1)%strands;
                    while(apuntador1 != favorite){
                        if(minimunBridges[apuntador1] - constructedBridges != minimunBridges[apuntador2]){
                            if(minimunBridges[apuntador1] - constructedBridges > minimunBridges[apuntador2]){
                            }
                            else{
                                
                            }
                        }
                        else{
                            int distance = maxTwoBridgesOfEachStrand[cont][0];
                            actualSpiderWeb.addBridge(colors[cont], distance+7, apuntador2+1);
                            constructedBridges +=1;
                            cont+=1;
                        }
                    }
                }
                else{
                    
                }
                cont+=1;
            }
        }
    }
    
    private int[][] getTheTwoMaximunBridgesOfEachStrand(int strands, int[][] bridges) {
        int[][] maxBridges = new int[strands][2]; // Inicializamos el arreglo con el tamaño adecuado
        for (int i = 0; i < strands; i++) {
            maxBridges[i] = new int[]{0, 0}; // Inicializamos cada subarreglo con valores por defecto
        }
        for (int[] info : bridges) {
            int numberStrand = info[1] - 1;
            int radius = info[0];
            if (radius > maxBridges[numberStrand][0]) {
                maxBridges[numberStrand][1] = maxBridges[numberStrand][0]; // Movemos el máximo anterior al segundo máximo
                maxBridges[numberStrand][0] = radius; // Actualizamos el máximo
            } else if (radius > maxBridges[numberStrand][1]) {
                maxBridges[numberStrand][1] = radius; // Actualizamos el segundo máximo
            }
        }
        return maxBridges; // Devolvemos el resultado al final del método
    }
}
