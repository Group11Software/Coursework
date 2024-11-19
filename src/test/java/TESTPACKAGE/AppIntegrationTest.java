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
    @Test
    void CityLargestToSmallestAsia() {
        // Use the new method to get Asian cities sorted by population
        ArrayList<City> cities = app.getAsianCitiesSortedByPopulation();

        // Ensure the returned list is not null
        assertNotNull(cities, "City list should not be null");

        System.out.println("Cities in Asia sorted from largest to smallest population:");
        for (City city : cities) {
            System.out.println(city.getName() + ": " + city.getPopulation());
        }

        // Verify that the cities are sorted in descending order of population
        for (int i = 1; i < cities.size(); i++) {
            assertTrue(cities.get(i - 1).getPopulation() >= cities.get(i).getPopulation(),
                    "Cities are not sorted correctly by population");
        }
    }

    @Test
    public void testNorthAmericaCitiesReport() {
        app.generateNorthAmericaCitiesReport();
        String reportFilePath = "./output/NorthAmericaCitiesReport.txt";
        StringBuilder reportContent = new StringBuilder();

        String[] reportLines = reportContent.toString().split("\n");

        ArrayList<Long> populations = new ArrayList<>();
        for (String line : reportLines) {
            if (line.contains(":")) {
                String populationStr = line.substring(line.lastIndexOf(":") + 1).trim();
                try {
                    long population = Long.parseLong(populationStr);
                    populations.add(population);
                } catch (NumberFormatException e) {
                }
            }
        }
        for (int i = 1; i < populations.size(); i++) {
            assertTrue(populations.get(i - 1) >= populations.get(i), "Cities are not sorted correctly by population");
        }
    }
}


