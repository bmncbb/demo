package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class CalculationKafkaService {

    private static final Logger logger = LoggerFactory.getLogger(CalculationKafkaService.class);
    
    @Autowired
    private KafkaTemplate<String, CalculationRequest> kafkaTemplate;
    
    // Store pending requests with their CompletableFuture
    private final Map<String, CompletableFuture<CalculationResponse>> pendingRequests = new ConcurrentHashMap<>();

    public CalculationResponse performCalculation(String operation, double operand1, double operand2) {
        String requestId = UUID.randomUUID().toString();
        
        // Create a CompletableFuture to wait for the response
        CompletableFuture<CalculationResponse> future = new CompletableFuture<>();
        pendingRequests.put(requestId, future);
        
        // Create and send the request
        CalculationRequest request = new CalculationRequest(operation, operand1, operand2, requestId);
        logger.info("Sending calculation request: {}", request);
        
        kafkaTemplate.send("calculation-requests", requestId, request);
        
        try {
            // Wait for the response with a timeout
            CalculationResponse response = future.get(10, TimeUnit.SECONDS);
            logger.info("Received calculation response: {}", response);
            return response;
        } catch (Exception e) {
            logger.error("Error waiting for calculation response: {}", e.getMessage());
            // Remove the pending request
            pendingRequests.remove(requestId);
            // Return an error response
            return CalculationResponse.error(requestId, operation, operand1, operand2, 
                    "Timeout or error waiting for calculation: " + e.getMessage());
        }
    }

    @KafkaListener(topics = "calculation-responses", groupId = "rest-group")
    public void handleCalculationResponse(CalculationResponse response) {
        logger.info("Received calculation response: {}", response);
        
        // Find the corresponding pending request and complete it
        CompletableFuture<CalculationResponse> future = pendingRequests.remove(response.getRequestId());
        if (future != null) {
            future.complete(response);
        } else {
            logger.warn("Received response for unknown request ID: {}", response.getRequestId());
        }
    }
} 