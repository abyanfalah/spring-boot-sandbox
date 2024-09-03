package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class Controller {

    @GetMapping
    public ResponseEntity<?> app(){
        return ResponseEntity.ok("demo app running");
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> post(@RequestBody Map<String, String> data){
        return ResponseEntity.ok(data);
    }
}