import org.junit.*;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConnectionClassTest {

    private ConnectionClass connectionClass;

    @BeforeClass
    public static void setUpAll(){
        System.out.println("Before All");
    }

    @Before
    public void setUp(){
        connectionClass = new ConnectionClass();
    }

    @Test
    public void shouldARegisterUser(){
        assertTrue(connectionClass.registerUser("test1", "root", "user_details"));
    }

    @Test
    public void shouldBCheckLogin() throws Exception {
        assertTrue(connectionClass.checkLogin("test1", "root", "user_details"));
    }

    @Test
    public void shouldCAddStock(){
        assertTrue(connectionClass.addStock("TES", "test", 99.99));
    }

    @Test
    public void shouldDDeleteStock(){
        assertTrue(connectionClass.deleteStock("TES"));
    }

    @Test
    public void shouldEDeleteUser(){
        assertTrue(connectionClass.deleteUser("test1"));
    }

    @After
    public void tearDown(){
    }

    @AfterClass
    public static void tearDownAll(){
        System.out.println("After All");
    }
}
