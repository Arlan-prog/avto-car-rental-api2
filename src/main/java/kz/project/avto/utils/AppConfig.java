package kz.project.avto.utils;

public final class AppConfig {
    private final String appName = "Avto Car Rental API";
    private final String version = "1.0";

    private AppConfig() {
    }

    private static final class Holder {
        private static final AppConfig INSTANCE = new AppConfig();
    }

    public static AppConfig getInstance() {
        return Holder.INSTANCE;
    }

    public String getAppName() { return appName; }
    public String getVersion() { return version; }
}
