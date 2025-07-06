package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestCalculator.class)
public class RestCalculatorTest {

    @Autowired
    private MockMvc mockMvc;

    /*
    I could not find a proper way to mock the calculationKafkaService
    that was not deprecated, so I'm using the @MockBean annotation.
    */
    @MockBean
    private CalculationKafkaService calculationKafkaService;

    @Test
    public void testAddEndpoint() throws Exception {
        // Mock the calculation service response
        CalculationResponse mockResponse = new CalculationResponse("test-id", "addition", 5.0, 3.0, 8.0, true, null);
        when(calculationKafkaService.performCalculation("add", 5.0, 3.0)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/calculator/add")
                .param("a", "5.0")
                .param("b", "3.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("addition"))
                .andExpect(jsonPath("$.operand1").value(5.0))
                .andExpect(jsonPath("$.operand2").value(3.0))
                .andExpect(jsonPath("$.result").value(8.0))
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    public void testSubtractEndpoint() throws Exception {
        // Mock the calculation service response
        CalculationResponse mockResponse = new CalculationResponse("test-id", "subtraction", 10.0, 4.0, 6.0, true, null);
        when(calculationKafkaService.performCalculation("subtract", 10.0, 4.0)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/calculator/subtract")
                .param("a", "10.0")
                .param("b", "4.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("subtraction"))
                .andExpect(jsonPath("$.operand1").value(10.0))
                .andExpect(jsonPath("$.operand2").value(4.0))
                .andExpect(jsonPath("$.result").value(6.0))
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    public void testMultiplyEndpoint() throws Exception {
        // Mock the calculation service response
        CalculationResponse mockResponse = new CalculationResponse("test-id", "multiplication", 6.0, 7.0, 42.0, true, null);
        when(calculationKafkaService.performCalculation("multiply", 6.0, 7.0)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/calculator/multiply")
                .param("a", "6.0")
                .param("b", "7.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("multiplication"))
                .andExpect(jsonPath("$.operand1").value(6.0))
                .andExpect(jsonPath("$.operand2").value(7.0))
                .andExpect(jsonPath("$.result").value(42.0))
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    public void testDivideEndpoint() throws Exception {
        // Mock the calculation service response
        CalculationResponse mockResponse = new CalculationResponse("test-id", "division", 15.0, 3.0, 5.0, true, null);
        when(calculationKafkaService.performCalculation("divide", 15.0, 3.0)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/calculator/divide")
                .param("a", "15.0")
                .param("b", "3.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("division"))
                .andExpect(jsonPath("$.operand1").value(15.0))
                .andExpect(jsonPath("$.operand2").value(3.0))
                .andExpect(jsonPath("$.result").value(5.0))
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    public void testDivideByZero() throws Exception {
        // Mock the calculation service response for division by zero
        CalculationResponse mockResponse = new CalculationResponse("test-id", "division", 10.0, 0.0, 0.0, false, "Division by zero is not allowed");
        when(calculationKafkaService.performCalculation("divide", 10.0, 0.0)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/calculator/divide")
                .param("a", "10.0")
                .param("b", "0.0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error").value("Division by zero is not allowed"));
    }

    @Test
    public void testHealthEndpoint() throws Exception {
        mockMvc.perform(get("/api/calculator/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.service").value("Calculator REST API"))
                .andExpect(jsonPath("$.version").value("1.0.0"));
    }
} 