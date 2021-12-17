package com.example.eksamenvinter2021.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorPageController{
    //Amanda Tolstrup Laursen

    //Denne controller returnerer en error-page
    @GetMapping("/error")
    public String customError() {
        return "error";
    }
}