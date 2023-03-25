package com.example.demo.Data.Request;

import lombok.Data;

@Data
public class CreateGameRequest {
    private String P1Name;
    private String P2Name;
    private ConfigurationRequest config;
}
