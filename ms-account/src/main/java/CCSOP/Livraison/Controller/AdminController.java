package CCSOP.Livraison.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
public class AdminController {

        @GetMapping
        public String helloWorld() {

            // Returning a simple "Hello World" response
            return "Hello World";
        }
}
