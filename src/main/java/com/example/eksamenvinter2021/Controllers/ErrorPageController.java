package com.example.eksamenvinter2021.Controllers;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorPageController implements ErrorController {

    @GetMapping("/error")
    public String customError() {
        return "The link you followed may be broken, or the page may have been removed.";
    }

}