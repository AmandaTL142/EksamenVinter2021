package com.example.eksamenvinter2021.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {

    @GetMapping("/")
    public String index (){
        return "Det virker lidt...";
    }
}
