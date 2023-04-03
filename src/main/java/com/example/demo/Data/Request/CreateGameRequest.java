package com.example.demo.Data.Request;

import lombok.Data;

@Data
public class CreateGameRequest {
    private String P1Name = "ball";
    private String P2Name = "boss";
    private ConfigurationRequest config;
}
