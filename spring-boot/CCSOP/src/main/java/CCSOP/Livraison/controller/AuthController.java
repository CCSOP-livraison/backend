package CCSOP.Livraison.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {
    @GetMapping("/login")
    public Map<String, String> login() {
        return Map.of("message", "Authentification réussie !");
    }

}
