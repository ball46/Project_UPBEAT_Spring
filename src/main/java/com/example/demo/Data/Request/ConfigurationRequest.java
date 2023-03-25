package com.example.demo.Data.Request;

import lombok.Data;

@Data
public class ConfigurationRequest {
    private long rows;
    private long cols;
    private long initialPlanMinutes;
    private long initialPlanSeconds;
    private long initialBudget;
    private long initialCenterDeposit;
    private long planRevisionMinutes;
    private long planRevisionSeconds;
    private long revisionCost;
    private long maxDeposit;
    private long interestRatePercentage;
}
