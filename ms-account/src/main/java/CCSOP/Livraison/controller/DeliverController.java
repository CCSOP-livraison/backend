package CCSOP.Livraison.controller;

import CCSOP.Livraison.Repository.UserRepository;
import CCSOP.Livraison.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("deliver")
public class DeliverController {
        @Autowired
        private AuthService authService;
        private UserRepository userRepository;
        public record LoginRequest(String email, String password) {}
    @GetMapping
    public String helloWorld() {

        // Returning a simple "Hello World" response
        return "Hello World";
    }
}
