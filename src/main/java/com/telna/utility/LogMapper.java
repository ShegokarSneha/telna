package com.telna.utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogMapper {

    private static final Logger logger = LogManager.getLogger(LogMapper.class);
    public static void info(String msg) { logger.info(msg); }
    public static void debug(String msg) { logger.debug(msg); }
    public static void error(String msg) { logger.error(msg); }
}
