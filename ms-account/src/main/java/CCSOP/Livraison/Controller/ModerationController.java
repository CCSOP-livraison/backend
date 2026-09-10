package CCSOP.Livraison.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("moderation")
public class ModerationController {
    @GetMapping
    public String helloWorld() {

        // Returning a simple "Hello World" response
        return "Hello World";
    }

    }
