package com.maersk.eacloud.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestConfiguration {

    public static boolean isJDK7OrHigher() {
        var isValidJDK =  isJDK_N_OrHigher(7, current());
        log.info("isValidJDK :{}", isValidJDK);
        return isValidJDK;
    }

    public static String current() {
        var javaVersion = System.getProperty("java.version");
        log.info("java version: {}", javaVersion);
        return javaVersion;
    }

    static boolean isJDK_N_OrHigher(int n, String javaVersion) {
        try {
            final String[] versionParts = javaVersion.split("\\.");

            int version;
            if ("1".equals(versionParts[0])) {
                version = Integer.parseInt(versionParts[1]);
            } else {
                version = Integer.parseInt(versionParts[0]);
            }
            return version >= n;
        } catch (Exception e) {
            // swallow any error and return false to maintain previous behaviour of defaulting to false
            return false;
        }
    }
}
