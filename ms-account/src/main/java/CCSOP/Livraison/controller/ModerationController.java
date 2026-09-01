package CCSOP.Livraison.controller;

import CCSOP.Livraison.Repository.UserRepository;
import CCSOP.Livraison.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("moderation")
public class ModerationController {
        @Autowired
        private AuthService authService;
        private UserRepository userRepository;
        public record LoginRequest(String email, String password) {}

        @PostMapping("/login")
        public ResponseEntity<?> login(@RequestBody CCSOP.Livraison.controller.AuthController.LoginRequest request) {
            boolean isValid = authService.authenticate(request.email(), request.password());

            if (isValid) {
                return ResponseEntity.ok(Map.of(
                        "message", "Connexion réussie !",
                        "user", request.email(),
                        "token", "fake-jwt-token-for-dev-12345"
                ));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Email ou mot de passe incorrect"));
            }
        }
    @GetMapping
    public String helloWorld() {

        // Returning a simple "Hello World" response
        return "Hello World";
    }

    }
