package TESTPACKAGE;

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
        if (cities == null) {
            System.out.println("No cities found");
            return;
        }

        cities.sort((city1, city2) -> Long.compare(city2.getPopulation(), city1.getPopulation()));


        for (City city : cities) {
            System.out.println(city.getName() + ": " + city.getPopulation());
        }
    }

    /**
     * All cities by continent generated
     */
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
            // SQL query to fetch capital cities in the specified region
            Statement stmt = con.createStatement();
            String sql = "SELECT city.ID, city.Name, city.Population " +
                    "FROM country " +
                    "JOIN city ON country.Capital = city.ID " +
                    "WHERE country.Region = '" + region + "' " +
                    "ORDER BY city.Population DESC";
            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                int id = rset.getInt("ID");
                String name = rset.getString("Name");
                int population = rset.getInt("Population");
                City city = new City(id, name, null, null, population); // CountryCode and District are irrelevant here
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
            // SQL query to fetch capital cities in the specified continent
            Statement stmt = con.createStatement();
            String sql = "SELECT city.ID, city.Name, city.Population " +
                    "FROM country " +
                    "JOIN city ON country.Capital = city.ID " +
                    "WHERE country.Continent = '" + continent + "' " +
                    "ORDER BY city.Population DESC";
            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                int id = rset.getInt("ID");
                String name = rset.getString("Name");
                int population = rset.getInt("Population");
                City city = new City(id, name, null, null, population); // CountryCode and District not needed here
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
            // SQL query to fetch all capital cities sorted by population
            Statement stmt = con.createStatement();
            String sql = "SELECT city.ID, city.Name, city.Population " +
                    "FROM country " +
                    "JOIN city ON country.Capital = city.ID " +
                    "ORDER BY city.Population DESC";
            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                int id = rset.getInt("ID");
                String name = rset.getString("Name");
                int population = rset.getInt("Population");
                City city = new City(id, name, null, null, population); // CountryCode and District not needed here
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



}
