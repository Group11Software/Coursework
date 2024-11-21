package TESTPACKAGE;

public class countrylanguage {
    private String language;
    private int count;
    private double percentage;
    private String countryCode;
    private boolean isOfficial;

    public countrylanguage(String language, int count, double percentage, String countryCode, boolean isOfficial) {
        this.language = language;
        this.count = count;
        this.percentage = percentage;
        this.countryCode = countryCode;
        this.isOfficial = isOfficial;
    }

    public String getLanguage() {
        return language;
    }

    public int getCount() {
        return count;
    }

    public double getPercentage() {
        return percentage;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public boolean getIsOfficial() {
        return isOfficial;
    }
}



