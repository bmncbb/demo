package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CalculationKafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(CalculationKafkaConsumer.class);
    
    @Autowired
    private CalculatorService calculatorService;
    
    @Autowired
    private KafkaTemplate<String, CalculationResponse> kafkaTemplate;

    @KafkaListener(topics = "calculation-requests", groupId = "calculator-group")
    public void handleCalculationRequest(CalculationRequest request) {
        logger.info("Received calculation request {} with id {}", request, request.getRequestId());
        
        CalculationResponse response;
        
        try {
            double result = performCalculation(request);
            response = CalculationResponse.success(
                request.getRequestId(),
                request.getOperation(),
                request.getOperand1(),
                request.getOperand2(),
                result
            );
            logger.info("Calculation successful: {} = {}", request.getOperation(), result);
        } catch (Exception e) {
            logger.error("Calculation failed for request {}: {}", request.getRequestId(), e.getMessage());
            response = CalculationResponse.error(
                request.getRequestId(),
                request.getOperation(),
                request.getOperand1(),
                request.getOperand2(),
                e.getMessage()
            );
        }
        
        // Send response back to Kafka
        kafkaTemplate.send("calculation-responses", request.getRequestId(), response);
        logger.info("Sent calculation response: {}", response);
    }

    private double performCalculation(CalculationRequest request) {
        switch (request.getOperation().toLowerCase()) {
            case "add":
            case "addition":
                return calculatorService.add(request.getOperand1(), request.getOperand2());
            case "subtract":
            case "subtraction":
                return calculatorService.subtract(request.getOperand1(), request.getOperand2());
            case "multiply":
            case "multiplication":
                return calculatorService.multiply(request.getOperand1(), request.getOperand2());
            case "divide":
            case "division":
                return calculatorService.divide(request.getOperand1(), request.getOperand2());
            default:
                throw new IllegalArgumentException("Unsupported operation: " + request.getOperation());
        }
    }
} 