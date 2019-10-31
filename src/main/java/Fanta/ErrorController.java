package Fanta;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class ErrorController {

    @RequestMapping(value = "/error")
    public String error() {
        return "Error";
    }
}
