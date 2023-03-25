package com.example.demo.Data.Responses;

import Game_state.Player.*;
import Game_state.Region.*;
import lombok.Data;

import java.util.List;

@Data
public class CreateGameResponses {
    List<Region> territory;
    Player player1;
    Player player2;
}
