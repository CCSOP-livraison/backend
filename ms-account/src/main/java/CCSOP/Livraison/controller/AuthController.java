package CCSOP.Livraison.controller;

import CCSOP.Livraison.Service.AuthService;
import CCSOP.Livraison.entities.Role;
import CCSOP.Livraison.entities.User;
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

    public record LoginRequest(String email, String password) {}

    public record RegisterRequest(
            String email,
            String password,
            String firstname,
            String lastname,
            String address,
            String zipcode,
            String locate,
            String phoneNumber
    ) {}

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

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (request == null || request.email() == null || request.email().isBlank()
                || request.password() == null || request.password().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "L'email et le mot de passe sont obligatoires"));
        }

        try {
            User user = new User();
            user.setEmail(request.email());
            user.setFirstname(request.firstname());
            user.setLastname(request.lastname());
            user.setAddress(request.address());
            user.setZipcode(request.zipcode());
            user.setLocate(request.locate());
            user.setPhoneNumber(request.phoneNumber());

            User registeredUser = authService.register(user, request.password());

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "message", "Inscription réussie !",
                    "user", registeredUser.getEmail()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
