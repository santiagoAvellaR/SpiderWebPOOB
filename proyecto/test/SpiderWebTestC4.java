package test;
import spiderweb.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * The test class SpiderWebTestC4.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class SpiderWebTestC4
{
    private SpiderWeb spiderweb;
    /**
     * Default constructor for test class SpiderWebTestC4
     */
    public SpiderWebTestC4()
    {
        spiderweb = new SpiderWeb(7,200);
    }
    
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
        spiderweb = new SpiderWeb(7,200);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }
    
    @Test
    public void ShouldBridgeChangeSpot(){
        spiderweb.addBridge("transformer", "blue", 50, 1);
        String[] expectedAnswer = {"blue"};
        assertArrayEquals(spiderweb.bridges(),expectedAnswer);
        assertArrayEquals(new String[0], spiderweb.spots());
        spiderweb.delBridge("blue");
        String[] spots = {"blue"};
        assertArrayEquals(spiderweb.spots(),spots);
        assertArrayEquals(spiderweb.bridges(),new String[0]);
        spiderweb.makeInvisible();
    }
    
    @Test
    public void ShouldNotBridgeDelete(){
        spiderweb.addBridge("fixed", "blue", 20, 1);
        String[] expectedAnswer = {"blue"};
        assertArrayEquals(spiderweb.bridges(),expectedAnswer);
        spiderweb.delBridge("blue");
        assertArrayEquals(spiderweb.bridges(),expectedAnswer);
        spiderweb.addBridge("transformer", "red", 24, 1);
        spiderweb.delBridge("red");
        assertArrayEquals(spiderweb.bridges(),expectedAnswer);
        spiderweb.makeInvisible();
    }
    
    @Test
    public void ShouldRelocateSpot(){
        spiderweb.addSpot("bouncy", "red", 1);
        spiderweb.spiderSit(1);
        assertEquals(spiderweb.spot("red"),1);
        String[] spots = {"red"};
        assertArrayEquals(spiderweb.spots(),spots);
        spiderweb.spiderWalk(true);
        assertEquals(spiderweb.spot("red"),2);
        assertArrayEquals(spiderweb.spots(),spots);
        spiderweb.makeInvisible();
    }
    
    @Test
    public void ShouldBridgeBreak(){
        spiderweb.addBridge("weak", "blue", 20, 1);
        spiderweb.spiderSit(1);
        String[] bridge = {"blue"};
        assertArrayEquals(spiderweb.bridges(),bridge);
        spiderweb.spiderWalk(true);
         assertArrayEquals(new String[0], spiderweb.bridges());
         spiderweb.makeInvisible();
    }
    
    @Test
    public void ShouldDieSpider(){
        spiderweb.addSpot("killer", "red",1);
        spiderweb.spiderWalk(true);
        assertTrue(spiderweb.ok());
        spiderweb.makeInvisible();
    }
    
    @Test
    public void ShouldDisappearThick(){
        spiderweb.addBridge("thick", "red", 40, 1);
        assertEquals(spiderweb.getNumberBridges(), 1);
        for(int i = 0; i < 6; i++){
            spiderweb.spiderSit(1);
            spiderweb.spiderWalk(true);
        }
        assertEquals(spiderweb.getNumberBridges(), 0);
        spiderweb.makeInvisible();
    }
}