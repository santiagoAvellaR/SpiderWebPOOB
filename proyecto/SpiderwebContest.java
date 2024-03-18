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
    private ArrayList<SpiderWeb> spiderWebs;
    
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
    private void updateBridges(int[] bridges, int strand) {
        int len = bridges.length;
        bridges[strand] = bridges[(strand + 1) % len];
        if (bridges[strand] < bridges[(strand + 1) % len]) {
            for (int i = 0; i < len; i++) {
                int pos2 = (strand - i) >= 0 ? (strand - i) : (len - (i - strand));
                int pos = (pos2 == 0) ? len - 1 : pos2 - 1;
                bridges[pos2] = Math.min(Math.min(bridges[(pos2 + 1) % len] + 1, bridges[pos] + 1), bridges[pos2]);
            }
        } else if (bridges[strand] > bridges[(strand + 1) % len]) {
            for (int i = 0; i < len; i++) {
                int pos2 = (strand + i) % len;
                int pos = (pos2 == 0) ? len - 1 : pos2 - 1;
                bridges[pos2] = Math.min(Math.min(bridges[(pos2 + 1) % len] + 1, bridges[pos] + 1), bridges[pos2]);
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
    public int[] solve(int strands, int favorite, int[][] bridges) {
        int m = strands;
        int s = favorite;
        int[] minimunNumberOfBridges = new int[m];
        int pos = s - 1;
        for (int i = -1; i < m - 1; i++) {
            minimunNumberOfBridges[pos] = Math.min(i + 1, m - i - 1);
            pos = (pos + 1) % m;
        }

        Arrays.sort(bridges, (a, b) -> Integer.compare(b[0], a[0]));
        for (int i = 0; i < bridges.length; i++) {
            updateBridges(minimunNumberOfBridges, bridges[i][1] - 1);
        }
        return minimunNumberOfBridges;
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
    }
}
