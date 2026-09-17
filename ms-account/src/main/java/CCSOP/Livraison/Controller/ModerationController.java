package CCSOP.Livraison.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("moderation")
public class ModerationController {
    @GetMapping
    public String helloWorld() {
        return "Hello World";
    }
}
