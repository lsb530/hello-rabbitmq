package boki.hellorabbitmq.step3.view

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController {

    @GetMapping("/home")
    fun home(model: Model): String {
        model.addAttribute("message", "Welcome to RabbitMQ Sample")
        return "step3/home"
    }

    @GetMapping("/")
    fun index(): String {
        return "step3/index"
    }

}