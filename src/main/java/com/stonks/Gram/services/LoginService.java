package com.stonks.Gram.services;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stonks.Gram.models.User;
import com.stonks.Gram.repos.LoginRepository;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@Service
public class LoginService {
    
    @Autowired
    private LoginRepository loginRepo;

    public Optional<JsonObject> checkUser(User user){
        
        // Check for username length
        if(user.getUserId().length() < 4)
        {   System.out.println("Username too short");   
            return Optional.of(Json.createObjectBuilder()
                        .add("msg", "Please choose username with at least 4 chars")
                        .build());
        }

        // Check for password length
        if(user.getPassword().length() < 8)
        {   System.out.println("Password too short");   
            return Optional.of(Json.createObjectBuilder()
                        .add("msg", "Please choose password with at least 8 chars")
                        .build());
        }

        // Check for password regex (Uppercase, Lowercase, and number)
        
        String pattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+$";
        // Create a Pattern object
        Pattern regexPattern = Pattern.compile(pattern);
        // Create a Matcher object
        Matcher matcher = regexPattern.matcher(user.getPassword());

        if( !matcher.matches()) // if regex not matched
        {   return Optional.of(Json.createObjectBuilder()
                        .add("msg", "Password needs at least one uppercase letter, one lowercase letter, & one number")
                        .build());   
        }

        // If format valid, check for username availability
        if(loginRepo.checkUserAvail(user).equals("no"))
        {   
            return Optional.of(Json.createObjectBuilder()
                        .add("msg", "Username not available")
                        .build()); 
        }

        
        // valid Username & password, create new User
        loginRepo.createNewUser(user);
        return Optional.empty();
    }




    public Optional<JsonObject> login(User user){
        
        // Check for password regex (Uppercase, Lowercase, and number)
        String pattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+$";
        // Create a Pattern object
        Pattern regexPattern = Pattern.compile(pattern);
        // Create a Matcher object
        Matcher matcher = regexPattern.matcher(user.getPassword());

        // Check for username length
        if(user.getUserId().length() < 4 || 
            user.getPassword().length() < 8 ||
            !matcher.matches()
            )
        {   System.out.println("Username/password format is wrong");   
            return Optional.of(Json.createObjectBuilder()
                        .add("msg", "Invalid username or password")
                        .build());
        }

    
        // valid Username & password format, check loginCredentials
        if(!loginRepo.validateLogin(user))
        {   return Optional.of(Json.createObjectBuilder()
                        .add("msg", "Wrong username or password")
                        .build());  };
        return Optional.empty();
    }



}
