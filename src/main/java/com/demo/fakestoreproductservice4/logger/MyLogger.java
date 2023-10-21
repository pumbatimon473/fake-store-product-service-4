package com.demo.fakestoreproductservice4.logger;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MyLogger {
    // Fields
    private Logger logger;

    // Behaviors
    public void logInfo(String message) {
        this.logger.info(message);
    }

    public void logWarn(String message) {
        this.logger.warn(message);
    }

    public void logError(String message) {
        this.logger.error(message);
    }
}
