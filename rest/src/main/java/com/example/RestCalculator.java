package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/calculator")
@CrossOrigin(origins = "*")
public class RestCalculator {

    @Autowired
    private CalculationKafkaService calculationKafkaService;

    @GetMapping("/add")
    public ResponseEntity<Map<String, Object>> add(
            @RequestParam double a,
            @RequestParam double b) {
        
        Map<String, Object> response = new HashMap<>();
        try {
            CalculationResponse calcResponse = calculationKafkaService.performCalculation("add", a, b);
            
            if (calcResponse.isSuccess()) {
                response.put("operation", calcResponse.getOperation());
                response.put("operand1", calcResponse.getOperand1());
                response.put("operand2", calcResponse.getOperand2());
                response.put("result", calcResponse.getResult());
                response.put("success", true);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", calcResponse.getError());
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error performing addition: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/subtract")
    public ResponseEntity<Map<String, Object>> subtract(
            @RequestParam double a,
            @RequestParam double b) {
        
        Map<String, Object> response = new HashMap<>();
        try {
            CalculationResponse calcResponse = calculationKafkaService.performCalculation("subtract", a, b);
            
            if (calcResponse.isSuccess()) {
                response.put("operation", calcResponse.getOperation());
                response.put("operand1", calcResponse.getOperand1());
                response.put("operand2", calcResponse.getOperand2());
                response.put("result", calcResponse.getResult());
                response.put("success", true);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", calcResponse.getError());
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error performing subtraction: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/multiply")
    public ResponseEntity<Map<String, Object>> multiply(
            @RequestParam double a,
            @RequestParam double b) {
        
        Map<String, Object> response = new HashMap<>();
        try {
            CalculationResponse calcResponse = calculationKafkaService.performCalculation("multiply", a, b);
            
            if (calcResponse.isSuccess()) {
                response.put("operation", calcResponse.getOperation());
                response.put("operand1", calcResponse.getOperand1());
                response.put("operand2", calcResponse.getOperand2());
                response.put("result", calcResponse.getResult());
                response.put("success", true);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", calcResponse.getError());
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error performing multiplication: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/divide")
    public ResponseEntity<Map<String, Object>> divide(
            @RequestParam double a,
            @RequestParam double b) {
        
        Map<String, Object> response = new HashMap<>();
        try {
            CalculationResponse calcResponse = calculationKafkaService.performCalculation("divide", a, b);
            
            if (calcResponse.isSuccess()) {
                response.put("operation", calcResponse.getOperation());
                response.put("operand1", calcResponse.getOperand1());
                response.put("operand2", calcResponse.getOperand2());
                response.put("result", calcResponse.getResult());
                response.put("success", true);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", calcResponse.getError());
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error performing division: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "Calculator REST API");
        response.put("version", "1.0.0");
        return ResponseEntity.ok(response);
    }
}
