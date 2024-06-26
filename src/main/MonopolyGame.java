package main;

import java.util.List;

import components.Board;
import components.MonopolyPiece;
import components.Player;
import actives.DecisionMaker;
import actives.MaxDecisionMaker;
import actives.Turn;

public class MonopolyGame {
    private List<Player> players;
    private Board board;

    public MonopolyGame(List<Player> players) {
        this.players = players;
        this.board = new Board();
    }

    public void playGame() {
        boolean gameEnded = false;
        while (!gameEnded) {
            for (Player player : players) {
                DecisionMaker decisionMaker = player.getDecisionMaker();
                Turn turn = new Turn(player, board, decisionMaker);
                turn.execute();

                // Add logic to check if the game has ended
            }
        }
    }

    public static void main(String[] args) {
        // Initialize players and start the game
        Player player1 = new Player("Alice", MonopolyPiece.CAT, new MaxDecisionMaker());
        Player player2 = new Player("Bob", MonopolyPiece.DOG, new MaxDecisionMaker());
        List<Player> players = List.of(player1, player2);

        MonopolyGame game = new MonopolyGame(players);
        game.playGame();
    }
}
