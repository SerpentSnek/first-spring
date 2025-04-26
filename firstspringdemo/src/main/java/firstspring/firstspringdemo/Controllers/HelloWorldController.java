package firstspring.firstspringdemo.Controllers;

// import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class HelloWorldController {
    @RequestMapping("/hello")
    public String helloWorld() {
        return "Hello World!!!!";
    }
}
