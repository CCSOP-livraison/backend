package CCSOP.Livraison.Controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("customer")
public class CustomerController {

    @GetMapping
    public String helloWorld() {

        return "Hello World";
    }
}
