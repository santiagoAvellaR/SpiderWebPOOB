package test;
import spiderweb.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class SpiderwebContestCTest.
 *
 * This class contains unit tests for the SpiderwebContest class.
 * It tests various functionalities such as simulating a spider web contest and solving spider web problems.
 * 
 * @author  (your name)
 * @version (a version number or a date)
 */
public class SpiderwebContestCTest{
    private SpiderwebContest contest;

    /**
     * Default constructor for test class SpiderwebContestCTest
     */
    public SpiderwebContestCTest(){
    }

    /**
     * Sets up the test fixture before each test case method.
     */
    @BeforeEach
    public void setUp(){
        contest = new SpiderwebContest();
    }

    /**
     * Tears down the test fixture after each test case method.
     */
    @AfterEach
    public void tearDown(){
        contest = null;
    }

    /**
     * Tests the solution of a spider web contest.
     */
    @Test
    public void shouldSolveSpiderwebProblem(){
        int strands = 10;
        int favorite = 5;
        int[][] bridges = {
            {10, 1},
            {3, 6},
            {19, 8},
            {13, 10}
        };

        int[] result = contest.solve(strands, favorite, bridges);
        int[] expectedResult = {3, 3, 2, 1, 0, 1, 1, 2, 3, 4};
        assertArrayEquals(result, expectedResult);
    }

    /**
     * Tests the solution of a spider web problem.
     */
    @Test
    public void shouldSolveSpiderwebProblem2(){
        int strands = 7;
        int favorite = 6;
        int[][] bridges = {
            {2, 1},
            {4, 3},
            {6, 3},
            {8, 7},
            {10, 5}
        };

        int[] result = contest.solve(strands, favorite, bridges);
        int[] expectedResult = {2, 1, 1, 1, 0, 1, 2};
        assertArrayEquals(result, expectedResult);
    }
     /**
     * Tests the solution of a spider web problem.
     */
    @Test
    public void shouldSolveSpiderwebProblem3(){
        int strands = 4;
        int favorite = 2;
        int[][] bridges = {
            {1, 1},
            {2, 2},
            {3, 3},
            {4, 4}
        };

        int[] result = contest.solve(strands, favorite, bridges);
        int[] expectedResult = {1,1,0,1};
        assertArrayEquals(result, expectedResult);
    }
    /**
     * Tests the simulation of a spider web contest.
     */
    @Test
    public void shouldSimulateSpiderwebProblem(){
       int strands = 10;
        int favorite = 5;
        int[][] bridges = {
            {10, 1},
            {3, 6},
            {19, 8},
            {13, 10}
        };

        int[] result = contest.simulate(strands, favorite, bridges, false);
        int[] expectedResult = {5, 5, 5, 5};
        assertArrayEquals(result, expectedResult);
    }
    
     /**
     * Tests the simulation of a spider web problem.
     */
    @Test
    public void shouldSimulateSpiderwebProblem2(){
        int strands = 7;
        int favorite = 6;
        int[][] bridges = {
            {2, 1},
            {4, 3},
            {6, 3},
            {8, 7},
            {10, 5}
        };

        int[] result = contest.simulate(strands, favorite, bridges, false);
        int[] expectedResult = {6,6,6,6,6,6,6};
        assertArrayEquals(result, expectedResult);
    }
     /**
     * Tests the simulation of a spider web problem.
     */
    @Test
    public void shouldSimulateSpiderwebProblem3(){
        int strands = 4;
        int favorite = 2;
        int[][] bridges = {
            {1, 1},
            {2, 2},
            {3, 3},
            {4, 4}
        };

        int[] result = contest.simulate(strands, favorite, bridges, false);
        int[] expectedResult = {2,2,2,2};
        assertArrayEquals(result, expectedResult);
    }
    
    
}