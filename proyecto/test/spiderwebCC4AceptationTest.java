package test;

import spiderweb.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.JOptionPane;

/**
 * The test class spiderwebCC4AceptationTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class spiderwebCC4AceptationTest
{
    SpiderWeb spiderweb;
    /**
     * Default constructor for test class spiderSpiderWebCC4AceptationTest
     */
    public spiderwebCC4AceptationTest()
    {
        spiderweb = new SpiderWeb(7,240);
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
        spiderweb = new SpiderWeb(7,240);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
        spiderweb.makeInvisible();
    }
    
    @Test
    public void shouldShowHowSpiderWalks(){
        //adds the spider SpiderWeb and some bridges
        JOptionPane.showMessageDialog(null, "comenzar");
        spiderweb.makeVisible();
        spiderweb.addBridge("blue",60,1);
        spiderweb.addBridge("red",80,2);
        spiderweb.addBridge("orange",100,3);
        spiderweb.makeVisible();
        JOptionPane.showMessageDialog(null, "continuar");
        spiderweb.spiderSit(1);
        spiderweb.spiderWalk(true);
        assertEquals(4,spiderweb.getStrandSpider());
        JOptionPane.showMessageDialog(null, "continuar");
        spiderweb.spiderWalk(false);
        assertTrue(spiderweb.spiderIsCentered());
        assertEquals(1,spiderweb.getStrandSpider());
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Aceptas la prueba?", "Confirmar", JOptionPane.OK_CANCEL_OPTION);
        assertEquals(respuesta,0);
    }
    
    @Test
    public void shouldShowHowThickDisappear(){
        //adds the spider SpiderWeb and some bridges
        JOptionPane.showMessageDialog(null, "comenzar");
        spiderweb.makeVisible();
        spiderweb.addBridge("thick","blue",60,1);
        spiderweb.addBridge("thick","red",80,2);
        spiderweb.addBridge("thick","orange",100,3);
        spiderweb.makeVisible();
        assertEquals(3,spiderweb.getNumberBridges());
        JOptionPane.showMessageDialog(null, "continuar");
        for(int i = 0; i < 6; i++){
            spiderweb.spiderSit(1);
            spiderweb.spiderWalk(true);
        }
        assertEquals(0,spiderweb.getNumberBridges());
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Aceptas la prueba?", "Confirmar", JOptionPane.OK_CANCEL_OPTION);
        assertEquals(respuesta,0);
    }

    @Test
    public void shouldShowHowKillerWorks(){
        //adds the spider SpiderWeb and some bridges
        JOptionPane.showMessageDialog(null, "comenzar");
        spiderweb.makeVisible();
        spiderweb.addSpot("killer","blue",1);
        spiderweb.addSpot("killer","green",2);
        spiderweb.makeVisible();
        assertEquals(2,spiderweb.getNumberSpots());
        JOptionPane.showMessageDialog(null, "continuar");
        spiderweb.spiderSit(1);
        spiderweb.spiderWalk(true);
        JOptionPane.showMessageDialog(null, "continuar");
        spiderweb.spiderSit(2);
        spiderweb.spiderWalk(true);
        assertTrue(spiderweb.spiderIsCentered());
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Aceptas la prueba?", "Confirmar", JOptionPane.OK_CANCEL_OPTION);
        assertEquals(respuesta,0);
    }
}
