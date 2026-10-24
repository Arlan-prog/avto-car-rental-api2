package kz.project.avto.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AppLogger {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppLogger.class);

    private AppLogger() {
    }

    private static final class Holder {
        private static final AppLogger INSTANCE = new AppLogger();
    }

    public static AppLogger getInstance() {
        return Holder.INSTANCE;
    }

    public void log(String message) {
        LOGGER.info("{}", message);
    }
}
