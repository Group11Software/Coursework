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

    /**
     * unit test to test if cities null to say no cities found
     **/
    @Test
    void printCitiesTestNull() {
        app.printCityReport(null);
    }

    /**
     * unit test to test if cities works
     **/
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

    /**
     * unit test to test if cities constructor and getters work
     **/
    @Test
    void testCityConstructorAndGettersWithReport() {

        ArrayList<City> cities = new ArrayList<>();
        City city1 = new City(1, "Los Angelos", "USA", "Us", 3800000);
        City city2 = new City(2, "Dawson City", "CAN", "Yukon", 1577);
        City city3 = new City(3, "Tequila", "MEX", "jalisco", 44353);
        cities.add(city1);
        cities.add(city2);
        cities.add(city3);

        app.printCityReport(cities);

        assertEquals(1, city1.getId());
        assertEquals("CityA", city1.getName());
        assertEquals("USA", city1.getCountryCode());
        assertEquals("DistrictA", city1.getDistrict());
        assertEquals(100000, city1.getPopulation());

        assertEquals(2, city2.getId());
        assertEquals("CityB", city2.getName());
        assertEquals("CAN", city2.getCountryCode());
        assertEquals("DistrictB", city2.getDistrict());
        assertEquals(200000, city2.getPopulation());

        assertEquals(3, city3.getId());
        assertEquals("CityC", city3.getName());
        assertEquals("MEX", city3.getCountryCode());
        assertEquals("DistrictC", city3.getDistrict());
        assertEquals(300000, city3.getPopulation());

    }

    /**
     * unit test to test if countries getters and setters works
     **/
    @Test
    void testCountryConstructorAndGettersSetters() {
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
        country.setCapital(1);
        country.setHeadOfState("President");

        assertEquals("US", country.getCode());
        assertEquals("United States", country.getName());
        assertEquals("North America", country.getContinent());
        assertEquals("Americas", country.getRegion());
        assertEquals(331000000, country.getPopulation());
        assertEquals(9833517, country.getSurfaceArea());
        assertEquals(79.11, country.getLifeExpectancy(), 0.01);
        assertEquals(21137518, country.getGnp());
        assertEquals(21000000, country.getGnpOld());
        assertEquals(1, country.getCapital());
        assertEquals("President", country.getHeadOfState());

        ArrayList<Country> countries = new ArrayList<>();
        countries.add(country);
        app.printCountryReport1(countries);
    }
}