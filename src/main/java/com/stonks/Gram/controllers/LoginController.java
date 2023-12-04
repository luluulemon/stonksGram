package com.stonks.Gram.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stonks.Gram.entities.Trade;
import com.stonks.Gram.models.User;
import com.stonks.Gram.services.LoginService;
import com.stonks.Gram.services.TradeEntryService;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    
    @Autowired
    private LoginService loginSvc;
    @Autowired
    private TradeEntryService tradeSvc;

    @PostMapping("/newUser")
    public ResponseEntity<String> createNewUser(@RequestBody User user){

        Optional<JsonObject> checkUserOpt = loginSvc.checkUser(user);

        // return for invalid account creation
        if(!checkUserOpt.isEmpty())
        {   return ResponseEntity.status(200).body(checkUserOpt.get().toString());   } 

        // return for account created
        return ResponseEntity.status(201)
                                .body(Json.createObjectBuilder()
                                .add("msg", "Account created!")
                                .build()
                                .toString());
    }


    @CrossOrigin(origins="*", allowedHeaders = "*")
    @PostMapping("/existingUser")
    public ResponseEntity<List<Trade>> existingUser(@RequestBody User user){
        System.out.println("It got to the endpoint");

        Optional<JsonObject> loginOpt = loginSvc.login(user);
        if(!loginOpt.isEmpty())
        {   return ResponseEntity.status(200).body(null);  }

        // return list of trades after successful login
        return ResponseEntity.ok(tradeSvc.loadTrades(user));
    }


    @CrossOrigin(origins="*", allowedHeaders = "*")
    @GetMapping("/test")
    public ResponseEntity<String> testPoint(){
        return ResponseEntity.ok("test test");
    }

}
