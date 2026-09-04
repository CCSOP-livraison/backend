package CCSOP.Livraison.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("customer")
public class CustomerController {

    @GetMapping
    public String helloWorld() {

        // Returning a simple "Hello World" response
        return "Hello World";
    }
    }
