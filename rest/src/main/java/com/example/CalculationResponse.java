package com.example;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CalculationResponse {
    
    @JsonProperty("requestId")
    private String requestId;
    
    @JsonProperty("operation")
    private String operation;
    
    @JsonProperty("operand1")
    private double operand1;
    
    @JsonProperty("operand2")
    private double operand2;
    
    @JsonProperty("result")
    private double result;
    
    @JsonProperty("success")
    private boolean success;
    
    @JsonProperty("error")
    private String error;

    // Default constructor for JSON deserialization
    public CalculationResponse() {}

    public CalculationResponse(String requestId, String operation, double operand1, double operand2, double result, boolean success, String error) {
        this.requestId = requestId;
        this.operation = operation;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.result = result;
        this.success = success;
        this.error = error;
    }

    // Static factory methods for convenience
    public static CalculationResponse success(String requestId, String operation, double operand1, double operand2, double result) {
        return new CalculationResponse(requestId, operation, operand1, operand2, result, true, null);
    }

    public static CalculationResponse error(String requestId, String operation, double operand1, double operand2, String error) {
        return new CalculationResponse(requestId, operation, operand1, operand2, 0.0, false, error);
    }

    // Getters and Setters
    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

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

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "CalculationResponse{" +
                "requestId='" + requestId + '\'' +
                ", operation='" + operation + '\'' +
                ", operand1=" + operand1 +
                ", operand2=" + operand2 +
                ", result=" + result +
                ", success=" + success +
                ", error='" + error + '\'' +
                '}';
    }
} 