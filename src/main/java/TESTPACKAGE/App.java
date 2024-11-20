package TESTPACKAGE;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

        // Disconnect from database
        a.disconnect();
    }

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
        return cities;
    }

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
        return city;
    }

    public ArrayList<Country> report2() {
        ArrayList<Country> countries = new ArrayList<>(); /** List to store countries **/
        try {
            /** Create an SQL statement **/
            Statement stmt = con.createStatement();
            /** SQL query to get name and population of each country **/
            String sql = "SELECT name, population FROM country";
            /** Execute SQL statement **/
            ResultSet rset = stmt.executeQuery(sql);

            /** Loop through the result set **/
            while (rset.next()) {
                String name = rset.getString("name");
                long population = rset.getLong("population");

                /** Create new Country object and set the name and population **/
                Country country = new Country();
                country.setName(name);
                country.setPopulation(population);

                /** Add the Country object to the list **/
                countries.add(country);
            }

            /** Optionally write to a file for debugging or logging **/
            new File("./output/").mkdir(); /** Create output folder if not exist **/
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("./output/report2.txt"))) {
                /** loops through all countries **/
                for (Country country : countries) {
                    writer.write(country.getName() + "\t" + country.getPopulation() + "\r\n");
                }
            }
            /** throws an error if fail **/
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to retrieve country details");
        }
        return countries; /** Return the list of Country objects **/
    }


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

    public ArrayList<City> getAsianCitiesSortedByPopulation() {
        ArrayList<City> asianCities = getCitiesByContinent("Asia");
        if (asianCities != null) {
            asianCities.sort((city1, city2) -> Long.compare(city2.getPopulation(), city1.getPopulation()));
        }
        return asianCities;
    }

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

            StringBuilder report = new StringBuilder();
            report.append("Cities in North America sorted by population:\n");
            for (City city : cities) {
                report.append(city.getName())
                        .append(" (")
                        .append(city.getCountryCode())
                        .append("): ")
                        .append(city.getPopulation())
                        .append("\n");
            }
            new File("./output/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter("./output/NorthAmericaCitiesReport.txt"));
            writer.write(report.toString());
            writer.close();
            System.out.println(report.toString());
        } catch (Exception e) {
            System.out.println("Error generating North America cities report: " + e.getMessage());
        }
    }

    public ArrayList<Country> report3() {
        ArrayList<Country> countries = new ArrayList<>(); /**List to store countries **/
        try {
            /** Create an SQL statement **/
            Statement stmt = con.createStatement();

            /** Correct SQL query to fetch name and population of countries in Asia **/
            String sql = "SELECT name, population FROM country WHERE continent = 'Asia'";

            /** Execute SQL statement **/
            ResultSet rset = stmt.executeQuery(sql);

            /** Loop through the result set **/
            while (rset.next()) {
                String name = rset.getString("name");
                long population = rset.getLong("population");

                /** Create new Country object and set the name and population **/
                Country country = new Country();
                country.setName(name);
                country.setPopulation(population);

                /** Add the Country object to the list **/
                countries.add(country);
            }

            /** Optionally write to a file for debugging or logging **/
            new File("./output/").mkdir(); /** Create output folder if not exist **/
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("./output/report2.txt"))) {
                /** loops through all the countries **/
                for (Country country : countries) {
                    writer.write(country.getName() + "\t" + country.getPopulation() + "\r\n");
                }
            }
            /** if fail **/
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to retrieve country details");
        }
        return countries; /** Return the list of Country objects **/
    }

    public ArrayList<Country> report4() {
        ArrayList<Country> countries = new ArrayList<>(); /** List to store countries **/
        try {
            /** Create an SQL statement **/
            Statement stmt = con.createStatement();

            /** Correct SQL query to fetch name and population of countries in Asia **/
            String sql = "SELECT name, population FROM country WHERE region = 'North America'";

            /** Execute SQL statement **/
            ResultSet rset = stmt.executeQuery(sql);

            /** Loop through the result set **/
            while (rset.next()) {
                String name = rset.getString("name");
                long population = rset.getLong("population");

                /** Create new Country object and set the name and population **/
                Country country = new Country();
                country.setName(name);
                country.setPopulation(population);

                /** Add the Country object to the list **/
                countries.add(country);
            }

            /** Optionally write to a file for debugging or logging **/
            new File("./output/").mkdir(); /** Create output folder if not exist **/
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("./output/report2.txt"))) {
                /** loops through all countries **/
                for (Country country : countries) {
                    writer.write(country.getName() + "\t" + country.getPopulation() + "\r\n");
                }
            }
            /** if fails sends out message **/
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to retrieve country details");
        }
        return countries; /** Return the list of Country objects **/
    }

    public ArrayList<Country> report5() {
        ArrayList<Country> countries = new ArrayList<>(); /** List to store countries **/
        try {
            /** Create an SQL statement **/
            Statement stmt = con.createStatement();

            /** Corrected SQL query **/
            String sql = "SELECT code, name, continent, region, population, capital FROM country";

            /** Execute SQL statement **/
            ResultSet rset = stmt.executeQuery(sql);

            /** Loop through the result set **/
            while (rset.next()) {
                String code = rset.getString("code"); /** Ensure the 'code' column exists **/
                String name = rset.getString("name");
                long population = rset.getLong("population");
                String continent = rset.getString("continent");
                String region = rset.getString("region");

                /** Create new Country object and set its fields **/
                Country country = new Country();
                country.setCode(code);
                country.setName(name);
                country.setPopulation(population);
                country.setContinent(continent);
                country.setRegion(region);

                /** Add the Country object to the list **/
                countries.add(country);
            }

            /** Optionally write to a file for debugging or logging **/
            new File("./output/").mkdir(); /** Create output folder if not exist **/
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("./output/report2.txt"))) {
                /** loops through countries **/
                for (Country country : countries) {
                    writer.write(country.getCode() + "\t" + country.getName() + "\t" + country.getPopulation() + "\t"
                            + country.getContinent() + "\t" + country.getRegion() + "\r\n");
                }
            }
            /** if error print exception message **/
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to retrieve country details");
        }
        return countries; /** Return the list of City objects **/
    }

    public ArrayList<City> report6() {
        ArrayList<City> cities = new ArrayList<>(); /** List to store cities **/
        try {
            /** Create an SQL statement **/
            Statement stmt = con.createStatement();

            /** Updated SQL query to include the 'id' column **/
            String sql = "SELECT id, name, countrycode, district, population FROM city";

            /** Execute SQL statement **/
            ResultSet rset = stmt.executeQuery(sql);

            /** Loop through the result set **/
            while (rset.next()) {
                /** Retrieve city data from the result set **/
                long id = rset.getLong("id");
                String name = rset.getString("name");
                String countryCode = rset.getString("countrycode");
                String district = rset.getString("district");
                long population = rset.getLong("population");

                /** Create a new City object using the constructor **/
                City city = new City(id, name, countryCode, district, population);

                /** Add the City object to the list **/
                cities.add(city);
            }

            /** Optionally write to a file for debugging or logging **/
            new File("./output/").mkdir(); /** Create output folder if not exist **/
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("./output/report6.txt"))) {
                /** loops through all cities**/
                for (City city : cities) {
                    writer.write(city.toString() + "\r\n");
                }
            }
            /** if fail print exceptino message **/
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to retrieve city details");
        }
        return cities; /** Return the list of City objects **/
    }

    public ArrayList<Country> report7() {
        ArrayList<Country> countries = new ArrayList<>();
        try {
            /** Create an SQL statement **/
            Statement stmt = con.createStatement();

            /** SQL query to join city and country tables, filtering for capital cities **/
            String sql = "SELECT country.name AS country_name, country.population AS country_population, " +
                    "city.name AS capital_name, city.population AS capital_population " +
                    "FROM country " +
                    "JOIN city ON country.code = city.countryCode " +
                    "WHERE city.id = country.capital";  /** Filter for capital cities **/

            /** Execute SQL statement **/
            ResultSet rset = stmt.executeQuery(sql);

            /** Loop through the result set **/
            while (rset.next()) {
                String countryName = rset.getString("country_name");
                long countryPopulation = rset.getLong("country_population");
                String capitalName = rset.getString("capital_name");
                long capitalPopulation = rset.getLong("capital_population");

                /** Create new Country object and set the fields **/
                Country country = new Country();
                country.setName(countryName);
                country.setPopulation(countryPopulation);

                /** Here we set the capital city as a part of country  **/
                System.out.println("Country: " + countryName +
                        ", Capital: " + capitalName + ", Capital Population: " + capitalPopulation);


                City capitalCity = new City(0, capitalName, "", "", capitalPopulation);
                country.setCapital(capitalCity.getId());  // Set the capital city ID if needed

                /** Add the Country object to the list **/
                countries.add(country);
            }

            /** write to a file for debugging or logging **/
            new File("./output/").mkdir(); /** Create output folder if not exist **/
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("./output/report2.txt"))) {
                /** loops through the countries **/
                for (Country country : countries) {
                    writer.write(country.getName() + "\t" + country.getPopulation() + "\r\n");
                }
            }
            /** fail message **/
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to retrieve country details");
        }
        return countries; /** Return the list of Country objects **/
    }

    public List<Object> report8() {
        /** List to store Country objects **/
        ArrayList<Country> countries = new ArrayList<>();
        /** List to store City objects **/
        ArrayList<City> cities = new ArrayList<>();

        try {
            /** Create an SQL statement **/
            Statement stmt = con.createStatement();

            /** Query to get all countries with their population **/
            String countrySQL = "SELECT code, name, population FROM country";
            ResultSet rsetCountry = stmt.executeQuery(countrySQL);

            /**Populate the countries list with country data **/
            while (rsetCountry.next()) {
                String countryCode = rsetCountry.getString("code");
                String countryName = rsetCountry.getString("name");
                long countryPopulation = rsetCountry.getLong("population");

                /** Create Country object and set its properties **/
                Country country = new Country();
                country.setCode(countryCode);
                country.setName(countryName);
                country.setPopulation(countryPopulation);

                /** Add Country object to the list **/
                countries.add(country);
            }

            /**  Populate the cities list with city data and calculate urban population **/
            for (Country country : countries) {
                String countryCode = country.getCode();
                long countryPopulation = country.getPopulation();

                /** Query to get all cities in the current country **/
                String citySQL = "SELECT name, population FROM city WHERE countryCode = '" + countryCode + "'";
                ResultSet rsetCity = stmt.executeQuery(citySQL);

                long totalCityPopulation = 0;
                while (rsetCity.next()) {
                    String cityName = rsetCity.getString("name");
                    long cityPopulation = rsetCity.getLong("population");

                    /**
                     * Create a City object and set its properties
                     * Pass default values for id and district (since they're not available in the query) **/
                    City city = new City(0, cityName, countryCode, "", cityPopulation);

                    /** Add City object to the list **/
                    cities.add(city);

                    /** Add the city population to the total city population for the country **/
                    totalCityPopulation += cityPopulation;
                }

                /** Calculate rural population (outside of cities) **/
                long ruralPopulation = countryPopulation - totalCityPopulation;

                /** You can print or store these values as needed, for now we'll print them **/
                System.out.println("Country: " + country.getName());
                System.out.println("Total Population: " + countryPopulation);
                System.out.println("Urban Population (in cities): " + totalCityPopulation);
                System.out.println("Rural Population (outside cities): " + ruralPopulation);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving country and city details: " + e.getMessage());
        }

        /** Return the list with both countries and cities **/
        List<Object> result = new ArrayList<>();
        result.add(countries); /** Add countries list **/
        result.add(cities); /** Add cities list **/

        return result;
    }
    public List<Object> report9() {
        /** List to store continents with their aggregated population data **/
        ArrayList<String> continents = new ArrayList<>();
        /** List to store population details for each continent **/
        ArrayList<Map<String, Object>> populationData = new ArrayList<>();

        try {
            /** Create an SQL statement **/
            Statement stmt = con.createStatement();

            /** Query to get all continents and their populations. **/
            String continentSQL =
                    "SELECT continent, " +
                            "       SUM(country.population) AS total_country_population, " +
                            "       SUM(city.population) AS total_city_population " +
                            "FROM country " +
                            "LEFT JOIN city ON country.code = city.countryCode " +
                            "GROUP BY continent";
            ResultSet rsetContinent = stmt.executeQuery(continentSQL);

            /** Process each continent's data **/
            while (rsetContinent.next()) {
                String continent = rsetContinent.getString("continent");
                long totalCountryPopulation = rsetContinent.getLong("total_country_population");
                long totalCityPopulation = rsetContinent.getLong("total_city_population");
                long totalRuralPopulation = totalCountryPopulation - totalCityPopulation;

                /** Add continent name to the continents list **/
                continents.add(continent);

                /** Store population details in a map **/
                Map<String, Object> data = new HashMap<>();
                data.put("continent", continent);
                data.put("totalCountryPopulation", totalCountryPopulation);
                data.put("totalCityPopulation", totalCityPopulation);
                data.put("totalRuralPopulation", totalRuralPopulation);

                /** Add the map to the population data list **/
                populationData.add(data);

                /** Print the details **/
                System.out.println("Continent: " + continent);
                System.out.println("  Total Population: " + totalCountryPopulation);
                System.out.println("  Urban Population (in cities): " + totalCityPopulation);
                System.out.println("  Rural Population (outside cities): " + totalRuralPopulation);
                System.out.println("---------------");
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving continent details: " + e.getMessage());
        }

        /** Return the list with both continent names and population data **/
        List<Object> result = new ArrayList<>();
        result.add(continents); /** Add continents list **/
        result.add(populationData); /** Add population data list **/

        return result;
    }
















}