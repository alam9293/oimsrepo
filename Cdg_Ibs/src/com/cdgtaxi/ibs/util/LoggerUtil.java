

package com.cdgtaxi.ibs.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;

public class LoggerUtil {
    public static void printStackTrace(Logger logger, Exception e) {
        StringWriter out = new StringWriter();
        PrintWriter writer = new PrintWriter(out);
        e.printStackTrace(writer);
        logger.error(out);
    }
}
