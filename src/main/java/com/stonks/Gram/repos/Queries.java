package com.stonks.Gram.repos;

public class Queries {
    
    public static String SQL_FIND_USERNAME = "select * from logincredentials where username = ?";
    public static String SQL_CHECK_LOGIN_CREDENTIALS = "select * from logincredentials where username = ? and password = ?";
    public static String SQL_TEST_ADD_USER = "insert into logincredentials values(?, ?)";

}
