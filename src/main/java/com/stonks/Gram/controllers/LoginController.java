package com.stonks.Gram.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stonks.Gram.models.User;
import com.stonks.Gram.services.LoginService;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    
    @Autowired
    private LoginService loginSvc;

    @PostMapping("/newUser")
    public ResponseEntity<String> createNewUser(@RequestBody User user){

        Optional<JsonObject> checkUserOpt = loginSvc.checkUser(user);

        // return for invalid account creation
        if(!checkUserOpt.isEmpty())
        {   return ResponseEntity.status(401).body(checkUserOpt.get().toString());   } 

        // return for account created
        return ResponseEntity.status(201)
                                .body(Json.createObjectBuilder()
                                .add("msg", "Account created!")
                                .build()
                                .toString());
    }

    // test add
    @PostMapping("/existingUser")
    public ResponseEntity<String> existingUser(@RequestBody User user){

        // JsonReader reader = Json.createReader(new StringReader(User));
        // JsonObject userObj = reader.readObject();

        Optional<JsonObject> loginOpt = loginSvc.login(user);
        if(!loginOpt.isEmpty())
        {   return ResponseEntity.status(401).body(loginOpt.get().toString());  }

        return ResponseEntity.ok(Json.createObjectBuilder()
                                .add("msg", "Login successful!")
                                .build()
                                .toString());
    }
}
