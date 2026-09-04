package CCSOP.Livraison.controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("moderation")
public class ModerationController {
    @GetMapping
    public String helloWorld() {

        // Returning a simple "Hello World" response
        return "Hello World";
    }

    }
