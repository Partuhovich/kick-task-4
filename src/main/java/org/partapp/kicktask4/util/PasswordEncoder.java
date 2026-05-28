package org.partapp.kicktask4.util;

import org.mindrot.jbcrypt.BCrypt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PasswordEncoder {
    private static final Logger logger = LogManager.getLogger(PasswordEncoder.class);
    private static final int WORK_FACTOR = 10;
    private static PasswordEncoder instance = new PasswordEncoder();

    private PasswordEncoder() {}

    public static PasswordEncoder getInstance() {
        return instance;
    }

    public String encode(String plainPassword) {
        String salt = BCrypt.gensalt(WORK_FACTOR);
        String hashedPassword = BCrypt.hashpw(plainPassword, salt);
        logger.debug("Password encoded successfully");
        return hashedPassword;
    }

    public boolean matches(String plainPassword, String hashedPassword) {
        try {
            boolean matches = BCrypt.checkpw(plainPassword, hashedPassword);
            logger.debug("Password match check result: {}", matches);
            return matches;
        } catch (Exception e) {
            logger.error("Error checking password", e);
            return false;
        }
    }
}