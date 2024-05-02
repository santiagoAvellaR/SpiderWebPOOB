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
    
    // {{2, 1}, {4, 3}, {6, 3}, {8, 7}, {10, 5}}
    /**
     * Simulates a spider web contest using the provided parameters.
     * 
     * @param strands the total number of strands in the spider web
     * @param favorite the index of the spider's favorite strand
     * @param bridges the 2D array containing information about bridges
     */
    public void simulate(int strands, int favourite, int[][] bridges){
        int[] minimunBridges = solve(strands, favourite, bridges);
        actualSpiderWeb = new SpiderWeb(strands, favourite, bridges);
        actualSpiderWeb.makeVisible();
        ArrayList<Bridge> bridgesForSimulation = new ArrayList<>();
        for(int startStrand = 0; startStrand < strands; startStrand++){
            if(minimunBridges[startStrand] != 0){
                int reachableStrand = actualSpiderWeb.spiderWalkSimulate(startStrand);
                boolean arriveFartherThanInitialStrand = (Math.abs(reachableStrand-favourite))>=(Math.abs(startStrand-favourite));
                int missingBridges = (arriveFartherThanInitialStrand)?-1:0;
                // quedo a derecha o izquierda
                if(reachableStrand > favourite){
                    for(int j = reachableStrand; reachableStrand > favourite; j--){
                    }
                    
                }
                else{
                    for(int j = reachableStrand; reachableStrand < favourite; j++){
                    }
                }
                // eliminar todo para la siguiente simulacion del hilo siguiente
                for(Bridge bridge : bridgesForSimulation){
                    actualSpiderWeb.delBridge(bridge.getColor());
                }
            }
            actualSpiderWeb.spiderSit(startStrand+1);
            actualSpiderWeb.spiderWalk(true);
        }
    }
}
