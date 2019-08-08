package nQueen;
import java.util.Random;
import java.util.Scanner;

public class HillClimbing {
	private Random randOriginal = new Random(); // random number generator for inital state
	private Random randSuccessor = new Random(); // random number generator for successor boards
	private int[] originalBoard;
	
	// Values in the array represent the row, index of the array represents the column
	
	// Create new board with random value
	public HillClimbing(int[]board, int n) {
		originalBoard = new int[n];
		for(int i = 0; i < board.length;i++) {
			board[i] = randOriginal.nextInt(n);
			originalBoard[i] = board[i];
		}
	}
	
	public HillClimbing(int[]board) {
		originalBoard = new int[board.length];
		for(int i = 0; i < board.length; i++) {
			originalBoard[i] = board[i];
		}
	}
	public void setBoard(int[] board) {
		originalBoard = board;
	}
	
	// Create successor from initial state
	public int[] successor(int n) {
		int[] successor = new int[n];
		
		for(int i = 0; i < successor.length; i++) {
			successor[i] = randSuccessor.nextInt(n);
		}
		
		
		return  successor;
	}
	
	//print state of chess board
	public void printBoard() {
		for(int i = 0; i < originalBoard.length; i++) {
			System.out.print(originalBoard[i]);
		}
		System.out.print("\n");
	}
	
	public void print2dArray() {
		int N = originalBoard.length;
		int[][] board = new int[N][N];
		int pos = 0;
		while(pos != N) {
			for(int i = 0; i< N; i++ ) {

				if(originalBoard[pos] == i)
					board[i][pos]= originalBoard[pos];
				else
					board[i][pos] = 0;


			}
			pos++;
		}
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				System.out.print(board[i][j] + " ");		
			}
			System.out.print("\n");
		}
			
			
	}
	
	// calculate heuristic for Row attacks
	public int heuristicRowAttack() {
		int heuristic = 0;
		int column = 0;
		
		  while(column != originalBoard.length) {
			  for(int i = 0; i < originalBoard.length-1; i++) {
				  if(originalBoard[column] == originalBoard[i+1] && (i+1 > column))
					  heuristic++;
			  }
		  column++;
		  }
	return heuristic;
	}
	
	//calculates the diagnoal direct and indirect attacking queens
	public int heuristicDiagonalAttack() {
		int heuristic = 0;
		int column_MAX = originalBoard.length;
		int columnCheck = 0;
		int columnCheck_2 = 0;
		int value = originalBoard[columnCheck];
		
		while(columnCheck != originalBoard.length) {
			 value = originalBoard[columnCheck];
			for(int i = 1;i < originalBoard.length; i++) {
				
				if((value - i) >= 0 && (columnCheck + i < column_MAX) && ((value - i) == originalBoard[columnCheck+i])){
					heuristic++;
				
				}
			
			}
			
			columnCheck = (columnCheck < column_MAX) ? (columnCheck + 1) : column_MAX+1;
			
		}
		while(columnCheck_2 != originalBoard.length) {
			 value = originalBoard[columnCheck_2];
			for(int i = 1;i < originalBoard.length; i++) {
				
				if((value + i) < originalBoard.length && (columnCheck_2 + i < column_MAX) && ((value + i) == originalBoard[columnCheck_2+i])){
					heuristic++;
				
				}
			
			}
			
			columnCheck_2 = (columnCheck_2 < column_MAX) ? (columnCheck_2 + 1) : column_MAX+1;
			
		}
		 
		return heuristic;
	}
	// main call function
	public static void main(String[] args) {
		int run = 4;
		int value = 0;
		int value_next = 0;
		int instances = 0;
		int count = 0;
		int choice=0;
		int[] N_queens;
		int [] next;
		HillClimbing prac;
		HillClimbing successor;
		long startTime = System.currentTimeMillis();
		while(instances < 501) {
			Scanner in = new Scanner(System.in);
			//System.out.println("Choose size of chess board: "); can un comment to select own size of board
			// choice = in.nextInt();
			choice = 8;
			
			N_queens = new int[choice];
			next = new int [choice];
			prac = new HillClimbing(N_queens,choice);
			prac.printBoard();
			prac.print2dArray();


			value = prac.heuristicRowAttack(); // checks Row attacks for initial board
			value= value + prac.heuristicDiagonalAttack(); // adds diagonal attacks to current heu value
			next = prac.successor(choice); // creates next state
			successor = new HillClimbing(next,choice);
			value_next = successor.heuristicRowAttack();
			value_next= value_next + successor.heuristicDiagonalAttack(); //  heu value calculation for next state

			if(value_next < value) { // picks the value that is best and sets new state
				prac = successor;
				count++;
			}

			if(value == 0 || value_next == 0) {
				System.out.print("Solution found!");
				break;
			}

			

			if(instances>=500 && (value !=0 || value_next !=0))
			{
				System.out.print("No Solution found after " + instances + " runs, heuristic value: " + "\n");
				System.out.print("Final H value: " +value+ "\n");
				System.out.print("Total Moves: " + count + "\n");
				
				prac.print2dArray();
			}

			long stopTime = System.currentTimeMillis();
			long elapsedTime = stopTime- startTime;
			System.out.print("Average Run Time: " + (String.format("%.2f", (double)elapsedTime/instances) + "ms" + "\n"));
			instances++;
			//in.close();
		}
		

	}
}
