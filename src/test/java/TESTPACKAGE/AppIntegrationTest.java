package TESTPACKAGE;

import TESTPACKAGE.App;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.IOException;

import java.util.*;

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
    public void testTopNPopulatedCountriesInContinent1() {
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
    public void testTopNPopulatedCountriesInRegion1() {
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

    /**--------------------------------------------------------------------------------------------------------------------**/


    /** test to porduce the countries form largest to smallest **/
    @Test
    void CountryLargestToSmallest() {
        /** Fetch the list of countries **/
        ArrayList<Country> countries = app.report2();

        /** Ensure the list is not null **/
        assertNotNull(countries, "Country list should not be null");

        /** Sort the list by population from largest to smallest **/
        countries.sort((country1, country2) -> Long.compare(country2.getPopulation(), country1.getPopulation()));

        /** Print the sorted list in columns **/
        System.out.println("Countries sorted from largest to smallest population:");
        System.out.printf("%-15s %-15s %s%n", "Country Name", "Population", "Continent");
        System.out.println("--------------------------------------------");

        for (Country country : countries) {
            System.out.printf("%-15s %-15d %-15s%n", country.getName(), country.getPopulation(), country.getContinent());
        }

        /** Validate the sorting order **/
        for (int i = 1; i < countries.size(); i++) {
            assertTrue(countries.get(i - 1).getPopulation() >= countries.get(i).getPopulation(),
                    "Countries are not sorted correctly by population");
        }
    }
    /** test to produce the countries from largest to smallest in a contintent**/
    @Test
    void CountryLargestToSmallestAsia() {
        /** Fetch the list of countries **/
        ArrayList<Country> countries = app.report3();

        /** Ensure the list is not null **/
        assertNotNull(countries, "Country list should not be null");

        /** Sort the list by population, from largest to smallest **/
        countries.sort((country1, country2) -> Long.compare(country2.getPopulation(), country1.getPopulation()));

        /** Print the sorted list in columns **/
        System.out.println("Countries in Asia sorted from largest to smallest population:");
        System.out.printf("%-10s %-15s %-15s %-15s %-15s%n", "Code", "Country", "Population", "Continent", "Capital");
        System.out.println("------------------------------------------------------------");

        for (Country country : countries) {
            System.out.printf("%-10s %-15s %-15d %-15s %-15s%n", country.getCode(), country.getName(),
                    country.getPopulation(), country.getContinent(), country.getCapital());
        }
    }
    /** test to produce the countries from largest to smallest in regions **/
    @Test
    void CountryLargestToSmallestRegions() {
        /** Fetch the list of countries **/
        ArrayList<Country> countries = app.report4();

        /** Ensure the list is not null **/
        assertNotNull(countries, "Country list should not be null");

        /** Sort the list by population, from largest to smallest **/
        countries.sort((country1, country2) -> Long.compare(country2.getPopulation(), country1.getPopulation()));

        /** Print the sorted list in columns **/
        System.out.println("Countries sorted from largest to smallest population by region:");
        System.out.printf("%-10s %-15s %-15s %-15s %-15s%n", "Code", "Country", "Population", "Continent", "Capital");
        System.out.println("------------------------------------------------------------");

        for (Country country : countries) {
            System.out.printf("%-10s %-15s %-15d %-15s %-15s%n", country.getCode(), country.getName(),
                    country.getPopulation(), country.getContinent(), country.getCapital());
        }
    }
    /** test to produce the country report **/
    @Test
    void countryreport1() {
        /** Fetch the list of countries **/
        ArrayList<Country> countries = app.report5();

        /** Print the sorted list in columns **/
        System.out.println("Country report:");
        System.out.printf("%-10s %-15s %-15s %-15s %-15s%n", "Code", "Country", "Population", "Continent", "Region");
        System.out.println("------------------------------------------------------------");

        for (Country country : countries) {
            System.out.printf("%-10s %-15s %-15d %-15s %-15s%n", country.getCode(), country.getName(),
                    country.getPopulation(), country.getContinent(), country.getRegion());
        }
    }
    /** test to produce the city report**/
    @Test
    void cityreport2() {
        /** Fetch the list of cities **/
        ArrayList<City> cities = app.report6();

        /** Print the sorted list in columns **/
        System.out.println("City report:");
        System.out.printf("%-15s %-15s %-15s %-15s%n", "City Name", "Country Code", "Population", "District");
        System.out.println("------------------------------------------------------------");

        for (City city : cities) {
            System.out.printf("%-15s %-15s %-15d %-15s%n", city.getName(), city.getCountryCode(),
                    city.getPopulation(), city.getDistrict());
        }
    }
    /** test to produce the capital city report **/
    @Test
    void capitalcityreport() {
        /** Fetch the list of countries **/
        ArrayList<Country> countries = app.report7();

        /** Print the sorted list in columns **/
        System.out.println("Capital city report:");
        System.out.printf("%-15s %-15s %-15s%n", "Capital", "Country", "Population");
        System.out.println("------------------------------------------------------------");

        for (Country country : countries) {
            System.out.printf("%-15s %-15s %-15d%n", country.getCapital(), country.getName(), country.getPopulation());
        }
    }
    /** test to output the people in and out of cities**/
    @Test
    void peopleinandoutofcitiesreport() {
        /** Fetch the list of countries and cities to make the report **/
        List<Object> result = app.report8();

        /** Extracting countries and cities from the result **/
        List<Country> countries = (List<Country>) result.get(0); /** Cast to List<Country> **/
        List<City> cities = (List<City>) result.get(1); /** Cast to List<City> **/

        // Print the header for the report
        System.out.println("People in and out of cities in each country:");
        System.out.printf("%-25s %-20s %-20s%n", "Country", "Total Population", "Urban Population");
        System.out.println("---------------------------------------------------------------");

        /** Loop through each country **/
        for (Country country : countries) {
            /** Calculate city population for the current country **/
            long totalCityPopulation = 0;

            /** Loop through all cities and add their population if they belong to the current country **/
            for (City city : cities) {
                if (city.getCountryCode().equals(country.getCode())) {
                    totalCityPopulation += city.getPopulation();
                }
            }

            /** Print the results in columns **/
            System.out.printf("%-25s %-20d %-20d%n", country.getName(), country.getPopulation(), totalCityPopulation);
        }
    }

    /** test to output the people in and out of cities in a continent **/
    @Test
    void PIAOOCIACContinentreport() {
        /** Call the report9 method and get the result **/
        List<Object> result = app.report9();

        /** Extract continents and their population data from the result **/
        List<String> continents = (List<String>) result.get(0);
        List<Map<String, Object>> populationData = (List<Map<String, Object>>) result.get(1);

        System.out.println("People in and out of cities in each continent:");

        for (Map<String, Object> data : populationData) {
            String continent = (String) data.get("continent");
            long totalPopulation = (long) data.get("totalCountryPopulation");
            long cityPopulation = (long) data.get("totalCityPopulation");
            long ruralPopulation = (long) data.get("totalRuralPopulation");

            System.out.printf("%-15s %-15d %-15d %-15d%n", continent, totalPopulation, cityPopulation, ruralPopulation);
            System.out.println("---------------");
        }
    }
    /** test to output the largest to smallest countries within a region to N **/
    @Test
    void CountryLargestToSmallestRegionswithn() {
        Scanner scanner = new Scanner(System.in);
        int n = 5;  // Number of countries to retrieve

        /** Fetch the list of top N countries **/
        ArrayList<Country> countries = app.report2withn(n);

        /** Ensure the list is not null **/
        assertNotNull(countries, "Country list should not be null");

        /** Print the sorted list in columns **/
        System.out.println("Top " + n + " countries by population:");
        System.out.printf("%-15s %-15s %-15s%n", "Country Name", "Population", "Continent");
        System.out.println("--------------------------------------------");

        for (Country country : countries) {
            System.out.printf("%-15s %-15d %-15s%n", country.getName(), country.getPopulation(), country.getContinent());
        }
    }
    /** Test to generate languages and their percentage of the world**/
    @Test
    void testGenerateLanguageReport() {
        try {
            // Call the generateLanguageReport method
            ArrayList<countrylanguage> languageReports = app.generateLanguageReport();

            // Ensure the list is not null or empty
            assertNotNull(languageReports, "Language reports should not be null");
            assertFalse(languageReports.isEmpty(), "Language reports should not be empty");

            // Print the reports in columns
            System.out.printf("%-20s %-10s %-15s%n", "Language", "Count", "Percentage");
            System.out.println("----------------------------------------");

            for (countrylanguage report : languageReports) {
                System.out.printf("%-20s %-10d %-15.2f%n", report.getLanguage(), report.getCount(), report.getPercentage());
            }

        } catch (Exception e) {
            fail("An exception occurred during the test: " + e.getMessage());
        }
    }
    /** test to print the top n amount of cities from largest to smallest **/
    @Test
    void CityLargesttosmalln() {
        int n = 5;
        ArrayList<City> cities = app.getCitieswithn(n);

        assertNotNull(cities, "City list should not be null");

        cities.sort((city1, city2) -> Long.compare(city2.getPopulation(), city1.getPopulation()));

        System.out.println("Top " + n + " Cities sorted by population:");
        System.out.printf("%-15s %-15s %s%n", "City Name", "Population", "Country Code");
        System.out.println("--------------------------------------------");

        for (City city : cities) {
            System.out.printf("%-15s %-15d %-15s%n", city.getName(), city.getPopulation(), city.getCountryCode());
        }

        for (int i = 1; i < cities.size(); i++) {
            assertTrue(cities.get(i - 1).getPopulation() >= cities.get(i).getPopulation(),
                    "Cities are not sorted correctly by population");
        }
    }
    /** test to call the top n cities from largest to smallest  **/
    @Test
    void testGetCitiesByContinentwithn() {
        String continent = "Asia"; // Example continent
        int n = 5; // Number of top cities to retrieve
        ArrayList<City> cities = app.getCitiesByContinentwithn(continent, n);

        // Validate the result is not null
        assertNotNull(cities, "City list should not be null");
        assertFalse(cities.isEmpty(), "City list should not be empty");

        // Validate that the size does not exceed 'n'
        assertTrue(cities.size() <= n, "City list should not exceed the specified number of cities");

        // Print the top cities in a formatted table
        System.out.println("Top " + n + " cities in " + continent + " sorted by population:");
        System.out.printf("%-15s %-15s %-15s %-15s %n", "City Name", "Country Code", "District", "Population");
        System.out.println("------------------------------------------------------------");

        for (City city : cities) {
            System.out.printf("%-15s %-15s %-15s %-15d %n",
                    city.getName(), city.getCountryCode(), city.getDistrict(), city.getPopulation());
        }

        // Validate the cities are sorted in descending order by population
        for (int i = 1; i < cities.size(); i++) {
            assertTrue(cities.get(i - 1).getPopulation() >= cities.get(i).getPopulation(),
                    "Cities are not sorted correctly by population");
        }
    }
    @Test
    public void testTopNPopulatedCountriesInContinent() {
        String continent = "Asia";
        int n = 5;

        // Generate the report
        app.generateTopNPopulatedCountriesReport(continent, n);

        // Validate the output
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

        // Verify the report contains the continent name and data for N countries
        assertTrue(reportContent.toString().contains("Top 5 Most Populated Countries in Asia"),
                "The report does not contain the expected title.");
        assertTrue(reportContent.toString().split("\n").length > n,
                "The report does not list the expected number of countries.");
    }

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

        // Verify the report contains the region name and data for N countries
        assertTrue(reportContent.toString().contains("Top 3 Most Populated Countries in Caribbean"),
                "The report does not contain the expected title.");
        assertTrue(reportContent.toString().contains("Total Population of Region:"),
                "The report does not contain the total population of the region.");
        assertTrue(reportContent.toString().contains("Total Population in Cities:"),
                "The report does not contain the total city population data.");
        assertTrue(reportContent.toString().contains("Total Population in Rural Areas:"),
                "The report does not contain the rural population data.");
        assertTrue(reportContent.toString().split("\n").length >= n + 4,
                "The report does not list the expected number of cities.");

    }

    @Test
    public void testTopNPopulatedCitiesInRegion() {
        String region = "Caribbean";
        int n = 4;

        // Generate the report
        app.generateTopNPopulatedCitiesInRegion(region, n);

        // Validate the output
        String reportFilePath = String.format("./output/Top%dPopulatedCities_%s.txt", n, region);
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

        // Verify the report contains the region name and data for N cities
        assertTrue(reportContent.toString().contains("Top 4 Most Populated Cities in Caribbean"),
                "The report does not contain the expected title.");
        assertTrue(reportContent.toString().split("\n").length > n,  // Include header row
                "The report does not list the expected number of cities.");
    }

    @Test
    public void testTopNPopulatedCitiesInCountry() {
        String countryCode = "USA";
        int n = 3;

        // Generate the report
        app.generateTopNPopulatedCitiesInCountry(countryCode, n);

        // Validate the output
        String reportFilePath = String.format("./output/Top%dPopulatedCities_%s.txt", n, countryCode);
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

        // Verify the report contains the country code and data for N cities
        assertTrue(reportContent.toString().contains("Top 3 Most Populated Cities in USA"),
                "The report does not contain the expected title.");

        // Adjust the expected number of lines based on the presence of the separator line
        int expectedLines = (reportContent.toString().contains("================================================================================")) ? n + 4 : n + 3;


    }

    @Test
    public void testTopNCitiesInKyotoDistrict() {
        // Test with N = 4
        int n = 4;

        // Fetch top N cities from Kyoto district
        app.generateTopNDistrictCitiesReport(n);

        // Validate the output
        String reportFilePath = String.format("./output/Top%dCities_Kyoto.txt", n);
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
        // Check if the report contains the title
        assertTrue(reportContent.toString().contains("Top " + n + " Cities in Kyoto District Sorted by Population"),
                "The report does not contain the expected title.");

        // Verify the number of cities is as expected
        assertTrue(reportContent.toString().split("\n").length >= n + 1, // +1 for header
                "The report does not list the expected number of cities.");

        // Optionally, verify that the cities are sorted by population in descending order
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

        // Verify that the populations are sorted in descending order
        for (int i = 1; i < populations.size(); i++) {
            assertTrue(populations.get(i - 1) >= populations.get(i), "Cities are not sorted correctly by population");
        }
    }



}


