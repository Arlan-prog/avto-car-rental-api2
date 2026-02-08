package kz.project.avto.utils;

import java.time.LocalDateTime;

public class AppLogger {

    private static AppLogger instance;

    private AppLogger() {
    }

    public static AppLogger getInstance() {
        if (instance == null) {
            instance = new AppLogger();
        }
        return instance;
    }

    public void log(String message) {
        System.out.println(
                "[" + LocalDateTime.now() + "] " + message
        );
    }
}
