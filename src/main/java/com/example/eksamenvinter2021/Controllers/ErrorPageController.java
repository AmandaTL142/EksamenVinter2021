package com.example.eksamenvinter2021.Controllers;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ErrorPageController {

    @GetMapping("/error")
    public String customError() {
        return "error.html";
    }

}