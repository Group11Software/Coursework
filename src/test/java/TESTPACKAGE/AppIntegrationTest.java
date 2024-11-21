package TESTPACKAGE;

import TESTPACKAGE.App;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


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
        /** calling the method to generate the report **/
        app.generateNorthAmericaCitiesReport();
        /**  **/
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
    void CountryLargestToSmallest() {
        /** Fetch the list of countries **/
        ArrayList<Country> countries = app.report2();

        /** Ensure the list is not null **/
        assertNotNull(countries, "Country list should not be null");

        /** Sort the list by population from largest to smallest **/
        countries.sort((country1, country2) -> Long.compare(country2.getPopulation(), country1.getPopulation()));

        /** Print the sorted list for debugging **/
        System.out.println("Countries sorted from largest to smallest population:");
        for (Country country : countries) {
            System.out.println(country.getName() + ": " + country.getPopulation());
        }

        /** Validate the sorting order **/
        for (int i = 1; i < countries.size(); i++) {
            assertTrue(countries.get(i - 1).getPopulation() >= countries.get(i).getPopulation(),
                    "Countries are not sorted correctly by population");
        }
    }

    @Test
    void CountryLargestToSmallestAsia() {
        /** Fetch the list of countries **/
        ArrayList<Country> countries = app.report3();

        /** Ensure the list is not null **/
        assertNotNull(countries, "Country list should not be null");

        /** Sort the list by population, from largest to smallest **/
        countries.sort((country1, country2) -> Long.compare(country2.getPopulation(), country1.getPopulation()));

        /** Print the sorted list for debugging **/
        System.out.println("Countries sorted from largest to smallest population:");
        for (Country country : countries) {
            System.out.println(country.getName() + ": " + country.getPopulation());
        }

    }
    @Test
    void CountryLargestToSmallestRegions() {
        /** Fetch the list of countries **/
        ArrayList<Country> countries = app.report4();

        /** Ensure the list is not null **/
        assertNotNull(countries, "Country list should not be null");

        /** Sort the list by population, from largest to smallest **/
        countries.sort((country1, country2) -> Long.compare(country2.getPopulation(), country1.getPopulation()));

        /** Print the sorted list for debugging **/
        System.out.println("Countries sorted from largest to smallest population:");
        for (Country country : countries) {
            System.out.println(country.getName() + ": " + country.getPopulation());
        }
    }
    @Test
    void countryreport1(){
        /** Fetch the list of countries **/
        ArrayList<Country> countries = app.report5();
        /** Print the sorted list for debugging **/
        System.out.println("country report:");
        for (Country country : countries) {
            System.out.println(country.getCode() + ": " + country.getName() + ": " + country.getPopulation() + ": " + country.getContinent() + ": " + country.getRegion());
        }
    }
    @Test
    void cityreport2(){
        /** Fetch the list of countries **/
        ArrayList<City> cities = app.report6();
        /** Print the sorted list for debugging **/
        System.out.println("city report:");
        for (City city : cities) {
            System.out.println(city.getName() + ": " + city.getCountryCode() + ":" + city.getPopulation() + ": " + city.getDistrict() );
        }
    }
    @Test
    void capitalcityreport(){
        /** Fetch the list of countries **/
        ArrayList<Country> countries = app.report7();
        /** Print the sorted list for debugging **/
        System.out.println("capital city report:");
        for (Country country : countries) {
            System.out.println(country.getCapital() + ": " + country.getName() + ": " + country.getPopulation() );
        }

    }
    @Test
    void peopleinandoutofcitiesreport() {
        /** Fetch the list of countries and cities to make the report **/
        List<Object> result = app.report8();

        /** Extracting countries and cities from the result **/
        List<Country> countries = (List<Country>) result.get(0); /** Cast to List<Country> **/
        List<City> cities = (List<City>) result.get(1); /** Cast to List<City> **/

        System.out.println("People in and out of cities in each country:");

        /** Loop through each country **/
        for (Country country : countries) {
            /** Calculate city population for the current country **/
            long totalCityPopulation = 0;

            /** Loop through all cities and add their population if they belong to the current country **/
            for (City city : cities) {
                /** Check if the city's countryCode matches the country's code **/
                if (city.getCountryCode().equals(country.getCode())) {
                    totalCityPopulation += city.getPopulation();
                }
            }

            /** Calculate the rural population (outside of cities) **/
            long ruralPopulation = country.getPopulation() - totalCityPopulation;

            /** Print the results **/
            System.out.println("Country: " + country.getName());
            System.out.println("  Total Population: " + country.getPopulation());
            System.out.println("  People Living in Cities: " + totalCityPopulation);
            System.out.println("  People Not Living in Cities: " + ruralPopulation);
            System.out.println("---------------");
        }
    }
    @Test
    void PIAOOCIACContinentreport() {
        /** Call the report9 method and get the result **/
        List<Object> result = app.report9();

        /** Extract continents and their population data from the result **/
        List<String> continents = (List<String>) result.get(0);  /** List of continent names **/
        List<Map<String, Object>> populationData = (List<Map<String, Object>>) result.get(1); /** Population data **/

        System.out.println("People in and out of cities in each continent:");

        /** Loop through the population data and print statistics for each continent **/
        for (Map<String, Object> data : populationData) {
            String continent = (String) data.get("continent");
            long totalPopulation = (long) data.get("totalCountryPopulation");
            long cityPopulation = (long) data.get("totalCityPopulation");
            long ruralPopulation = (long) data.get("totalRuralPopulation");

            System.out.println("Continent: " + continent);
            System.out.println("  Total Population: " + totalPopulation);
            System.out.println("  People Living in Cities: " + cityPopulation);
            System.out.println("  People Not Living in Cities: " + ruralPopulation);
            System.out.println("---------------");
        }
    }
    @Test
    void CountryLargestToSmallestRegionswithn() {
        Scanner scanner = new Scanner(System.in);

        /** Ask the user how many top countries they want **/
        int n = 5;

        /** Fetch the list of top N countries **/
        ArrayList<Country> countries = app.report2withn(n);

        /** Ensure the list is not null **/
        assertNotNull(countries, "Country list should not be null");

        /** Print the sorted list for debugging **/
        System.out.println("Top " + n + " countries by population:");
        for (Country country : countries) {
            System.out.println(country.getName() + ": " + country.getPopulation());
        }
    }
    @Test
    void testGenerateLanguageReport() {
        try {
            // Call the generateLanguageReport method
            ArrayList<countrylanguage> languageReports = app.generateLanguageReport();

            // Ensure the list is not null or empty
            assertNotNull(languageReports, "Language reports should not be null");
            assertFalse(languageReports.isEmpty(), "Language reports should not be empty");

            // Print the reports for debugging (only language, count, and percentage)
            for (countrylanguage report : languageReports) {
                System.out.println("Language: " + report.getLanguage() +
                        ", Count: " + report.getCount() +
                        ", Percentage: " + String.format("%.2f", report.getPercentage()) + "%");
            }

            // Example assertion to verify at least one language report exists
            assertTrue(languageReports.size() > 0, "There should be at least one language report");

            // Additional assertions can verify specific values if expected results are known
            // Example: Check for Chinese language in the result
            assertTrue(languageReports.stream().anyMatch(report -> report.getLanguage().equals("Chinese")),
                    "The report should include data for Chinese language");

        } catch (Exception e) {
            fail("An exception occurred during the test: " + e.getMessage());
        }
    }




}



















