package com.example.demo.Websocket;

import com.example.demo.Data.Request.ConfigurationRequest;
import com.example.demo.Data.Responses.PlayerResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import java.security.Principal;

@RestController
@MessageMapping("/start")
public class GameStart {

    private final PlayerResponses[] playerResponses = new PlayerResponses[2];
    private int connectedPlayers = 0;
    private int editingPlayers = 0;
    private ConfigurationRequest configuration;
    private String  nameP1, nameP2;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    //add name and return it
    @MessageMapping("/addName")
    @SendTo("/topic/name")
    public NameResponses addName(@RequestBody NameRequest nameRequest) {
        NameResponses nameResponses = new NameResponses();
        nameResponses.setNameP1(nameRequest.getNameP1());
        nameResponses.setNameP2(nameRequest.getNameP2());
        this.nameP1 = nameRequest.getNameP1();
        this.nameP2 = nameRequest.getNameP2();
        return nameResponses;
    }

    //check 2 players is ready and return it
    @MessageMapping("/getReady")
    @SendTo("/topic/ready")
    public boolean gameReady(){
        nameP1 = "";
        nameP2 = "";
        configuration = null;
        return true;
    }

    //set data
    @SubscribeMapping("/entry")
    public EntryResponses entry(){
        EntryResponses entryResponses = new EntryResponses();
        entryResponses.setNameP1(nameP1);
        entryResponses.setNameP2(nameP2);
        if(configuration != null) entryResponses.setConfiguration(configuration);
        return entryResponses;
    }

    // this method is updated configuration
    @MessageMapping("/configuration")
    @SendTo("/topic/configuration")
    public ConfigurationRequest updateConfiguration(@RequestBody ConfigurationRequest configuration){
        this.configuration = configuration;
        return this.configuration;
    }

    //To check now who is playing
    @MessageMapping("/checkPlayer")
    public void PlayerResponses(final Principal principal){
        simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/topic/playerResponses", playerResponses[editingPlayers]);
    }

    //check players is connected
    @EventListener(SessionConnectedEvent.class)
    public void PlayerIsConnected(SessionConnectedEvent sessionConnectedEvent) {
        connectedPlayers++;
        setPlayerResponses(sessionConnectedEvent.getUser());
        System.out.println("Player " + connectedPlayers + " player is connected");
    }

    //check players is disconnected
    @EventListener(SessionConnectedEvent.class)
    public void PlayerIsDisconnected(SessionConnectedEvent sessionConnectedEvent) {
        connectedPlayers--;
        resetPlayerResponses(sessionConnectedEvent.getUser());
        System.out.println("Player " + connectedPlayers + " player is disconnected");
    }

    //if players is connected, set player responses
    private void setPlayerResponses(Principal principal){
        if(playerResponses[0] == null){
            playerResponses[0] = new PlayerResponses();
            playerResponses[0].setPlayerID(1);
            playerResponses[0].setPlayerName(principal.getName());
            editingPlayers = 0;
        }else if(playerResponses[1] == null){
            playerResponses[1] = new PlayerResponses();
            playerResponses[1].setPlayerID(2);
            playerResponses[1].setPlayerName(principal.getName());
            editingPlayers = 1;
        }
    }

    //if players is disconnected, reset player responses
    private void resetPlayerResponses(Principal principal){
        if(playerResponses[0] != null && playerResponses[0].getPlayerName().equals(principal.getName())){
            playerResponses[0] = null;
        }else if(playerResponses[1]!= null && playerResponses[1].getPlayerName().equals(principal.getName())){
            playerResponses[1] = null;
        }
    }
}