package nQueen;

import java.util.ArrayList;
import java.util.Random;

public class Node implements Comparable<Node> {

	private static final int N=8; //8 queens
	public boardState[] state; //the node's state
	private ArrayList<Node> neighbors;
	private int heuristic; //heuristic score
	
	
	public Node(){
		state = new boardState[N]; // initial state
		neighbors = new ArrayList<Node>(); //empty neighbor list
	} 
	
	/**
	 * Constructor which creates a copy of a node's state
	 * @param n
	 */
	public Node(Node n){
		state = new boardState[N];
		neighbors = new ArrayList<Node>();
		for(int i=0; i<N; i++)
			state[i] = new boardState(n.state[i].getRow(), n.state[i].getColumn());
		heuristic=0;
	}
	
	/**
	 * Generates the possible chess board configurations given a
	 * specific board state
	 * @param startState
	 */
	public ArrayList<Node> generateNeighbors(Node startState){
		int count=0;
		
		if(startState==null)
			System.out.println("warning");
		else{

		}
		
		for(int i=0; i<N; i++){
			for(int j=1; j<N; j++){
				neighbors.add(count, new Node(startState));
				neighbors.get(count).state[i].moveDown(j);
				//make sure to compute its heuristic value
				neighbors.get(count).computeHeuristic();
				
				count++;
			}
		}
		
		return neighbors;
	}
	
	/**
	 * Returns a randomly generated neighbour of a given state
	 * @param startState
	 * @return
	 */
	public Node getRandomNeighbor(Node startState){
		Random gen = new Random();
		
		int col = gen.nextInt(N);
		int d = gen.nextInt(N-1)+1;
		
		Node neighbor = new Node(startState);
		neighbor.state[col].moveDown(d);
		neighbor.computeHeuristic();
		
		return neighbor;
	}
	
	/**
	 * Calculates number of queens attacking each other
	 */
	public int computeHeuristic(){
	
		for(int i=0; i<N-1; i++){
			for(int j=i+1; j<N; j++){
				if(state[i].canAttack(state[j])){
						heuristic++;
				}
			}
		}
		
		return heuristic;
	}
	

	public int getHeuristic(){
		return heuristic;
	}
	

	public int compareTo(Node n){
		if(this.heuristic < n.getHeuristic())
			return -1;
		else if(this.heuristic > n.getHeuristic())
			return 1;
		else 
			return 0;
	}
	
	
	public void setState(boardState[] s){
		for(int i=0; i<N; i++){
			state[i]= new boardState(s[i].getRow(), s[i].getColumn());
		}
	}
	
	

	public boardState[] getState(){
		return state;
	}
	
	
	public String toString(){
		String result="";
		String[][] board = new String[N][N];
		
		//initialise board with X's to indicate empty spaces
		for(int i=0; i<N; i++)
			for(int j=0; j<N; j++)
				board[i][j]="X ";
		
		//place the queens on the board
		for(int i=0; i<N; i++){
			board[state[i].getRow()][state[i].getColumn()]="Q ";
		}
		
		//feed values into the result string
		for(int i=0; i<N; i++){
			for(int j=0; j<N; j++){
				result+=board[i][j];
			}
			result+="\n";
		}
		
		return result;
	}
}
