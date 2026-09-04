package CCSOP.Livraison.controller;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("deliver")
public class DeliverController {

    @GetMapping
    public String helloWorld() {

        // Returning a simple "Hello World" response
        return "Hello World";
    }
}
