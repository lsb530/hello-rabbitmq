package boki.hellorabbitmq.step4.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController {

    @GetMapping("/home")
    fun home(model: Model): String {
        model.addAttribute("message", "Welcome to RabbitMQ Sample")
        return "step4/home"
    }

    @GetMapping("/")
    fun news(model: Model): String {
        model.addAttribute("message", "Welcome to RabbitMQ News Sample!");
        return "step4/news"
    }

}