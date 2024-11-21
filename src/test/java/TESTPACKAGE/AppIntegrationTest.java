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

    /**
     * All the cities in the world sorted by population.
     */

    @Test
    public void testWorldCityReport() {
        ArrayList<City> cities = app.getCities();
        assertNotNull(cities, "City list should not be null");

        app.printCityReport(cities);

        for (int i = 1; i < cities.size(); i++) {
            assertTrue(cities.get(i - 1).getPopulation() >= cities.get(i).getPopulation(),
                    "Cities are not sorted correctly by population");
        }

        City firstCity = cities.get(0);
        assertNotNull(firstCity, "First city should not be null");
        System.out.printf("Largest city: %-20s Population: %,d%n",
                firstCity.getName(), firstCity.getPopulation());
    }



    /**
     * Integration test for - All cities in the continent Asia sorted by population.
     */
    @Test
    void CityLargestToSmallestAsia() {
        ArrayList<City> cities = app.getAsianCitiesSortedByPopulation();

        assertNotNull(cities, "City list should not be null");

        System.out.println("Cities in Asia sorted from largest to smallest population:");
        System.out.printf("%-20s %-20s %-20s %-15s%n", "Name", "Country", "District", "Population");
        System.out.println("-------------------------------------------------------------------------------");

        for (City city : cities) {
            Country country = app.getCountryByCode(city.getCountryCode());
            String countryName = (country != null) ? country.getName() : "Unknown";
            System.out.printf("%-20s %-20s %-20s %-15d%n",
                    city.getName(), countryName, city.getDistrict(), city.getPopulation());
        }

        for (int i = 1; i < cities.size(); i++) {
            assertTrue(cities.get(i - 1).getPopulation() >= cities.get(i).getPopulation(),
                    "Cities are not sorted correctly by population");
        }
    }


    /**
     * Integration test for - All cities in a region sorted by population, ours being North America
     */
    @Test
    public void testNorthAmericaCitiesReport() {
        ArrayList<City> cities = app.getCitiesByContinent("North America");

        assertNotNull(cities, "City list should not be null");

        System.out.println("Cities in North America sorted from largest to smallest population:");
        System.out.printf("%-25s %-15s %-20s %-12s%n", "Name", "Country", "District", "Population");
        System.out.println("---------------------------------------------------------------");

        for (City city : cities) {
            Country country = app.getCountryByCode(city.getCountryCode());
            String countryName = (country != null) ? country.getName() : "Unknown";
            System.out.printf("%-25s %-15s %-20s %-12d%n",
                    city.getName(), countryName, city.getDistrict(), city.getPopulation());
        }

        for (int i = 1; i < cities.size(); i++) {
            assertTrue(cities.get(i - 1).getPopulation() >= cities.get(i).getPopulation(),
                    "Cities are not sorted correctly by population");
        }
    }



    /**
     * Integration test for - All cities in a country ours being the UK, sorted by population
     */
    @Test
    public void testUKCitiesReport() {
        ArrayList<City> ukCities = app.getUKCitiesSortedByPopulation();

        assertNotNull(ukCities, "UK city list should not be null");

        System.out.println("Cities in the United Kingdom sorted from largest to smallest population:");
        System.out.printf("%-25s %-15s %-20s %-12s%n", "Name", "Country", "District", "Population");
        System.out.println("---------------------------------------------------------------");

        for (City city : ukCities) {
            Country country = app.getCountryByCode(city.getCountryCode());
            String countryName = (country != null) ? country.getName() : "Unknown";
            System.out.printf("%-25s %-15s %-20s %-12d%n",
                    city.getName(), countryName, city.getDistrict(), city.getPopulation());
        }
        for (int i = 1; i < ukCities.size(); i++) {
            assertTrue(ukCities.get(i - 1).getPopulation() >= ukCities.get(i).getPopulation(),
                    "Cities are not sorted correctly by population");
        }
    }

    /**
     * Integration test for - All cities in a district, ours being Kyoto sorted by population
     */
    @Test
    void testKyotoDistrictCitiesReport() {
        ArrayList<City> kyotoCities = app.getKyotoDistrictCitiesSortedByPopulation();

        assertNotNull(kyotoCities, "Kyoto city list should not be null");

        System.out.println("Cities in the Kyoto District sorted from largest to smallest population:");
        System.out.printf("%-25s %-15s %-20s %-12s%n", "Name", "Country", "District", "Population");
        System.out.println("---------------------------------------------------------------");

        for (City city : kyotoCities) {
            Country country = app.getCountryByCode(city.getCountryCode());
            String countryName = (country != null) ? country.getName() : "Unknown";

            System.out.printf("%-25s %-15s %-20s %-12d%n",
                    city.getName(), countryName, city.getDistrict(), city.getPopulation());
        }
        for (int i = 1; i < kyotoCities.size(); i++) {
            assertTrue(kyotoCities.get(i - 1).getPopulation() >= kyotoCities.get(i).getPopulation(),
                    "Cities are not sorted correctly by population");
        }
    }


    /**
     * Integration test for - All capitals cities in a region sorted by population
     */
    @Test
    void testCapitalCitiesReportByRegion() {
        ArrayList<City> capitalCities = app.getCapitalCitiesByRegion("British Islands");

        assertNotNull(capitalCities, "Capital cities list should not be null");
        assertFalse(capitalCities.isEmpty(), "Capital cities list should not be empty");

        System.out.println("Capital Cities in Region 'British Islands' sorted from largest to smallest population:");
        System.out.printf("%-25s %-15s %-12s%n", "Name", "Country", "Population");
        System.out.println("----------------------------------------------");

        for (City city : capitalCities) {
            Country country = app.getCountryByCode(city.getCountryCode());
            String countryName = (country != null) ? country.getName() : "Unknown";

            System.out.printf("%-25s %-15s %-12d%n",
                    city.getName(), countryName, city.getPopulation());
        }

        for (int i = 1; i < capitalCities.size(); i++) {
            assertTrue(capitalCities.get(i - 1).getPopulation() >= capitalCities.get(i).getPopulation(),
                    "Capital cities are not sorted correctly by population");
        }
    }



    /**
     * Integration test for - All capitals in a continent sorted by population
     */
    @Test
    void testCapitalCitiesReportByContinent() {
        App app = new App();
        app.connect("localhost:33060", 0);

        ArrayList<City> capitalCities = app.getCapitalCitiesByContinent("Europe");

        assertNotNull(capitalCities, "Capital cities list should not be null");
        assertFalse(capitalCities.isEmpty(), "Capital cities list should not be empty");

        System.out.println("Capital Cities in Continent 'Europe' sorted from largest to smallest population:");
        System.out.printf("%-25s %-15s %-12s%n", "Name", "Country", "Population");
        System.out.println("----------------------------------------------");
        for (City city : capitalCities) {
            Country country = app.getCountryByCode(city.getCountryCode());
            String countryName = (country != null) ? country.getName() : "Unknown";

            System.out.printf("%-25s %-15s %-12d%n",
                    city.getName(), countryName, city.getPopulation());
        }

        for (int i = 1; i < capitalCities.size(); i++) {
            assertTrue(capitalCities.get(i - 1).getPopulation() >= capitalCities.get(i).getPopulation(),
                    "Capital cities are not sorted correctly by population");
        }
    }


    /**
     * Integration test for - All capital cities in the world sorted by population
     */
    @Test
    void testAllCapitalCitiesSortedByPopulation() {
        ArrayList<City> capitalCities = app.getAllCapitalCitiesSortedByPopulation();

        assertNotNull(capitalCities, "Capital cities list should not be null");
        assertFalse(capitalCities.isEmpty(), "Capital cities list should not be empty");

        System.out.println("All Capital Cities Sorted by Population (Greatest to Lowest):");
        System.out.printf("%-25s %-15s %-12s%n", "Name", "Country", "Population");
        System.out.println("----------------------------------------------");

        for (City city : capitalCities) {
            Country country = app.getCountryByCode(city.getCountryCode());
            String countryName = (country != null) ? country.getName() : "Unknown";
            System.out.printf("%-25s %-15s %-12d%n", city.getName(), countryName, city.getPopulation());
        }

        for (int i = 1; i < capitalCities.size(); i++) {
            assertTrue(capitalCities.get(i - 1).getPopulation() >= capitalCities.get(i).getPopulation(),
                    "Capital cities are not sorted correctly by population");
        }
    }

}


