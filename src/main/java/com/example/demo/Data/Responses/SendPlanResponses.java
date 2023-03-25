package com.example.demo.Data.Responses;

import Game_state.Region.*;
import lombok.Data;

import java.util.List;
@Data
public class SendPlanResponses {
    List<Region> territory;
    double P1Budget;
    double P2Budget;
}
