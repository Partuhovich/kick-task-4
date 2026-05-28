package org.partapp.kicktask4.service.impl.param;

public final class ServiceParameter {

    private ServiceParameter() {}

    public static final String USERNAME_PATTERN = "^[a-zA-Z0-9_]{3,20}$";
    public static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
    public static final int MIN_USERNAME_LENGTH = 3;
    public static final int MAX_USERNAME_LENGTH = 20;
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_PASSWORD_LENGTH = 50;
}
