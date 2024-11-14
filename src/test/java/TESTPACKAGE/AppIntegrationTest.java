package TESTPACKAGE;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class AppIntegrationTest
{
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
        // if running locally delay can be zero if database is running
        // To work on GitHub Actions set the delay to 10000 as the database takes a while to build
        app.connect("localhost:33060", 10000);

    }

    @Test
    void testGetCity()
    {
        City city = app.getCity(1);
        assertEquals("Kabul", city.getName());
        System.out.println("successfully retrieved " + city);
    }
}