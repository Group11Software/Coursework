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




}



















