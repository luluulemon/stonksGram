package com.stonks.Gram.controllers;

import java.io.StringReader;

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

        return ResponseEntity.ok(loginSvc.checkUser(userObj).toString());
    }


    @PostMapping("/existingUser")
    public ResponseEntity<String> existingUser(@RequestBody String User){

        return ResponseEntity.ok(null);
    }
}
