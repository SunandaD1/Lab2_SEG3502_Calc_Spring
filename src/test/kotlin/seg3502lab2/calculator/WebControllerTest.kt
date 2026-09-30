package seg3502lab2.calculator

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.Locale

@Controller
class WebController {

    // Exécuté avant chaque gestionnaire : initialise (ou réinitialise) les attributs du modèle
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
        // defaultValue = "" : évite une erreur 500 si un paramètre est absent de l'URL
        @RequestParam(defaultValue = "") n1: String,
        @RequestParam(defaultValue = "") n2: String,
        @RequestParam(defaultValue = "") operation: String,
        model: Model
    ): String {
        // On réaffiche toujours les valeurs saisies, même en cas d'erreur
        model.addAttribute("n1", n1)
        model.addAttribute("n2", n2)

        val num1 = n1.trim().toDoubleOrNull()
        val num2 = n2.trim().toDoubleOrNull()

        if (num1 == null || num2 == null) {
            model.addAttribute("error", "NumberFormatError")
            return "home"
        }

        val result: Double = when (operation) {
            "add" -> num1 + num2
            "sub" -> num1 - num2
            "mul" -> num1 * num2
            "div" -> {
                if (num2 == 0.0) {
                    model.addAttribute("error", "DivisionByZero")
                    return "home"
                }
                num1 / num2
            }
            else -> {
                model.addAttribute("error", "InvalidOperation")
                return "home"
            }
        }

        // Locale.ROOT : toujours un point décimal ("2.50"), même sur un poste configuré en français
        model.addAttribute("result", String.format(Locale.ROOT, "%.2f", result))
        return "home"
    }
}