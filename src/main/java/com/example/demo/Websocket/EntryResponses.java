package com.example.demo.Websocket;

import com.example.demo.Data.Request.ConfigurationRequest;
import lombok.Data;

@Data
public class EntryResponses {
    private String nameP1;
    private String nameP2;
    private ConfigurationRequest configuration;
}
