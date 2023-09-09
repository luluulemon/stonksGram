package com.stonks.Gram.repos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import static com.stonks.Gram.repos.Queries.*;

import jakarta.json.JsonObject;

@Repository
public class LoginRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public String checkUserAvail(JsonObject userObj){
        // if username available, return "yes"

        SqlRowSet userRS = jdbcTemplate.queryForRowSet(SQL_FIND_USERNAME, userObj.getString("user"));
        if(userRS.next())
        {   return "no";    }
        
        return "yes";
    }


    public void createNewUser(JsonObject userObj){
        jdbcTemplate.update(SQL_TEST_ADD_USER, userObj.getString("user"), userObj.getString("password"));
    }


    public boolean validateLogin(JsonObject userObj){

        SqlRowSet userRS = jdbcTemplate.queryForRowSet(SQL_CHECK_LOGIN_CREDENTIALS, 
                                                        userObj.getString("user"),
                                                        userObj.getString("password"));
        if(userRS.next()){  return true;    }

        return false;
    }
}
