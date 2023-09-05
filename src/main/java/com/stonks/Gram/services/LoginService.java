package com.stonks.Gram.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stonks.Gram.repos.LoginRepository;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@Service
public class LoginService {
    
    @Autowired
    private LoginRepository loginRepo;

    public JsonObject checkUser(JsonObject userObj){
        // Check for username availability
        if(loginRepo.checkUserAvail(userObj).equals("no"))
        {   
            return Json.createObjectBuilder()
                        .add("msg", "Username not available")
                        .build(); 
        }
        
        // Check for username length
        if(userObj.getString("user").length() < 4)
        {   System.out.println("Username too short");   
            return Json.createObjectBuilder()
                        .add("msg", "Please choose username with at least 4 chars")
                        .build();
        }

        // Check for password length
        if(userObj.getString("password").length() < 8)
        {   System.out.println("Password too short");   
            return Json.createObjectBuilder()
                        .add("msg", "Please choose password with at least 8 chars")
                        .build();
        }

        // Check for password regex (Uppercase, Lowercase, and number)
        
        String pattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+$";
        // Create a Pattern object
        Pattern regexPattern = Pattern.compile(pattern);
        // Create a Matcher object
        Matcher matcher = regexPattern.matcher(userObj.getString("password"));

        if( !matcher.matches()) // if regex not matched
        {   return Json.createObjectBuilder()
                        .add("msg", "Password needs at least one uppercase letter, one lowercase letter, & one number")
                        .build();   }
        
        // valid Username & password
        return Json.createObjectBuilder()
                    .add("msg", "User created!")
                    .build();
    }



}
