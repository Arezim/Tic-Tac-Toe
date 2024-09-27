import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Main class. By default, plays a game where moves are selected uniformly
 * at random. Students may modify this to implement AI-driven decision-making.
 * 
 * @author Dennis Soemers
 */
public class Main {

	// A simple class to store a move and its value
	public static class MoveValue {
		public int move;
		public int value;

		public MoveValue(int move, int value) {
			this.move = move;
			this.value = value;
		}
	}

	public static void main(final String[] args) {

		// Initialize the game state
		final GameState gameState = new GameState("x"); // <-- specify starting player here (x or o)
		// Optionally apply some initial moves

		gameState.applyMove(4);
		gameState.applyMove(0);
		gameState.applyMove(6);
		gameState.applyMove(3);

		// Print the initial board
		System.out.println("Initial board:");
		gameState.printBoard();

		int numMoves = 0;
		int winner = 0; // initialize as draw
		List<Integer> movesMade = new ArrayList<>();

		// Simulate the game
		while (!gameState.isGameOver()) {
			MoveValue mv = null;
			if (gameState.getCurrentPlayer() == GameState.PLAYER_1) {
				// Max player
				mv = maxValue(gameState);
			} else {
				// Min player
				mv = minValue(gameState);
			}

			if (mv.move == -1) {
				// if no move is possible, break the loop and end the game
				break;
			}

			gameState.applyMove(mv.move);
			movesMade.add(mv.move);
			gameState.printBoard();
			numMoves++;
		}

		winner = gameState.getWinner();

		System.out.println("Game ended after " + numMoves + " moves.");
		System.out.println("Winner: " + (winner == 1 ? "X" : winner == -1 ? "O" : "Draw"));
		System.out.print("Moves made: ");
		for (int move : movesMade) {
			System.out.print(move + " ");
		}
		System.out.println();
	}


	private static MoveValue maxValue(GameState gameState){
		if(gameState.isGameOver()){
			// move == -1 indicates no move
			return new MoveValue(-1, gameState.getWinner());
		}
		List<Integer> legalMoves = new ArrayList<>(gameState.getLegalMoves());
		int value = Integer.MIN_VALUE;
		int bestMove = -1;
		for(int move : legalMoves){
			gameState.applyMove(move);
			MoveValue mv = minValue(gameState);
			if(mv.value > value){
				value = mv.value;
				bestMove = move;
			}
			gameState.undoMove(move);
		}
		return new MoveValue(bestMove, value);
	}


	private static MoveValue minValue(GameState gameState){
		if(gameState.isGameOver()){
			// move == -1 indicates no move
			return new MoveValue(-1, gameState.getWinner());
		}
		List<Integer> legalMoves = new ArrayList<>(gameState.getLegalMoves());
		int value = Integer.MAX_VALUE;
		int bestMove = -1;
		for(int move : legalMoves){
			gameState.applyMove(move);
			MoveValue mv = maxValue(gameState);
			if(mv.value < value){
				value = mv.value;
				bestMove = move;
			}
			gameState.undoMove(move);
		}
		return new MoveValue(bestMove, value);
	}
}


