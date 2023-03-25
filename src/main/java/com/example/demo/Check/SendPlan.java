package com.example.demo.Check;

import Game_state.Game.Game;
import com.example.demo.Data.Request.SendPlanRequest;
import com.example.demo.Service.ConstructionPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/check")
public class SendPlan {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/sendPlan")
    public ResponseEntity<Game> sendPlan(@RequestBody SendPlanRequest sendPlanRequest) {
        try {
            if(sendPlanRequest.getPlan() == null || sendPlanRequest.getPlan().isEmpty()) {
                throw new IllegalStateException("You must provide a plan");
            }else if(sendPlanRequest.getName() == null){
                throw new IllegalStateException("You must provide a name");
            }
            Game game = ConstructionPlan.SendPlan(sendPlanRequest.getPlan(), sendPlanRequest.getName());
            simpMessagingTemplate.convertAndSend("/topic/updatePlan", game);
            return ResponseEntity.ok(game);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
