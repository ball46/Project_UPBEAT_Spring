package com.example.demo.Check;

import Game_state.Game.Game;
import com.example.demo.Data.Request.ConfigurationRequest;
import com.example.demo.Data.Request.CreateGameRequest;
import com.example.demo.Interning.CreateNewGame;
import com.example.demo.Service.ConstructionPlan;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/check")
public class CreateGame {

    @PostMapping("/createGame")
    public ResponseEntity<Object> createGame(@RequestBody CreateGameRequest createGameRequest){
        try{
            if(createGameRequest.getP1Name() == null || createGameRequest.getP2Name() == null){
                throw new NullPointerException("Player names cannot be null");
            }else{
                String config = convertDataConfigToString(createGameRequest.getConfig());
                Game game = CreateNewGame.getGame(createGameRequest.getP1Name(), createGameRequest.getP2Name(), config);
                ConstructionPlan.setGame(game);
                return ResponseEntity.ok(game);
            }
        }catch (Exception e){
            throw new IllegalStateException(e.getMessage());
        }
    }

    private String convertDataConfigToString(ConfigurationRequest configuration){
        String result = "row = " + configuration.getRows() + "\n";
        result += "col = " + configuration.getCols() + "\n";
        result += "initPlanMin = " + configuration.getInitialPlanMinutes() + "\n";
        result += "initPlanSec = " + configuration.getInitialPlanSeconds() + "\n";
        result += "initBudget = " + configuration.getInitialBudget() + "\n";
        result += "initCenterDep = " + configuration.getInitialCenterDeposit() + "\n";
        result += "planRevMin = " + configuration.getPlanRevisionMinutes() + "\n";
        result += "planRevSec = " + configuration.getPlanRevisionSeconds() + "\n";
        result += "revCost = " + configuration.getRevisionCost() + "\n";
        result += "maxDep = " +configuration.getMaxDeposit() + "\n";
        result += "interestPct = " + configuration.getInterestRatePercentage() + "\n";
        return result;
    }
}
