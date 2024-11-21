package TESTPACKAGE;

import TESTPACKAGE.App;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

public class AppTest {
    static App app;

    @BeforeAll
    static void init() {
        app = new App();
    }
    /**unit test to test if cities null to say no cities found**/
    @Test
    void printCitiesTestNull() {
        app.printCityReport(null);
    }
    /**unit test to test if cities works**/
    @Test
    void printCitiesTestNormal() {
        ArrayList<City> cities = new ArrayList<>();
        City city = new City(458, "Glasgow", "GBR", "Scotland", 619680);
        cities.add(city);
        city = new City(459, "Liverpool", "GBR", "England", 461000);
        cities.add(city);
        city = new City(460, "Edinburgh", "GBR", "Scotland", 450180);
        cities.add(city);
        app.printCityReport(cities);
    }
    /**unit test to test if cities constructor and getters work**/
    @Test
    void testCityConstructorAndGetters() {
        // Arrange
        long id = 1;
        String name = "CityA";
        String countryCode = "US";
        String district = "DistrictA";
        long population = 100000;

        // Act
        City city = new City(id, name, countryCode, district, population);

        // Assert
        assertEquals(id, city.getId());
        assertEquals(name, city.getName());
        assertEquals(countryCode, city.getCountryCode());
        assertEquals(district, city.getDistrict());
        assertEquals(population, city.getPopulation());
    }
    /**unit test to test if countries getters and setters works**/
    @Test
    void testCountryConstructorAndGettersSetters() {
        // Arrange
        Country country = new Country();
        country.setCode("US");
        country.setName("United States");
        country.setContinent("North America");
        country.setRegion("Americas");
        country.setPopulation(331000000);
        country.setSurfaceArea(9833517);
        country.setLifeExpectancy(79.11);
        country.setGnp(21137518);
        country.setGnpOld(21000000);
        country.setCapital(1);  // Assuming 1 is the capital city's ID
        country.setHeadOfState("President");

        // Act & Assert
        assertEquals("US", country.getCode());
        assertEquals("United States", country.getName());
        assertEquals("North America", country.getContinent());
        assertEquals("Americas", country.getRegion());
        assertEquals(331000000, country.getPopulation());
        assertEquals(9833517, country.getSurfaceArea());
        assertEquals(79.11, country.getLifeExpectancy());
        assertEquals(21137518, country.getGnp());
        assertEquals(21000000, country.getGnpOld());
        assertEquals(1, country.getCapital());
        assertEquals("President", country.getHeadOfState());
    }
    /**unit test to test if it will say countries are null if countries is set to null**/
    @Test
    void printCountriesTestNull(){app.printCountryReport(null);}




}
