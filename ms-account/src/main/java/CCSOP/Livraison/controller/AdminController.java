package CCSOP.Livraison.controller;
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
