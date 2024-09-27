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
	
	public static void main(final String[] args) {

		
		int numMoves = 0;
		int winner = 0;

		System.out.print("Who starts (x/o)? : ");
		Scanner sc = new Scanner(System.in);
		String player = sc.nextLine();
		if (player.equalsIgnoreCase("x")) {
			final GameState gameState = new GameState(player.toLowerCase());
			winner = maxValue(gameState);
		} else if (player.equalsIgnoreCase("o")) {
			final GameState gameState = new GameState(player.toLowerCase());
			winner = minValue(gameState);
		} else {
			System.out.println("You can only choose x or o");
		}

		System.out.println("Game ended after " + numMoves + " moves.");
		System.out.println("Winner: " + (winner == 1 ? "X" : winner == -1 ? "O" : "Draw"));

	}

	// Minimax MAX function
	private static int maxValue(GameState gameState){

		if(gameState.isGameOver()){
			// return the winner (utility value of the state: -1, 0, or 1)
			return gameState.getWinner();
		}
		List<Integer> legalMoves = new ArrayList<>(gameState.getLegalMoves()); //java limitations - you have to copy
		int value = Integer.MIN_VALUE;
		for(int move : legalMoves){
			gameState.applyMove(move);
			value = Math.max(value, minValue(gameState));
			gameState.undoMove(move);
		}
		return value;
	}

	// Minimax MIN function
	private static int minValue(GameState gameState){
		if(gameState.isGameOver()){
			return gameState.getWinner();
		}
		List<Integer> legalMoves = new ArrayList<>(gameState.getLegalMoves()); //java limitations - you have to copy
		int value = Integer.MAX_VALUE;
		for(int move : legalMoves){
			gameState.applyMove(move);
			value = Math.min(value, maxValue(gameState));
			gameState.undoMove(move);
		}
		return value;
	}
}
