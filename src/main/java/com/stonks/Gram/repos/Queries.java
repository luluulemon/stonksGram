package com.stonks.Gram.repos;

public class Queries {
    
    public static String SQL_FIND_USERNAME = "select * from logincredentials where username = ?";
    public static String SQL_CHECK_LOGIN_CREDENTIALS = "select * from logincredentials where username = ? and password = ?";
    public static String SQL_TEST_ADD_USER = "insert into logincredentials values(?, ?)";

    public static String SQL_ADD_TRADE= "insert into trades values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static String SQL_LOAD_TRADES = "select * from trades where username=?";
    public static String SQL_DELETE_TRADE = "delete from trades where tradeId=?";
    public static String SQL_FIND_TRADE_BY_ID = "select * from trades where tradeId=?";
    public static String SQL_UPDATE_TRADE = 
        "update trades set username=?, entryDate=?, exitDate=?, entryPrice=?, exitPrice=?, tradeSize=?, tradeType=?, comments=?, tradePicsList=?, pnL=? where tradeId=?"; 
    
}
