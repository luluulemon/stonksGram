package com.stonks.Gram.controllers;

import java.io.StringReader;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stonks.Gram.services.LoginService;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    
    @Autowired
    private LoginService loginSvc;

    @PostMapping("/newUser")
    public ResponseEntity<String> createNewUser(@RequestBody String User){

        JsonReader reader = Json.createReader(new StringReader(User));
        JsonObject userObj = reader.readObject();

        Optional<JsonObject> checkUserObt = loginSvc.checkUser(userObj);

        // return for invalid account creation
        if(!checkUserObt.isEmpty())
        {   return ResponseEntity.status(401).body(checkUserObt.get().toString());   } 

        // return for account created
        return ResponseEntity.status(201)
                                .body(Json.createObjectBuilder()
                                .add("msg", "Account created!")
                                .build()
                                .toString());
    }

    // test add
    @PostMapping("/existingUser")
    public ResponseEntity<String> existingUser(@RequestBody String User){

        JsonReader reader = Json.createReader(new StringReader(User));
        JsonObject userObj = reader.readObject();

        Optional<JsonObject> loginOpt = loginSvc.login(userObj);
        if(!loginOpt.isEmpty())
        {   return ResponseEntity.status(401).body(loginOpt.get().toString());  }

        return ResponseEntity.ok(Json.createObjectBuilder()
                                .add("msg", "Login successful!")
                                .build()
                                .toString());
    }
}
