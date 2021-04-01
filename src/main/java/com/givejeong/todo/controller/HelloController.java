package com.givejeong.todo.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/")
    public String hello(){
        return "hello";
    }
    @PostMapping("/token")
    public String token(){
        return "<h1>token</h1>";
    }
}
