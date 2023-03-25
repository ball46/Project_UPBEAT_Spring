package com.example.demo.Interning;

import Game_state.Game.*;

import java.util.HashMap;
import java.util.Map;

public class CreateNewGame {
    private static final Map<String, Game> map = new HashMap<>();

    public static Game getGame(String P1, String P2, String config) {
        String hash = P1 + P2;
        if(map.isEmpty() || !map.containsKey(hash)){
            ReadData.getDataFile(config);
            Game game = ReadData.cretateGame(P1, P2);
            map.put(hash, game);
            return game;
        }
        return map.get(hash);
    }
}