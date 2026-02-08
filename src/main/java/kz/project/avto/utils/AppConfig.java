package kz.project.avto.utils;

public class AppConfig {

    private static AppConfig instance;

    private final String appName;
    private final String version;

    // private constructor
    private AppConfig() {
        this.appName = "Avto Car Rental API";
        this.version = "1.0";
    }

    // Singleton access method
    public static AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }

    public String getAppName() {
        return appName;
    }

    public String getVersion() {
        return version;
    }
}
