import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
/**
 * The test class SpiderWebTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class SpiderwebCC2Test{
    /**
     * Default constructor for test class SpiderWebTest
     */
    public SpiderwebCC2Test(){
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Test
    public void shouldNotCreate() {
        SpiderWeb aux = new SpiderWeb(5,200);
        aux.addBridge("red",201,1);//Crear un puente mas grande lo debido, no deberia dejar por lo que la ultima accion va a ser falsa
        assertFalse(aux.ok());
        aux.addBridge("red", 100,1);//adiciona un puente
        aux.addBridge("red",50,1);//no deberia adicionar un puente con el mismo color
        String[] coloresEsperados = {"red"};
        assertArrayEquals(aux.bridges(),coloresEsperados);
        assertEquals(aux.bridge("red"), 1);//indica en que hilo nace un puente
        //aux.delBridge("red");
        //assertNull(aux.bridges());
        aux.addSpot("pink", 2);//añade un spot
        aux.addSpot("red",8);//añade un spot a un hilo inexistente, ultima accion va a ser fasa
        assertFalse(aux.ok());
        coloresEsperados[0] = "pink";//como el ultimo spot no debio ser añadido solo deberia estar el color pink
        assertArrayEquals(aux.spots(),coloresEsperados);
        assertEquals(aux.spot("pink"), 2);//indica en que hilo esta el spot
        aux.delSpot("red");//elimina un spot inexistente, ultima accion falsa, pero no debe eliminar nada del contenedor
        assertArrayEquals(aux.spots(),coloresEsperados);
        aux.enlarge(2);//hace que el tamaño de la telaraña crezca el doble
        assertEquals(aux.getLargeStrand(), 400);
        aux.enlarge(-2);//al ser un numero negativo no debe cambiar el valor
        assertEquals(aux.getLargeStrand(), 400);
        aux.addStrand();//se aumenta el numero de hilos, siendo ahora 6
        assertEquals(aux.getNumberStrands(), 6);
        
    }
    
    @BeforeEach
    public void setUp()
    {
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