package com.bvm.tic_tac_toe.model;

import lombok.Data;

@Data
public class GameState {
    private String gameId;
    private String[][] board;
    private String currentPlayer;
    private String winner;
    private boolean isDraw;
    private Player player1;
    private Player player2;

    public GameState() {
        this.board = new String[3][3];
        this.winner = "";
        this.isDraw = false;
    }
}
