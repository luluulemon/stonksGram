package com.stonks.Gram.repos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.stonks.Gram.models.User;

import static com.stonks.Gram.repos.Queries.*;



@Repository
public class LoginRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public String checkUserAvail(User user){
        // if username available, return "yes"

        SqlRowSet userRS = jdbcTemplate.queryForRowSet(SQL_FIND_USERNAME, user.getUserId());
        if(userRS.next())
        {   return "no";    }
        
        return "yes";
    }


    public void createNewUser(User user){
        jdbcTemplate.update(SQL_TEST_ADD_USER, user.getUserId(), user.getPassword());
    }


    public boolean validateLogin(User user){

        SqlRowSet userRS = jdbcTemplate.queryForRowSet(SQL_CHECK_LOGIN_CREDENTIALS, 
                                                        user.getUserId(),
                                                        user.getPassword());
        if(userRS.next()){  return true;    }

        return false;
    }
}
