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
    @Test
    void testUKCitiesReport() {
        app.generateUKCitiesReport();
        ArrayList<City> ukCities = app.getUKCitiesSortedByPopulation();

        assertNotNull(ukCities, "UK city list should not be null");
        assertFalse(ukCities.isEmpty(), "UK city list should not be empty");

        for (int i = 1; i < ukCities.size(); i++) {
            assertTrue(ukCities.get(i - 1).getPopulation() >= ukCities.get(i).getPopulation(),
                    "Cities are not sorted correctly by population");
        }
    }
    @Test
    void testKyotoDistrictCitiesReport() {
        app.generateKyotoDistrictCitiesReport();
        ArrayList<City> kyotoCities = app.getKyotoDistrictCitiesSortedByPopulation();

        assertNotNull(kyotoCities, "Kyoto city list should not be null");
        assertFalse(kyotoCities.isEmpty(), "Kyoto city list should not be empty");

        for (int i = 1; i < kyotoCities.size(); i++) {
            assertTrue(kyotoCities.get(i - 1).getPopulation() >= kyotoCities.get(i).getPopulation(),
                    "Cities are not sorted correctly by population");
        }
    }
    @Test
    void testCapitalCitiesReportByRegion() {
        ArrayList<City> capitalCities = app.getCapitalCitiesByRegion("British Islands");

        assertNotNull(capitalCities, "Capital cities list should not be null");
        assertFalse(capitalCities.isEmpty(), "Capital cities list should not be empty");

        System.out.println("Capital Cities in Region 'British Islands':");
        for (City city : capitalCities) {
            System.out.println(city.getName() + ": " + city.getPopulation());
        }

        // Verify sorting by population in descending order
        for (int i = 1; i < capitalCities.size(); i++) {
            assertTrue(capitalCities.get(i - 1).getPopulation() >= capitalCities.get(i).getPopulation(),
                    "Capital cities are not sorted correctly by population");
        }
    }
    @Test
    void testCapitalCitiesReportByContinent() {
        ArrayList<City> capitalCities = app.getCapitalCitiesByContinent("Europe");

        assertNotNull(capitalCities, "Capital cities list should not be null");
        assertFalse(capitalCities.isEmpty(), "Capital cities list should not be empty");

        System.out.println("Capital Cities in Continent 'Europe':");
        for (City city : capitalCities) {
            System.out.println(city.getName() + ": " + city.getPopulation());
        }

        // Verify sorting by population in descending order
        for (int i = 1; i < capitalCities.size(); i++) {
            assertTrue(capitalCities.get(i - 1).getPopulation() >= capitalCities.get(i).getPopulation(),
                    "Capital cities are not sorted correctly by population");
        }
    }
    @Test
    void testAllCapitalCitiesSortedByPopulation() {
        ArrayList<City> capitalCities = app.getAllCapitalCitiesSortedByPopulation();

        assertNotNull(capitalCities, "Capital cities list should not be null");
        assertFalse(capitalCities.isEmpty(), "Capital cities list should not be empty");

        System.out.println("All Capital Cities Sorted by Population:");
        for (City city : capitalCities) {
            System.out.println(city.getName() + ": " + city.getPopulation());
        }

        // Verify sorting by population in descending order
        for (int i = 1; i < capitalCities.size(); i++) {
            assertTrue(capitalCities.get(i - 1).getPopulation() >= capitalCities.get(i).getPopulation(),
                    "Capital cities are not sorted correctly by population");
        }
    }

}


