package TESTPACKAGE;

import TESTPACKAGE.App;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.IOException;

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

    /**
     * Integration test for - Total world population
     */
    @Test
    public void testPopulationOfTheWorld() {
        app.generateWorldPopulationReport();
        String reportFilePath = "./output/WorldPopulationReport.txt";
        StringBuilder reportContent = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(reportFilePath))) {
            String line;
            while ((line = reader.readLine()) !=null) {
                reportContent.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue(reportContent.toString().contains("Total World Population"));
    }
    /**
     * Integration test for - Total population of Europe
     */

    @Test
    public void testPopulationOfContinentEurope() {
        app.generateContinentPopulationReport("Europe");

        String reportFilePath = "./output/EuropePopulationReport.txt";
        StringBuilder reportContent = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(reportFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                reportContent.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertTrue(reportContent.toString().contains("Total Population of Europe:"),
                "The report does not contain the total population for Europe");
    }

    /**
     * Integration test for - Total population of Iran
     */
/*
    @Test
    public void testPopulationOfCountry() {
        app.generateCountryPopulationReport("Iran");

        String reportFilePath = "./output/Iran_PopulationReport.txt";
        StringBuilder reportContent = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(reportFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                reportContent.append(line).append("\n");
            }
        } catch (IOException e) {
            fail("Failed to read the population report file: " + e.getMessage());
        }

        assertTrue(reportContent.toString().contains("Population of Iran:"),
                "The report does not contain the population for Iran");
    }
*/
    /**
     * Integration test for - Total population of region Baltic countries
     */
    @Test
    public void testPopulationOfRegion() {
        app.generateRegionPopulationReport("Baltic Countries");

        String reportFilePath = "./output/Baltic_CountriesPopulationReport.txt";
        StringBuilder reportContent = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(reportFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                reportContent.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue(reportContent.toString().contains("Total Population of Baltic Countries:"),
                "The report does not contain the population for Baltic Countries");
    }

    /**
     * Integration test for - Total population of distict British Colombia
     */
    @Test
    public void testPopulationOfDistrict() {
        app.generateDistrictPopulationReport("British Colombia");

        String reportFilePath = "./output/British_ColombiaPopulationReport.txt";
        StringBuilder reportContent = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(reportFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                reportContent.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue(reportContent.toString().contains("Total Population of British Colombia:"),
                "The report does not contain the population for British Colombia");
    }

    /**
     * Integration test for - Total population of a city Yerevan
     */
    @Test
    public void testPopulationOfCity() {
        // Generate the report for Yerevan
        app.generateCityPopulationReport("Yerevan");

        String reportFilePath = "./output/YerevanPopulationReport.txt";
        StringBuilder reportContent = new StringBuilder();

        // Read the content of the generated report
        try (BufferedReader reader = new BufferedReader(new FileReader(reportFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                reportContent.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue(reportContent.toString().contains("Population of Yerevan:"),
                "The report does not contain the population for Yerevan");
    }
    /**
     * Integration test for - Top N (5) city populations of Asia
     */
    @Test
    public void testTopNPopulatedCountriesInContinent() {
        String continent = "Asia";
        int n = 5;

        app.generateTopNPopulatedCountriesReport(continent, n);

        String reportFilePath = String.format("./output/Top%dPopulatedCountries_%s.txt", n, continent);
        StringBuilder reportContent = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(reportFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                reportContent.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            fail("Error reading the report file");
        }

        assertTrue(reportContent.toString().contains("Top 5 Most Populated Countries in Asia"),
                "The report does not contain the expected title.");
        assertTrue(reportContent.toString().split("\n").length > n,
                "The report does not list the expected number of countries.");
    }

    /**
     * Integration test for - Top N(3) countries in the region Caribbean
     */
    @Test
    public void testTopNPopulatedCountriesInRegion() {
        String region = "Caribbean";
        int n = 3;

        // Generate the report
        app.generateTopNPopulatedCountriesInRegion(region, n);

        // Validate the output
        String reportFilePath = String.format("./output/Top%dPopulatedCountries_%s.txt", n, region);
        StringBuilder reportContent = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(reportFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                reportContent.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            fail("Error reading the report file");
        }

        assertTrue(reportContent.toString().contains("Top 3 Most Populated Countries in Caribbean"),
                "The report does not contain the expected title.");
        assertTrue(reportContent.toString().contains("Total Population of Region:"),
                "The report does not contain the total population of the region.");
        assertTrue(reportContent.toString().contains("Total Population in Cities:"),
                "The report does not contain the total city population data.");
        assertTrue(reportContent.toString().contains("Total Population in Rural Areas:"),
                "The report does not contain the rural population data.");
        assertTrue(reportContent.toString().split("\n").length > n + 4,  // +4 for the headers and summary
                "The report does not list the expected number of countries and population data.");
    }


}


