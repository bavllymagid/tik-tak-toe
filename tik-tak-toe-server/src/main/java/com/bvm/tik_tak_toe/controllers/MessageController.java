package com.bvm.tik_tak_toe.controllers;

import com.bvm.tik_tak_toe.exceptions.exception.InvalidMoveException;
import com.bvm.tik_tak_toe.exceptions.exception.NoSuchGameFoundException;
import com.bvm.tik_tak_toe.model.GameMove;
import com.bvm.tik_tak_toe.model.GameState;
import com.bvm.tik_tak_toe.services.GameServiceImpl;
import com.bvm.tik_tak_toe.utils.BoardManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    private static final Logger log = LoggerFactory.getLogger(MessageController.class);
    private final GameServiceImpl gameService;

    @Autowired
    public MessageController(GameServiceImpl gameService) {
        this.gameService = gameService;
    }

    @MessageMapping("/move/{gameId}")
    @SendTo("/topic/game/{gameId}")
    public GameState processMove(@DestinationVariable String gameId, @Payload GameMove move) throws InvalidMoveException,
            NoSuchGameFoundException {
        return gameService.updateGame(gameId, move);
    }

    @MessageMapping("/join/{gameId}")
    @SendTo("/topic/game/{gameId}")
    public GameState joinGame(@DestinationVariable String gameId) throws NoSuchGameFoundException {
        GameState gameState = gameService.getGame(gameId);
        if (gameState == null) {
            throw new NoSuchGameFoundException("No such game found");
        }
        log.debug("Player joined game with id: {}", gameState.getPlayer2());
        return gameState;
    }

    @MessageMapping("/over/{gameId}")
    @SendTo("/topic/game/{gameId}")
    public GameState endGame(@DestinationVariable String gameId) {
        gameService.endGame(gameId);
        return null;
    }
}
