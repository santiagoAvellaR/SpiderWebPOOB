import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * The test class SpiderWebTest.
 *
 * This class contains unit tests for the SpiderWeb class.
 * It tests various functionalities such as adding bridges, spots, deleting bridges and spots, etc.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class SpiderwebCC2Test{
    private SpiderWeb spiderWebProof;
    /**
     * Default constructor for test class SpiderWebTest
     */
    public SpiderwebCC2Test(){
        this.spiderWebProof = new SpiderWeb(10,200);
        spiderWebProof.addBridge("red", 100,1);//adiciona un puente
        spiderWebProof.addBridge("yellow", 30,6);//adiciona un puente
        spiderWebProof.addBridge("magenta", 190,8);//adiciona un puente
        spiderWebProof.addBridge("cyan", 135,10);//adiciona un puente
        
        spiderWebProof.addSpot("blue", 1);//añade un spot
        spiderWebProof.addSpot("green", 3);//añade un spot
        spiderWebProof.addSpot("red", 5);//añade un spot
        spiderWebProof.addSpot("orange", 7);//añade un spot
    }
    
    /**
     * Tests the addition of a bridge to the spider web.
     */
    @Test
    public void shouldAddBridge(){
        String[] expectedAnswer = {"red", "yellow", "magenta", "cyan"};
        assertArrayEquals(spiderWebProof.bridges(),expectedAnswer);
        spiderWebProof.addBridge("blue", 125,1);//adiciona un puente
        assertTrue(spiderWebProof.ok());
        String[] expectedAnswer1 = {"red", "yellow", "magenta", "cyan", "blue"};
        assertArrayEquals(spiderWebProof.bridges(),expectedAnswer1);
        spiderWebProof.addBridge("orange", 100,5);//adiciona un puente
        assertTrue(spiderWebProof.ok());
        String[] expectedAnswer2 = {"red", "yellow", "magenta", "cyan", "blue", "orange"};
        assertArrayEquals(spiderWebProof.bridges(),expectedAnswer2);
        spiderWebProof.addBridge("gray", 100,9);//adiciona un puente
        assertTrue(spiderWebProof.ok());
        String[] expectedAnswer3 = {"red", "yellow", "magenta", "cyan", "blue", "orange", "gray"};
        assertArrayEquals(spiderWebProof.bridges(),expectedAnswer3);
    }
    
    /**
     * Tests scenarios where a bridge should not be added to the spider web.
     */
    @Test
    public void shouldNotAddBridge(){
        spiderWebProof.addBridge("red",201,1);//Crear un puente mas grande lo debido, no deberia dejar por lo que la ultima accion va a ser falsa
        assertFalse(spiderWebProof.ok());
        spiderWebProof.addBridge("red",50,1);//no deberia adicionar un puente con el mismo color
        assertFalse(spiderWebProof.ok());
        spiderWebProof.addBridge("red",70,0);//Crear un puente en un hilo inexistente
        assertFalse(spiderWebProof.ok());
        spiderWebProof.addBridge("red",70,12);//Crear un puente en un hilo inexistente
        assertFalse(spiderWebProof.ok());
        spiderWebProof.addBridge("green",70,3);//Crear un puente de color verde, ya que este es reservado para LastPath
        assertFalse(spiderWebProof.ok());
        spiderWebProof.addBridge("pink",100,1);//Crear un puente donde ya hay un puente
        assertFalse(spiderWebProof.ok());
        spiderWebProof.addBridge("pink",100,2);//Crear un puente y que quede al lado de otro de misma distancia
        assertFalse(spiderWebProof.ok());
        spiderWebProof.addBridge("pink",100,10);//Crear un puente y que quede al lado de otro de misma distancia
        assertFalse(spiderWebProof.ok());
    }
    
    /**
     * Tests the deletion of a bridge from the spider web.
     */
    @Test
    public void shouldDeleteBridge(){
        spiderWebProof.delBridge("red"); //eliminar el puente existente
        assertTrue(spiderWebProof.ok());
        spiderWebProof.delBridge("yellow"); //eliminar el puente existente
        assertTrue(spiderWebProof.ok());
        spiderWebProof.delBridge("magenta"); //eliminar el puente existente
        assertTrue(spiderWebProof.ok());
        spiderWebProof.delBridge("cyan"); //eliminar el puente existente
        assertTrue(spiderWebProof.ok());
    }
    
    /**
     * Tests scenarios where a bridge should not be deleted from the spider web.
     */
    @Test
    public void shouldNotDeleteBridge(){
        spiderWebProof.delBridge("gray"); //eliminar el puente inexistente
        assertFalse(spiderWebProof.ok());
        spiderWebProof.delBridge("green"); //eliminar el puente inexistente
        assertFalse(spiderWebProof.ok());
    }
    
    /**
     * Tests the relocation of a bridge within the spider web.
     */
    @Test
    public void shouldRelocateBridge(){
    }
    
    /**
     * Tests scenarios where a bridge should not be relocated within the spider web.
     */
    @Test
    public void shouldNotRelocateBridge(){
        spiderWebProof.relocateBridge("gray", 3); //reubicar un puente inexistente
        assertFalse(spiderWebProof.ok());
        spiderWebProof.relocateBridge("green", 5); //reubicar un puente inexistente
        assertFalse(spiderWebProof.ok());
    }
    
    /**
     * Tests the retrieval of the strand and radius of a specific bridge.
     */
    @Test
    public void shouldReturnTheStrandAndRadiusOfABridge(){
        int[] expectedAnswer = {1,100};
        assertArrayEquals(spiderWebProof.bridge("red"), expectedAnswer);//indica en que hilo nace un puente y su distancia
        expectedAnswer[0] = 6;
        expectedAnswer[1]= 30;
        assertArrayEquals(spiderWebProof.bridge("yellow"), expectedAnswer);//indica en que hilo nace un puente y su distancia
        expectedAnswer[0] = 8;
        expectedAnswer[1]= 190;
        assertArrayEquals(spiderWebProof.bridge("magenta"), expectedAnswer);//indica en que hilo nace un puente y su distancia
        expectedAnswer[0] = 10;
        expectedAnswer[1]= 135;
        assertArrayEquals(spiderWebProof.bridge("cyan"), expectedAnswer);//indica en que hilo nace un puente y su distancia
    }
    
    /**
     * Tests scenarios where the strand and radius of a bridge should not be retrieved.
     */
    @Test
    public void shouldNotReturnTheStrandAndRadiusOfABridge(){
        int[] expectedAnswer = {};
        assertArrayEquals(spiderWebProof.bridge("gray"), expectedAnswer); //informacion de  un puente inexistente
        assertFalse(spiderWebProof.ok());
        assertArrayEquals(spiderWebProof.bridge("green"), expectedAnswer); //informacion de un puente inexistente
        assertFalse(spiderWebProof.ok());
    }
    
    /**
     * Tests the retrieval of all bridge colors in the spider web.
     */
    @Test
    public void shouldReturnTheColorOfAllTheBridges(){
        String[] expectedAnswer = {"red", "yellow", "magenta", "cyan"};
        assertArrayEquals(spiderWebProof.bridges(),expectedAnswer);
    }
    
    /**
     * Tests the addition of a spot to the spider web.
     */
    @Test
    public void shouldAddASpot(){
        spiderWebProof.addSpot("pink", 2);//añade un spot
        assertTrue(spiderWebProof.ok());
        
        spiderWebProof.addSpot("gray", 4);//añade un spot
        assertTrue(spiderWebProof.ok());
        
        spiderWebProof.addSpot("cyan", 6);//añade un spot
        assertTrue(spiderWebProof.ok());
        
    }
    
    /**
     * Tests scenarios where a spot should not be added to the spider web.
     */
    @Test
    public void shouldNotAddASpot(){
        spiderWebProof.addSpot("red",15);//añade un spot a un hilo inexistente, ultima accion va a ser fasa
        assertFalse(spiderWebProof.ok());
        spiderWebProof.addSpot("red",0);//añade un spot a un hilo inexistente, ultima accion va a ser fasa
        assertFalse(spiderWebProof.ok());
        spiderWebProof.addSpot("blue",2);//añade un spot con un color que ya está en uso
        assertFalse(spiderWebProof.ok());
        spiderWebProof.addSpot("cyan",1);//añade un spot a un hilo que ya tiene un spot
        assertFalse(spiderWebProof.ok());
    }
    
    /**
     * Tests the deletion of a spot from the spider web.
     */
    @Test
    public void shouldDeleteASpot(){
        spiderWebProof.delSpot("blue");//elimina un spot
        assertTrue(spiderWebProof.ok());
        spiderWebProof.delSpot("green");//elimina un spot
        assertTrue(spiderWebProof.ok());
        spiderWebProof.delSpot("red");//elimina un spot
        assertTrue(spiderWebProof.ok());
        spiderWebProof.delSpot("orange");//elimina un spot
        assertTrue(spiderWebProof.ok());
    }
    
    /**
     * Tests scenarios where a spot should not be deleted from the spider web.
     */
    @Test
    public void shouldNotDeleteASpot(){
        spiderWebProof.delSpot("cyan");//elimina un spot inexistente
        assertFalse(spiderWebProof.ok());
        spiderWebProof.delSpot("gray");//elimina un spot inexistente
        assertFalse(spiderWebProof.ok());
        spiderWebProof.delSpot("purple");//elimina un spot inexistente
        assertFalse(spiderWebProof.ok());
        spiderWebProof.delSpot("white");//elimina un spot inexistente
        assertFalse(spiderWebProof.ok());
    }
    
    /**
     * Tests the retrieval of the strand where a specific spot is located.
     */
    @Test
    public void shouldReturnTheStrandOfTheSpot(){
        assertEquals(spiderWebProof.spot("blue"), 1);//hilo en el que esta un spot
        assertTrue(spiderWebProof.ok());
        assertEquals(spiderWebProof.spot("green"), 3);//hilo en el que esta un spot
        assertTrue(spiderWebProof.ok());
        assertEquals(spiderWebProof.spot("red"), 5);//hilo en el que esta un spot
        assertTrue(spiderWebProof.ok());
        assertEquals(spiderWebProof.spot("orange"), 7);//hilo en el que esta un spot
        assertTrue(spiderWebProof.ok());
    }
    
    /**
     * Tests scenarios where the strand of a spot should not be retrieved.
     */
    @Test
    public void shouldNotReturnTheStrandOfTheSpot(){
        assertEquals(spiderWebProof.spot("gray"), -500);//hilo en el que esta un spot
        assertFalse(spiderWebProof.ok());
        assertEquals(spiderWebProof.spot("cyan"), -500);//hilo en el que esta un spot
        assertFalse(spiderWebProof.ok());
    }
    
    /**
     * Tests the retrieval of all spot colors in the spider web.
     */
    public void shouldReturnTheColorsOfTheSpot(){
        String[] expectedAnswer = {"blue", "green", "red", "orange"};
        assertArrayEquals(spiderWebProof.spots(),expectedAnswer);
    }
    
    /**
     * Tests the addition of a strand to the spider web.
     */
    public void shouldAddAStrand(){
        assertEquals(spiderWebProof.getNumberStrands(), 10);
        spiderWebProof.addStrand();
        assertTrue(spiderWebProof.ok());
        assertEquals(spiderWebProof.getNumberStrands(), 11);
        spiderWebProof.addStrand();
        assertTrue(spiderWebProof.ok());
        assertEquals(spiderWebProof.getNumberStrands(), 12);
    }
    
    /**
     * Tests the enlargement of the spider web.
     */
    @Test
    public void shouldEnlargeSpiderWeb(){
        assertEquals(spiderWebProof.getLargeStrand(), 200);
        spiderWebProof.enlarge(1);
        assertTrue(spiderWebProof.ok());
        assertEquals(spiderWebProof.getLargeStrand(), 200);
        spiderWebProof.enlarge(0.5);
        assertTrue(spiderWebProof.ok());
        assertEquals(spiderWebProof.getLargeStrand(), 100);
        spiderWebProof.enlarge(4);
        assertTrue(spiderWebProof.ok());
        assertEquals(spiderWebProof.getLargeStrand(), 400);
    }
    
    /**
     * Tests scenarios where the spider web should not be enlarged.
     */
    @Test
    public void shouldNotEnlargeSpiderWeb(){
        spiderWebProof.enlarge(-2);//al ser un numero negativo no debe cambiar el valor
        assertFalse(spiderWebProof.ok());
        assertEquals(spiderWebProof.getLargeStrand(), 200);
        spiderWebProof.enlarge(0);//al ser un numero negativo no debe cambiar el valor
        assertFalse(spiderWebProof.ok());
        assertEquals(spiderWebProof.getLargeStrand(), 200);
    }
    
    /**
     * Tests the retrieval of unused bridges in the spider web.
     */
    public void shouldReturnTheUnUsedBridges(){
        String[] expectedAnswer = {};
        assertArrayEquals(spiderWebProof.unUsedBridges(), expectedAnswer);

    }
    
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp(){
        this.spiderWebProof = new SpiderWeb(10,200);
        spiderWebProof.addBridge("red", 100,1);//adiciona un puente
        spiderWebProof.addBridge("yellow", 30,6);//adiciona un puente
        spiderWebProof.addBridge("magenta", 190,8);//adiciona un puente
        spiderWebProof.addBridge("cyan", 135,10);//adiciona un puente
        
        spiderWebProof.addSpot("blue", 1);//añade un spot
        spiderWebProof.addSpot("green", 3);//añade un spot
        spiderWebProof.addSpot("red", 5);//añade un spot
        spiderWebProof.addSpot("orange", 7);//añade un spot
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
}