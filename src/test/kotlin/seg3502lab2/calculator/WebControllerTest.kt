package seg3502lab2.calculator

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

@WebMvcTest(WebController::class)
class WebControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    private fun calc(n1: String, n2: String, op: String) =
        mockMvc.perform(
            get("/calculate")
                .param("n1", n1)
                .param("n2", n2)
                .param("operation", op)
        )

    @Test
    fun request_to_home() {
        mockMvc.perform(get("/"))
            .andExpect(status().isOk)
            .andExpect(view().name("home"))
    }

    @Test
    fun addition() {
        calc("2", "3", "add")
            .andExpect(status().isOk)
            .andExpect(model().attribute("result", "5.00"))
            .andExpect(view().name("home"))
    }

    @Test
    fun subtraction() {
        calc("10", "4", "sub")
            .andExpect(model().attribute("result", "6.00"))
    }

    @Test
    fun multiplication() {
        calc("3", "5", "mul")
            .andExpect(model().attribute("result", "15.00"))
    }

    @Test
    fun division() {
        calc("10", "4", "div")
            .andExpect(model().attribute("result", "2.50"))
    }

    @Test
    fun division_by_zero() {
        calc("10", "0", "div")
            .andExpect(status().isOk)
            .andExpect(model().attribute("error", "DivisionByZero"))
            .andExpect(model().attribute("result", ""))
    }

    @Test
    fun invalid_number() {
        calc("abc", "3", "add")
            .andExpect(status().isOk)
            .andExpect(model().attribute("error", "NumberFormatError"))
            .andExpect(model().attribute("n1", "abc"))
    }

    @Test
    fun invalid_operation() {
        calc("2", "3", "pow")
            .andExpect(model().attribute("error", "InvalidOperation"))
            .andExpect(model().attribute("n1", "2"))
    }

    @Test
    fun missing_parameters_do_not_crash() {
        mockMvc.perform(get("/calculate"))
            .andExpect(status().isOk)
            .andExpect(model().attribute("error", "NumberFormatError"))
    }
}