package com.example.demo.Service;

import Game_state.Game.*;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
public class ConstructionPlan {
    @Setter
    private static Game game;

    public static Game SendPlan(String plan, String player) throws RuntimeException {
        try {
            if(game.getCurrentPlayer().getName().equals(player)) {
                game.sendPlan(plan);
                return game;
            }else throw new RuntimeException("It's not your turn");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
