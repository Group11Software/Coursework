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
    static void init()
    {
        app = new App();
        // if running locally delay can be zero if database is running
        // To work on GitHub Actions set the delay to 10000 as the database takes a while to build
        app.connect("localhost:33060", 10000);

    }


    @Test
    void printCitiesTestNull() {
        app.printCityReport(null);
    }

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

}
