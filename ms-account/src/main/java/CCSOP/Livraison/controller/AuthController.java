package CCSOP.Livraison.controller;

import CCSOP.Livraison.Repository.UserRepository;
import CCSOP.Livraison.Service.AuthService;
import CCSOP.Livraison.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    private UserRepository userRepository;
    public record LoginRequest(String email, String password) {}

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
       Collection<Role> roles =authService.authenticate(request.email(), request.password());

        if (roles!=null) {
            return ResponseEntity.ok(Map.of(
                    "message", "Connexion réussie !",
                    "user", request.email(),
                    "token", "fake-jwt-token-for-dev-12345",
                    "roles",roles
            ));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Email ou mot de passe incorrect"));
        }
    }
}
