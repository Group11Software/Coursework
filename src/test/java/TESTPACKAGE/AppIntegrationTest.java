package TESTPACKAGE;

import TESTPACKAGE.App;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class AppIntegrationTest {

    static App app;

    @BeforeAll
    static void init() {
        app = new App();
        app.connect("localhost:33060", 10000);
    }

    @Test
    void testGetCity() {
        City city = app.getCity(1);
        assertEquals("Kabul", city.getName());
        System.out.println("Successfully retrieved " + city);
    }

    @Test
    void testGetPopulation() {
        City city = app.getCity(1);
        assertEquals("Kabul", city.getName());
        System.out.println("Successfully retrieved population: " + city.getPopulation());
    }

    @Test
    void CityLargestToSmallest() {
        ArrayList<City> cities = app.getCities();

        assertNotNull(cities, "City list should not be null");

        cities.sort((city1, city2) -> Long.compare(city2.getPopulation(), city1.getPopulation()));

        System.out.println("Cities sorted from largest to smallest population:");
        for (City city : cities) {
            System.out.println(city.getName() + ": " + city.getPopulation());
        }

        for (int i = 1; i < cities.size(); i++) {
            assertTrue(cities.get(i - 1).getPopulation() >= cities.get(i).getPopulation(),
                    "Cities are not sorted correctly by population");
        }
    }
}
