package com.example;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CalculationRequest {
    
    @JsonProperty("operation")
    private String operation;
    
    @JsonProperty("operand1")
    private double operand1;
    
    @JsonProperty("operand2")
    private double operand2;
    
    @JsonProperty("requestId")
    private String requestId;

    // Default constructor for JSON deserialization
    public CalculationRequest() {}

    public CalculationRequest(String operation, double operand1, double operand2, String requestId) {
        this.operation = operation;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.requestId = requestId;
    }

    // Getters and Setters
    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public double getOperand1() {
        return operand1;
    }

    public void setOperand1(double operand1) {
        this.operand1 = operand1;
    }

    public double getOperand2() {
        return operand2;
    }

    public void setOperand2(double operand2) {
        this.operand2 = operand2;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return "CalculationRequest{" +
                "operation='" + operation + '\'' +
                ", operand1=" + operand1 +
                ", operand2=" + operand2 +
                ", requestId='" + requestId + '\'' +
                '}';
    }
} 