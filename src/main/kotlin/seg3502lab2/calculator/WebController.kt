package seg3502lab2.calculator

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class WebController {

    @ModelAttribute
    fun addAttributes(model: Model) {
        model.addAttribute("result", "")
        model.addAttribute("error", "")
        model.addAttribute("n1", "")
        model.addAttribute("n2", "")
    }

    @RequestMapping("/")
    fun home(): String {
        return "home"
    }

    @GetMapping("/calculate")
    fun calculate(
        @RequestParam(required = false) n1: String,
        @RequestParam(required = false) n2: String,
        @RequestParam(required = false) operation: String,
        model: Model
    ): String {

        try {
            val num1 = n1.toDouble()
            val num2 = n2.toDouble()

            val result = when(operation) {
                "add" -> num1 + num2
                "sub" -> num1 - num2
                "mul" -> num1 * num2
                "div" -> {
                    if (num2 == 0.0) {
                        model.addAttribute("error", "DivisionByZero")
                        model.addAttribute("n1", n1)
                        model.addAttribute("n2", n2)
                        return "home"
                    }
                    num1 / num2
                }
                else -> {
                    model.addAttribute("error", "InvalidOperation")
                    return "home"
                }
            }

            model.addAttribute("result", String.format("%.2f", result))

        } catch (e: NumberFormatException) {
            model.addAttribute("error", "NumberFormatError")
        }

        model.addAttribute("n1", n1)
        model.addAttribute("n2", n2)

        return "home"
    }
}