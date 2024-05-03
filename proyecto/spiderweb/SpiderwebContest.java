package spiderweb;

import shapes.*;

import java.util.*;
import javax.swing.JOptionPane;

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
    private String[] colors = {"red", "orange", "blue", "yellow", "green", "magenta", "cyan", 
                   "darkGray", "gray", "lightGray", "orange", "pink", "darkGreen", 
                   "brown", "darkBrown", "purple", "golden", "burgundy", "blueGray"};
    private boolean hasFoundASolution;
    
    /**
     * Constructs a SpiderwebContest object.
     */
    public SpiderwebContest(){
        spiderWebs = new ArrayList<>();
        hasFoundASolution = false;
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
    // aproximacion iterativa
    
    /*
    public void simulate(int strands, int favourite, int[][] bridges){
        ArrayList<Integer> finalPositions = new ArrayList<>();
        int[] minimunBridges = solve(strands, favourite, bridges);
        actualSpiderWeb = new SpiderWeb(strands, favourite, bridges);
        actualSpiderWeb.makeVisible();
        ArrayList<Bridge> bridgesForSimulation = new ArrayList<>();
        for(int startStrand = 0; startStrand < strands; startStrand++){
            if(minimunBridges[startStrand] != 0){
                int reachableStrand = actualSpiderWeb.spiderWalkSimulate(startStrand)+1;
                boolean arriveFartherThanInitialStrand = (Math.abs(reachableStrand-favourite))>=(Math.abs(startStrand-favourite));
                int missingBridges = (arriveFartherThanInitialStrand)?-1:0;
                int middle = (favourite+strands/2)%strands;
                if(strands%2 == 0){
                    //busca por el mas cercano
                    if(middle <= reachableStrand && reachableStrand < favourite){
                        for(int j = reachableStrand; reachableStrand < favourite; j++){
                            if(actualSpiderWeb.hasBridgeBetweenStrands(j, j+1)){
                                int[] possibleRadiusNewBridge = actualSpiderWeb.maximunBridgesRadiusInTheStrand(j);
                                actualSpiderWeb.addBridge(colors[j%strands], possibleRadiusNewBridge[possibleRadiusNewBridge.length-1]+5, j);
                            }
                            else{
                                int[] possibleRadiusNewBridge = actualSpiderWeb.maximunBridgesRadiusInTheStrand(j);
                                actualSpiderWeb.addBridge(colors[j%strands], possibleRadiusNewBridge[possibleRadiusNewBridge.length-1]+5, j);
                            }
                        }
                    }
                    else{
                        for(int j = reachableStrand; reachableStrand > favourite; j--){  
                        }
                    }
                }
                else{
                    boolean found = false;
                    if(reachableStrand == middle){ // busca por ambos lados
                        for(int j = reachableStrand; reachableStrand < favourite; j++){
                        }
                        if(!found){
                            for(Bridge bridge : bridgesForSimulation){
                                actualSpiderWeb.delBridge(bridge.getColor());
                            }
                            for(int j = reachableStrand; reachableStrand > favourite; j--){    
                            }
                        }
                    }
                    else{ // busca por el mas cercano
                    }
                }
            }
            actualSpiderWeb.spiderSit(startStrand+1);
            actualSpiderWeb.spiderWalk(true);
            finalPositions.add(actualSpiderWeb.spiderWalkSimulate(startStrand));
            // eliminar todo para la siguiente simulacion del hilo siguiente
            for(Bridge bridge : bridgesForSimulation){
                actualSpiderWeb.delBridge(bridge.getColor());
            }
        }
    }
    */
   
    
    // Aproximacion recursiva
    
    /*
     * Method tha simulates the solution for each strand
       */
    public int[] simulate(int strands, int favourite, int[][] bridges, boolean makeVisible){
        for(int i = 0; i < bridges.length; i++){
            bridges[i][0]*=10;
        }
        ArrayList<Integer> finalPositions = new ArrayList<>(); 
        for(int strand = 1; strand <= strands; strand++){
            hasFoundASolution = false;
            simulateForOneStrand(strands, favourite, bridges, strand, makeVisible);
            finalPositions.add(actualSpiderWeb.spiderWalkSimulate(strand-1));
            if(makeVisible){
                actualSpiderWeb.makeVisible();
               JOptionPane.showMessageDialog(null, "solucion hilo: " + strand);
               actualSpiderWeb.spiderSit(strand);
               actualSpiderWeb.spiderWalk(true);
               actualSpiderWeb.makeInvisible();
            }
        }
        return finalPositions.stream().mapToInt(Integer::intValue).toArray();
    }
    
    public void simulateForOneStrand(int strands, int favorite, int[][] bridges, int strand, boolean watch){
        int[] minimunNumberBridges = solve(strands, favorite, bridges);
        actualSpiderWeb = new SpiderWeb(strands, favorite, bridges);
        // ajustar el tamaño de los hilos para que todos los puentes puedan crearse
        if(actualSpiderWeb.getRadiusStrand()<bridges[0][0]){
            int radiusDistanceDifference = bridges[0][0]-actualSpiderWeb.getRadiusStrand();
            int percentage = (int)((double)radiusDistanceDifference*(double)100/(double)actualSpiderWeb.getRadiusStrand());
            actualSpiderWeb.enlarge(percentage<1? 10 : percentage);
        }
        int numberBridgesToCreate = minimunNumberBridges[strand-1];
        actualSpiderWeb.spiderSit(strand);
        // comenzar iteracion recursiva por derecha
        if(!hasFoundASolution){
            traverseClockWise(3, favorite-1, numberBridgesToCreate, strands, strand, bridges, actualSpiderWeb, strand-1);
        }
        // si no encontro solucion por derecha, comenzar iteracion recursiva por derecha
        if(!hasFoundASolution){
            traverseCounterClockWise(3, favorite-1, numberBridgesToCreate, strands, strand, bridges, actualSpiderWeb, strand-1);
        }
    }
    
    private void traverseClockWise(int height, int fav,int remainingBridges,int numberStrands, int strand, int[][]bridges, SpiderWeb spiderWeb, int start){
        // si ya uso todos los puentes y llega al favorito, encontro respuesta
        if(remainingBridges == 0){
            if(spiderWeb.spiderWalkSimulate(start) == fav){
                hasFoundASolution = true;
                return;
            }
        }
        // si aun quedan puentes, seguir iterando, si no para y deja de hacer el llamado recursivo
        if(remainingBridges >0 && !hasFoundASolution){
            List<int[]> foundBridges = findBridgesClockWise(bridges, height, strand, numberStrands);
            for(int[] bridge : foundBridges){
                if(hasFoundASolution){return;}
                if(bridge.length == 2){
                    spiderWeb.addBridge(colors[remainingBridges%colors.length],bridge[0],strand);
                    traverseClockWise(bridge[0], fav, remainingBridges-1, numberStrands, bridge[1], bridges, spiderWeb, start);
                    if(!hasFoundASolution)
                        spiderWeb.delBridge(colors[remainingBridges%colors.length]);
                }else{
                    traverseClockWise(bridge[0], fav, remainingBridges, numberStrands, (bridge[1]+1)%numberStrands, bridges, spiderWeb, start);
                }
            }
        }
    }
    
    private List<int[]> findBridgesClockWise(int[][] bridges,int height, int strand, int size){
        List<int[]> foundBridges = new ArrayList<>();
        int[] initialBridge = {height+1, (strand+1)%size};
        foundBridges.add(initialBridge);
        for(int i = bridges.length -1; i > -1; i--){
            int[] bridge = bridges[i];
            if(bridge[0]>height && bridge[1]==strand){
                int[] bridgeInformation = {bridge[0],bridge[1],0};
                foundBridges.add(bridgeInformation);
                break;
            }
        }
        for(int[] bridge:bridges){
            if(foundBridges.size() > 1 && foundBridges.get(1)[0] > bridge[0] && bridge[0]>height+1 && bridge[1]==(strand+1)%size){
                int[] bridgeInformation = {bridge[0]+5,bridge[1]};
                foundBridges.add(bridgeInformation);
            }else if(foundBridges.size()==1 && bridge[0]>height+1 && bridge[1]==(strand+1)%size){
                int[] bridgeInformation = {bridge[0]+5,bridge[1]};
                foundBridges.add(bridgeInformation);
            }
        }
        return foundBridges;
    }
    private void traverseCounterClockWise(int height, int fav, int remainingBridges, int numberStrands, int strand, int[][]bridges, SpiderWeb spiderWeb, int start){
        if(remainingBridges == 0){
            if(spiderWeb.spiderWalkSimulate(start) == fav){
                hasFoundASolution = true;
                return;
            }
        }
        if(remainingBridges > 0 && !hasFoundASolution){
            List<int[]> foundBridges = findBridgesCounterClockWise(bridges, height, strand, numberStrands);
            for(int[] bridge : foundBridges){
                if(hasFoundASolution) return;
                System.out.println(bridge.length +" "+ bridge[1] + " " + bridge[0]);
                if(bridge.length == 2){
                    spiderWeb.addBridge(colors[remainingBridges%colors.length],bridge[0],bridge[1]);
                    traverseCounterClockWise(bridge[0], fav, remainingBridges - 1, numberStrands, bridge[1], bridges, spiderWeb, start);
                    if(!hasFoundASolution)
                        spiderWeb.delBridge(colors[remainingBridges%colors.length]);
                }else{
                    int pos = strand!=0?strand-1:numberStrands-1;
                    traverseCounterClockWise(bridge[0], fav, remainingBridges, numberStrands, pos, bridges, spiderWeb, start);
                }
            }
        }
    }
    
    private List<int[]> findBridgesCounterClockWise(int[][] bridges,int height, int strand, int size){
        List<int[]> foundBridges = new ArrayList<>();
        int pos = strand!=0?strand-1:size-1;
        int[] initialBridge = {height+1,pos};
        foundBridges.add(initialBridge);
        for(int i = bridges.length - 1; i > -1; i--){
            int[] bridge = bridges[i];
            if(bridge[0]>height && bridge[1]==pos){
                int[] bridgeInformation = {bridge[0],bridge[1],0};
                foundBridges.add(bridgeInformation);
                break;
            }
        }
        int pos2 = pos!=0?pos-1:size-1;
        for(int[] bridge:bridges){
            if(foundBridges.size() > 1 && foundBridges.get(1)[0] > bridge[0] && bridge[0]>height+1 && bridge[1]==pos2){
                int[] bridgeInformation = {bridge[0]+5,pos};
                foundBridges.add(bridgeInformation);
            }else if(foundBridges.size()==1 && bridge[0]>height+1 && bridge[1]==pos2){
                int[] bridgeInformation = {bridge[0]+5,pos};
                foundBridges.add(bridgeInformation);
            }
        }
        return foundBridges;
    }
}