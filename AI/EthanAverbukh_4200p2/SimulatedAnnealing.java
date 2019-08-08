package nQueen;

import java.text.NumberFormat;
import java.util.*;

/**
 * @file SimulatedAnnealing.java
 * @author nsquires
 * Implements the Simulated Annealing algorithm
 */
import java.util.*;

public class SimulatedAnnealing {
	private final static int N=8;
	int nodesGenerated;
	private boardState[] start;
	private Node initial;
	
	public SimulatedAnnealing() {
		
	}
	public SimulatedAnnealing(boardState[] s){
		nodesGenerated = 0;
		initial = new Node();
		start = new boardState[N];
		
		for(int i=0; i<N; i++){
			start[i] = new boardState(s[i].getRow(), s[i].getColumn());
		}
		initial.setState(start);
		initial.computeHeuristic();
	}
	
	/*
	  Initializes queens on
	  a board
	 */
	public void startState(){
		initial = new Node();
		start = new boardState[N];
		Random gen = new Random();
		
		for(int i=0; i<N; i++){
			start[i] = new boardState(gen.nextInt(N), i);
		}
		initial.setState(start);
		initial.computeHeuristic();
	}
	
	
	public Node simulatedAnneal(double initialTemp, double step){
		Node currentNode = initial;
		double temperature = initialTemp;
		double val = step;
		double probability;
		int delta;
		double determine;
		
		Node nextNode = new Node();
		
		while(currentNode.getHeuristic()!=0 && temperature > 0){
			//select a random neighbor from currentNode
			nextNode = currentNode.getRandomNeighbor(currentNode);
			nodesGenerated++;
			
			if(nextNode.getHeuristic()==0)
				return nextNode;
			
			delta = currentNode.getHeuristic() - nextNode.getHeuristic();
			
			if(delta > 0){ //currentNode has a higher heuristic
				currentNode = nextNode;
			}else{ 
				probability = Math.exp(delta/temperature);
				//Do we want to choose nextNode or stick with currentNode?
				determine = Math.random();
				
				if(determine <= probability){ //choose nextNode
					currentNode = nextNode;
				}
			}
			temperature = temperature - val;
		}
		
		return currentNode;
	}
	
	
	public int getNodesGenerated(){
		return nodesGenerated;
	}
	

	public Node getStartNode(){
		return initial;
	}
	
	public static void main(String[] args){
		SimulatedAnnealing board = new SimulatedAnnealing();
		int numberOfRuns = 200;
		int annealNodes=0;
		int annealSuccesses=0;
		Node annealSolved;
		String currentState = "";
		for(int i=0; i<numberOfRuns; i++){
			boardState[] startBoard = board.generateBoard();
	
			SimulatedAnnealing anneal = new SimulatedAnnealing(startBoard);			
	
			 annealSolved = anneal.simulatedAnneal(28, 0.0001);
			 currentState = annealSolved.toString();
			 
			if(annealSolved.getHeuristic()==0){
				annealSuccesses++;
			}
			annealNodes += anneal.getNodesGenerated();
		}
		System.out.print(currentState);
		System.out.println("Simulated Annealing successes: "+annealSuccesses);
		
		double annealPercent = ((double)annealSuccesses/numberOfRuns);
		NumberFormat fmt = NumberFormat.getPercentInstance();
		
		System.out.println("Simulated Annealing:\nNodes: "+annealNodes);
		System.out.println("Percent successes: "+fmt.format(annealPercent));
	}
	
	//generate a board;
	public boardState[] generateBoard(){
		boardState[] start = new boardState[8];
		Random gen = new Random();
		
		for(int i=0; i<8; i++){
			start[i] = new boardState(gen.nextInt(8),i);
		}
		return start;
	}
}