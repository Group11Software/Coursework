package TESTPACKAGE;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
public class App {

    /**
     * Connection to MySQL database.
     */
    private Connection con = null;
    public static void main(String[] args) {
        // Create new Application
        App a = new App();

        if (args.length < 1) {
            //local
            a.connect("localhost:33060", 0);
        } else {
            //docker parameters passed from Dockerfile
            a.connect(args[0], Integer.parseInt(args[1]));
        }
        a.printCityReport(a.getCities());
        a.report2();
        a.generateNorthAmericaCitiesReport();
        a.generateCapitalCitiesReportByRegion("British Islands");

        // Disconnect from database
        a.disconnect();
    }
    /**
     * This connects to the city.java and gets the cities from the world.sql
     */
    public ArrayList<City> getCities() {
        ArrayList<City> cities = new ArrayList<>();
        try {

            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String sql = "select * from city";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(sql);
            //cycle
            while (rset.next()) {
                Integer id = rset.getInt("ID");
                String name = rset.getString("Name");
                String countryCode = rset.getString("CountryCode");
                String district = rset.getString("District");
                Integer population = rset.getInt("Population");
                City city = new City(id, name, countryCode, district, population);
                cities.add(city);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
            return null;
        }
        return  cities;
    }
    /**
     *Get city method used to generate the highest to lowest population report
     */
    public City getCity(int id) {
        City city = null;
        try {

            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String sql = "select * from city where ID = " + id;
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(sql);
            //cycle
            if (rset.next()) {
                String name = rset.getString("Name");
                String countryCode = rset.getString("CountryCode");
                String district = rset.getString("District");
                Integer population = rset.getInt("Population");
                city = new City(id, name, countryCode, district, population);

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
            return null;
        }
        return  city;
    }
    /**
     * Example report received from devops3
     */

    public void report2() {
        StringBuilder sb = new StringBuilder();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String sql = "select * from country";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(sql);
            //cycle
            while (rset.next()) {
                String name = rset.getString("name");
                Integer population = rset.getInt("population");
                sb.append(name + "\t" + population + "\r\n");
            }
            System.out.println(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
            return;
        }
    }

    /**
     * Connects to the database
     */
    public void connect(String conString, int delay) {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                // Wait a bit for db to start
                Thread.sleep(delay);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://" + conString
                        + "/world?allowPublicKeyRetrieval=true&useSSL"
                        + "=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt "
                        + Integer.toString(i));
                System.out.println(sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }
    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect() {
        if (con != null) {
            try {
                // Close connection
                con.close();
            } catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }
    }
    /**
     * prints the city of all cities greatests to lowest
     */
    public void printCityReport(ArrayList<City> cities) {
        if (cities == null || cities.isEmpty()) {
            System.out.println("No cities found.");
            return;
        }

        // Sort cities in descending order of population
        cities.sort((city1, city2) -> Long.compare(city2.getPopulation(), city1.getPopulation()));

        // Print table header
        System.out.printf("%-20s %-20s %-20s %-15s%n", "Name", "Country Code", "District", "Population");
        System.out.println("-------------------------------------------------------------------------------");

        // Print city details in a formatted table
        for (City city : cities) {
            System.out.printf("%-20s %-20s %-20s %,15d%n",
                    city.getName(), city.getCountryCode(), city.getDistrict(), city.getPopulation());
        }
    }


    /**
     * All cities by continent generated
     */

    public Country getCountryByCode(String countryCode) {
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM country WHERE Code = '" + countryCode + "'";
            ResultSet rset = stmt.executeQuery(sql);
            if (rset.next()) {
                Country country = new Country();
                country.setCode(rset.getString("Code"));
                country.setName(rset.getString("Name"));
                country.setContinent(rset.getString("Continent"));
                country.setRegion(rset.getString("Region"));
                country.setSurfaceArea(rset.getDouble("SurfaceArea"));
                country.setIndepYear(rset.getLong("IndepYear"));
                country.setPopulation(rset.getLong("Population"));
                country.setLifeExpectancy(rset.getDouble("LifeExpectancy"));
                country.setGnp(rset.getDouble("GNP"));
                country.setGnpOld(rset.getDouble("GNPOld"));
                country.setLocalName(rset.getString("LocalName"));
                country.setGovernmentForm(rset.getString("GovernmentForm"));
                country.setHeadOfState(rset.getString("HeadOfState"));
                country.setCapital(rset.getLong("Capital"));
                country.setCode2(rset.getString("Code2"));
                return country;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
        }
        return null;
    }

    public ArrayList<City> getCitiesByContinent(String continent) {
        ArrayList<City> cities = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT city.* " +
                    "FROM city " +
                    "JOIN country ON city.CountryCode = country.Code " +
                    "WHERE country.Continent = '" + continent + "'";
            ResultSet rset = stmt.executeQuery(sql);
            while (rset.next()) {
                Integer id = rset.getInt("ID");
                String name = rset.getString("Name");
                String countryCode = rset.getString("CountryCode");
                String district = rset.getString("District");
                Integer population = rset.getInt("Population");
                City city = new City(id, name, countryCode, district, population);
                cities.add(city);
            }

            // Sort cities by population in descending order
            cities.sort((city1, city2) -> Long.compare(city2.getPopulation(), city1.getPopulation()));

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
            return null;
        }
        return cities;
    }


    /**
     * Getting all Asian cities population report from highest to lowest
     */
    public ArrayList<City> getAsianCitiesSortedByPopulation() {
        ArrayList<City> asianCities = getCitiesByContinent("Asia");
        if (asianCities != null) {
            asianCities.sort((city1, city2) -> Long.compare(city2.getPopulation(), city1.getPopulation()));
        }
        return asianCities;
    }


    /**
     * Method and Report on region North Americas cities greatest to lowest
     */
    public void generateNorthAmericaCitiesReport() {
        ArrayList<City> cities = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT city.* " +
                    "FROM city " +
                    "JOIN country ON city.CountryCode = country.Code " +
                    "WHERE country.Region = 'North America' " +
                    "ORDER BY city.Population DESC";
            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                Integer id = rset.getInt("ID");
                String name = rset.getString("Name");
                String countryCode = rset.getString("CountryCode");
                String district = rset.getString("District");
                Integer population = rset.getInt("Population");
                City city = new City(id, name, countryCode, district, population);
                cities.add(city);
            }

            System.out.println("Cities in North America sorted by population:");
            for (City city : cities) {
                System.out.println(city.getName() + " (" + city.getCountryCode() + "): " + city.getPopulation());
            }
        } catch (Exception e) {
            System.out.println("Error generating North America cities report: " + e.getMessage());
        }
    }


    /**
     * Method for sorting out UK cities from greatest to lowest
     */
    public ArrayList<City> getUKCitiesSortedByPopulation() {
        ArrayList<City> ukCities = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT city.* " +
                    "FROM city " +
                    "JOIN country ON city.CountryCode = country.Code " +
                    "WHERE country.Name = 'United Kingdom' " +
                    "ORDER BY city.Population DESC";
            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                Integer id = rset.getInt("ID");
                String name = rset.getString("Name");
                String countryCode = rset.getString("CountryCode");
                String district = rset.getString("District");
                Integer population = rset.getInt("Population");
                City city = new City(id, name, countryCode, district, population);
                ukCities.add(city);
            }
        } catch (Exception e) {
            System.out.println("Error fetching UK cities: " + e.getMessage());
        }
        return ukCities;
    }

    /**
     * Report for generating Uk cities greatest to lowest
     */

    public void generateUKCitiesReport() {
        ArrayList<City> ukCities = getUKCitiesSortedByPopulation();
        if (ukCities == null || ukCities.isEmpty()) {
            System.out.println("No cities found for the United Kingdom.");
            return;
        }

        System.out.println("United Kingdom Cities Sorted by Population (Greatest to Lowest):");
        for (City city : ukCities) {
            System.out.println(city.getName() + ": " + city.getPopulation());
        }
    }


    /**
     * Method for getting the population of cities in the kyoto district
     */
    public ArrayList<City> getKyotoDistrictCitiesSortedByPopulation() {
        ArrayList<City> kyotoCities = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM city " +
                    "WHERE District = 'Kyoto' " +
                    "ORDER BY Population DESC";
            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                Integer id = rset.getInt("ID");
                String name = rset.getString("Name");
                String countryCode = rset.getString("CountryCode");
                String district = rset.getString("District");
                Integer population = rset.getInt("Population");
                City city = new City(id, name, countryCode, district, population);
                kyotoCities.add(city);
            }
        } catch (Exception e) {
            System.out.println("Error fetching cities in Kyoto district: " + e.getMessage());
        }
        return kyotoCities;
    }


    /**
     * Report for kyoto district
     */
    public void generateKyotoDistrictCitiesReport() {
        ArrayList<City> kyotoCities = getKyotoDistrictCitiesSortedByPopulation();
        if (kyotoCities == null || kyotoCities.isEmpty()) {
            System.out.println("No cities found in Kyoto district.");
            return;
        }

        System.out.println("Cities in Kyoto District Sorted by Population (Greatest to Lowest):");
        for (City city : kyotoCities) {
            System.out.println(city.getName() + ": " + city.getPopulation());
        }
    }


    /**
     * Method for getting the capital cities by region
     */
    public ArrayList<City> getCapitalCitiesByRegion(String region) {
        ArrayList<City> capitalCities = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT city.ID, city.Name, city.Population, country.Code as CountryCode, country.Name as CountryName " +
                    "FROM country " +
                    "JOIN city ON country.Capital = city.ID " +
                    "WHERE country.Region = '" + region + "' " +
                    "ORDER BY city.Population DESC";
            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                long id = rset.getLong("ID");
                String cityName = rset.getString("Name");
                String countryCode = rset.getString("CountryCode");
                String countryName = rset.getString("CountryName");  // Fetch the country name
                long population = rset.getLong("Population");
                City city = new City(id, cityName, countryCode, countryName,  population); // Added country name to constructor
                capitalCities.add(city);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching capital cities by region: " + e.getMessage());
        }
        return capitalCities;
    }


    /**
     * Report for capital cities in a region, ours being British Islands
     */
    public void generateCapitalCitiesReportByRegion(String region) {
        ArrayList<City> capitalCities = getCapitalCitiesByRegion(region);
        if (capitalCities == null || capitalCities.isEmpty()) {
            System.out.println("No capital cities found for region: " + region);
            return;
        }

        System.out.println("Capital Cities in Region '" + region + "' Sorted by Population (Greatest to Lowest):");
        for (City city : capitalCities) {
            System.out.println(city.getName() + ": " + city.getPopulation());
        }
    }

    /**
     * Capital cities by continent being Europe in test case
     */
    public ArrayList<City> getCapitalCitiesByContinent(String continent) {
        ArrayList<City> capitalCities = new ArrayList<>();
        try {
            // SQL query to fetch capital cities by continent, sorted by population
            Statement stmt = con.createStatement();
            String sql = "SELECT city.ID, city.Name, city.Population, country.Code " +
                    "FROM country " +
                    "JOIN city ON country.Capital = city.ID " +
                    "WHERE country.Continent = ? " +
                    "ORDER BY city.Population DESC";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, continent);  // Set continent parameter

            ResultSet rset = ps.executeQuery();

            while (rset.next()) {
                int id = rset.getInt("ID");
                String name = rset.getString("Name");
                int population = rset.getInt("Population");
                String countryCode = rset.getString("Code");
                City city = new City(id, name, countryCode, null, population); // Set the CountryCode
                capitalCities.add(city);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching capital cities by continent: " + e.getMessage());
        }
        return capitalCities;
    }

    /**
     * Report for capital cities highest to lowest on a continent
     */
    public void generateCapitalCitiesReportByContinent(String continent) {
        ArrayList<City> capitalCities = getCapitalCitiesByContinent(continent);
        if (capitalCities == null || capitalCities.isEmpty()) {
            System.out.println("No capital cities found for continent: " + continent);
            return;
        }

        System.out.println("Capital Cities in Continent '" + continent + "' Sorted by Population (Greatest to Lowest):");
        for (City city : capitalCities) {
            System.out.println(city.getName() + ": " + city.getPopulation());
        }
    }

    /**
     * Java code used for making the all the capital cities in the world sorted by population
     */
    public ArrayList<City> getAllCapitalCitiesSortedByPopulation() {
        ArrayList<City> capitalCities = new ArrayList<>();
        try {
            // SQL query to fetch all capital cities sorted by population, including countryCode
            Statement stmt = con.createStatement();
            String sql = "SELECT city.ID, city.Name, city.Population, country.Code " +
                    "FROM country " +
                    "JOIN city ON country.Capital = city.ID " +
                    "ORDER BY city.Population DESC";
            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                int id = rset.getInt("ID");
                String name = rset.getString("Name");
                int population = rset.getInt("Population");
                String countryCode = rset.getString("Code");  // Retrieve the country code
                City city = new City(id, name, countryCode, null, population); // Set countryCode
                capitalCities.add(city);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching all capital cities: " + e.getMessage());
        }
        return capitalCities;
    }


    /**
     * Report for generating all capital cities in the world
     */
    public void generateAllCapitalCitiesReport() {
        ArrayList<City> capitalCities = getAllCapitalCitiesSortedByPopulation();
        if (capitalCities == null || capitalCities.isEmpty()) {
            System.out.println("No capital cities found.");
            return;
        }

        System.out.println("All Capital Cities Sorted by Population (Greatest to Lowest):");
        for (City city : capitalCities) {
            System.out.println(city.getName() + ": " + city.getPopulation());
        }
    }

    public void generateWorldPopulationReport() {
        long totalPopulation = 0;  // Initialize total population as 0
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT population FROM country";  // Query to get the population of all countries
            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                totalPopulation += rset.getLong("population");  // Add the population of each country
            }

            // Create the report
            StringBuilder report = new StringBuilder();
            report.append("Total World Population: ").append(totalPopulation).append("\n");

            // Create the output folder if it doesn't exist
            new File("./output/").mkdir();

            // Write the report to a file
            BufferedWriter writer = new BufferedWriter(new FileWriter("./output/WorldPopulationReport.txt"));
            writer.write(report.toString());
            writer.close();

            // Print the report content
            System.out.println(report.toString());
        } catch (Exception e) {
            System.out.println("Error generating World population report: " + e.getMessage());
        }
    }
    public void generateContinentPopulationReport(String continent) {
        long totalPopulation = 0;  // Initialize total population as 0

        try {
            Statement stmt = con.createStatement();

            // Update the SQL query to select countries from the specific continent
            String sql = "SELECT population FROM country WHERE continent = '" + continent + "'";  // Filter by continent
            ResultSet rset = stmt.executeQuery(sql);

            // Sum up the population of countries in the specified continent
            while (rset.next()) {
                totalPopulation += rset.getLong("population");  // Add the population of each country
            }

            // Create the report content
            StringBuilder report = new StringBuilder();
            report.append("Total Population of ").append(continent).append(": ").append(totalPopulation).append("\n");

            // Ensure the output folder exists
            new File("./output/").mkdir();

            // Write the report to a file
            BufferedWriter writer = new BufferedWriter(new FileWriter("./output/" + continent + "PopulationReport.txt"));
            writer.write(report.toString());
            writer.close();

            // Print the report content
            System.out.println(report.toString());

        } catch (Exception e) {
            System.out.println("Error generating " + continent + " population report: " + e.getMessage());
        }
    }

    public void generateCountryPopulationReport(String countryName) {
        long population = 0;

        try {
            Statement stmt = con.createStatement();

            // SQL query to fetch the population of a specific country
            String sql = "SELECT population FROM country WHERE name = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, countryName);
            ResultSet rset = pstmt.executeQuery();

            if (rset.next()) {
                population = rset.getLong("population");
            } else {
                System.out.println("Country " + countryName + " not found.");
                return;
            }

            String report = "Population of " + countryName + ": " + population + "\n";

            // Ensure the output folder exists
            File outputDir = new File("./output/");
            if (!outputDir.exists()) {
                outputDir.mkdir();
            }

            // Write the report to a file
            String reportFileName = "./output/" + countryName.replace(" ", "_") + "PopulationReport.txt";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(reportFileName))) {
                writer.write(report);
            }

            // Print the report content to the console
            System.out.println(report);

        } catch (SQLException e) {
            System.out.println("SQL error while generating country population report for " + countryName + ": " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O error while generating country population report for " + countryName + ": " + e.getMessage());
        }
    }

    public void generateRegionPopulationReport(String region) {
        long totalPopulation = 0;

        try {
            Statement stmt = con.createStatement();

            // SQL query to fetch the total population for a specific region
            String sql = "SELECT population FROM country WHERE region = '" + region + "'";
            ResultSet rset = stmt.executeQuery(sql);

            // Sum up the population of countries in the specified region
            while (rset.next()) {
                totalPopulation += rset.getLong("population");
            }

            // Create the report content
            StringBuilder report = new StringBuilder();
            report.append("Total Population of ").append(region).append(": ").append(totalPopulation).append("\n");

            // Ensure the output folder exists
            new File("./output/").mkdir();

            // Write the report to a file
            BufferedWriter writer = new BufferedWriter(new FileWriter("./output/" + region.replace(" ", "_") + "PopulationReport.txt"));
            writer.write(report.toString());
            writer.close();

            // Print the report content to console
            System.out.println(report.toString());

        } catch (Exception e) {
            System.out.println("Error generating population report for " + region + ": " + e.getMessage());
        }
    }

    public void generateDistrictPopulationReport(String districtName) {
        long totalPopulation = 0;

        try {
            Statement stmt = con.createStatement();

            // SQL query to fetch the total population for a specific district
            String sql = "SELECT population FROM city WHERE district = '" + districtName + "'";
            ResultSet rset = stmt.executeQuery(sql);

            // Sum up the population of cities in the specified district
            while (rset.next()) {
                totalPopulation += rset.getLong("population");
            }

            // Create the report content
            StringBuilder report = new StringBuilder();
            report.append("Total Population of ").append(districtName).append(": ").append(totalPopulation).append("\n");

            // Ensure the output folder exists
            new File("./output/").mkdir();

            // Write the report to a file
            BufferedWriter writer = new BufferedWriter(new FileWriter("./output/" + districtName.replace(" ", "_") + "PopulationReport.txt"));
            writer.write(report.toString());
            writer.close();

            // Print the report content to console
            System.out.println(report.toString());

        } catch (Exception e) {
            System.out.println("Error generating population report for district " + districtName + ": " + e.getMessage());
        }
    }

    public void generateCityPopulationReport(String cityName) {
        long population = 0;

        try {
            Statement stmt = con.createStatement();

            // SQL query to fetch the population of a specific city
            String sql = "SELECT population FROM city WHERE name = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, cityName);
            ResultSet rset = pstmt.executeQuery();

            if (rset.next()) {
                population = rset.getLong("population");
            } else {
                System.out.println("City " + cityName + " not found.");
                return;
            }

            String report = "Population of " + cityName + ": " + population + "\n";

            // Ensure the output folder exists
            File outputDir = new File("./output/");
            if (!outputDir.exists()) {
                outputDir.mkdir();
            }

            // Write the report to a file
            String reportFileName = "./output/" + cityName.replace(" ", "_") + "PopulationReport.txt";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(reportFileName))) {
                writer.write(report);
            }

            // Print the report content to the console
            System.out.println(report);

        } catch (SQLException e) {
            System.out.println("SQL error while generating city population report for " + cityName + ": " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O error while generating city population report for " + cityName + ": " + e.getMessage());
        }
    }

    public void generateTopNPopulatedCountriesReport(String continent, int n) {
        ArrayList<Country> countries = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            String sql = String.format(
                    "SELECT Name, Population, " +
                            "(SELECT SUM(Population) FROM city WHERE CountryCode = country.Code) AS PopulationInCities " +
                            "FROM country " +
                            "WHERE Continent = '%s' " +
                            "ORDER BY Population DESC LIMIT %d",
                    continent, n
            );
            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                String name = rset.getString("Name");
                long totalPopulation = rset.getLong("Population");
                long cityPopulation = rset.getLong("PopulationInCities");
                long ruralPopulation = totalPopulation - cityPopulation;
                double cityPopulationPercentage = (cityPopulation * 100.0) / totalPopulation;
                double ruralPopulationPercentage = (ruralPopulation * 100.0) / totalPopulation;

                Country country = new Country();
                country.setName(name);
                country.setPopulation(totalPopulation);
                countries.add(country);

                // Print or save the details
                System.out.printf(
                        "Country: %s, Total: %d, City: %d (%.2f%%), Rural: %d (%.2f%%)\n",
                        name, totalPopulation, cityPopulation, cityPopulationPercentage,
                        ruralPopulation, ruralPopulationPercentage
                );
            }

            // Save report to a file
            StringBuilder report = new StringBuilder();
            report.append(String.format("Top %d Most Populated Countries in %s:\n", n, continent));
            for (Country country : countries) {
                report.append(String.format(
                        "Country: %s, Total: %d\n",
                        country.getName(), country.getPopulation()
                ));
            }
            new File("./output/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter("./output/Top" + n + "PopulatedCountries_" + continent + ".txt"));
            writer.write(report.toString());
            writer.close();

        } catch (Exception e) {
            System.out.println("Error generating top N populated countries report: " + e.getMessage());
        }
    }

    public void generateTopNPopulatedCountriesInRegion(String region, int n) {
        ArrayList<Country> countries = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            String sql = String.format(
                    "SELECT Name, Population, " +
                            "(SELECT SUM(Population) FROM city WHERE CountryCode = country.Code) AS PopulationInCities " +
                            "FROM country " +
                            "WHERE Region = '%s' " +
                            "ORDER BY Population DESC LIMIT %d",
                    region, n
            );
            ResultSet rset = stmt.executeQuery(sql);

            long totalRegionPopulation = 0;
            long totalCityPopulation = 0;

            // First pass to gather data for the top N countries
            while (rset.next()) {
                String name = rset.getString("Name");
                long totalPopulation = rset.getLong("Population");
                long cityPopulation = rset.getLong("PopulationInCities");
                long ruralPopulation = totalPopulation - cityPopulation;
                double cityPopulationPercentage = (cityPopulation * 100.0) / totalPopulation;
                double ruralPopulationPercentage = (ruralPopulation * 100.0) / totalPopulation;

                Country country = new Country();
                country.setName(name);
                country.setPopulation(totalPopulation);
                countries.add(country);

                // Update region-wide population totals
                totalRegionPopulation += totalPopulation;
                totalCityPopulation += cityPopulation;

                // Print details for each country
                System.out.printf("Country: %s, Total Population: %d, City Population: %d (%.2f%%), Rural Population: %d (%.2f%%)\n",
                        name, totalPopulation, cityPopulation, cityPopulationPercentage,
                        ruralPopulation, ruralPopulationPercentage
                );
            }

            // Calculate region-wide population percentages
            long ruralRegionPopulation = totalRegionPopulation - totalCityPopulation;
            double cityRegionPercentage = (totalCityPopulation * 100.0) / totalRegionPopulation;
            double ruralRegionPercentage = (ruralRegionPopulation * 100.0) / totalRegionPopulation;

            // Save report to a file
            StringBuilder report = new StringBuilder();
            report.append(String.format("Top %d Most Populated Countries in %s:\n", n, region));
            report.append(String.format("Total Population of Region: %d\n", totalRegionPopulation));
            report.append(String.format("Total Population in Cities: %d (%.2f%%)\n", totalCityPopulation, cityRegionPercentage));
            report.append(String.format("Total Population in Rural Areas: %d (%.2f%%)\n", ruralRegionPopulation, ruralRegionPercentage));
            report.append("\n");

            for (Country country : countries) {
                report.append(String.format("Country: %s, Total Population: %d\n", country.getName(), country.getPopulation()));
            }

            new File("./output/").mkdir();
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter("./output/Top" + n + "PopulatedCountries_" + region + ".txt"));
            writer.write(report.toString());
            writer.close();

        } catch (Exception e) {
            System.out.println("Error generating top N populated countries report: " + e.getMessage());
        }
    }



}
