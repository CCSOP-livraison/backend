package CCSOP.Livraison.Controller;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("deliver")
public class DeliverController {

    @GetMapping
    public String helloWorld() {

        return "Hello World";
    }
}
