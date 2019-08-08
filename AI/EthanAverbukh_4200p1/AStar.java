package intelligentAgent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class AStar {
    //public Solver(Board initial)            // find a solution to the initial board (using the A* algorithm)
    //public boolean isSolvable()             // is the initial board solvable?
    //public int moves()                      // min number of moves to solve initial board; -1 if no solution
    //public Iterable<Board> solution()       // sequence of boards in a shortest solution; null if no solution
    //public static void main(String[] args)  // solve a slider puzzle (given below)
    // you will want to use comparator for hamming & man
    //MinPq<node> frontier as the priority queue

    private Node goalNode; 
    int nodeCounter = 0;
    private MinPQ<Node> frontier = new MinPQ<Node>();
    private MinPQ<Node> frontierTwin = new MinPQ<Node>();  
    
    public class Node implements Comparable<Node>{
        public Board board;
        public Node previous;
        public int moves;

        public int compareTo(Node that){
            if(this.priority() == that.priority()) return 0;
            return (this.priority() > that.priority()) ? 1 :  -1; 
        }
        
        public Node(Board b, Node prev, int m){
            board = b;
            previous = prev;
            moves = m;
        }
        
        public int priority(){
            return board.manhattan() + moves;
        } 
    } 
    
    public AStar(Board initial){
        Board initialBoard;
       
        initialBoard = initial;
        
        Node currentNode = new Node(initial, null, 0);
        nodeCounter++;
        Node currentTwin = new Node(initial.twin(), null, 0);
        frontier.insert(currentNode);
        frontierTwin.insert(currentTwin);
        
        while(!currentNode.board.isGoal() && !currentTwin.board.isGoal()){

            currentNode = frontier.delMin();
            currentTwin = frontierTwin.delMin();
                     
            for(Board b : currentNode.board.findNeighbors()) {
                if(!b.equals(currentNode.board))
                    frontier.insert(new Node(b, currentNode, currentNode.moves +1)); 
                	nodeCounter++;
            }
            
            for(Board b : currentTwin.board.findNeighbors()) {
                if(!b.equals(currentNode.board))
                    frontierTwin.insert(new Node(b, currentTwin, currentTwin.moves +1)); 
            }
        }
        
        if(currentNode.board.isGoal())
            goalNode = currentNode;
        else
            goalNode = currentTwin;
    }
    
    public Iterable<Board> solutionPath(){
        ArrayList<Board> path = new ArrayList<Board>();
        path.add(goalNode.board);
        while (goalNode.previous != null){
            goalNode = goalNode.previous;
            path.add(goalNode.board);
        }
        
        return path;
    }
    
    public boolean isSolvable(){
        return goalNode != null;
    }
    
    public int moves(){
        return goalNode.moves;
    }
    
	  private static ArrayList<Integer> randomNumGen() {
	  
	  
		  ArrayList<Integer> numbers = new ArrayList<>();
		  
		  for(int i = 0; i<9; i++) { 
			  numbers.add(i); 
		  } 
		  
		  Collections.shuffle(numbers);
		  
		  return numbers;
	  }
	 
    
    public static void main(String[] args) throws IOException  {
        // create initial board from file
    	Scanner in = new Scanner(System.in);
    	Scanner puzzlePieces = new Scanner(System.in);
        int version = 1;
        int N = 3;
        int[][] blocks = new int[N][N];
        
     // Only used for 3 sample outputs to be written to file
     	//FileOutputStream writer = new FileOutputStream("output_"+ version+ ".txt");
      //BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(writer));
	        System.out.println("Choose option: ");
	        System.out.println("1. Random puzzle generation.");
	        System.out.println("2. Input from user.(Program hangs only choose 1)");
	        int n = in.nextInt();
	        
	       
	        if(n == 1) {
		        	while(version < 100) {
		        		 long startTime = System.currentTimeMillis();
		                 version++;
		                 
		                 
		                 int index = 0;
		                 ArrayList<Integer> random = new ArrayList<Integer>();
		                 random = randomNumGen();
			        
					
					
		                 for (int i = 0; i < N; i++) { 
		                	 for (int j = 0; j < N; j++) { 
		                		 if(index < N * N)
		                			 blocks[i][j] = random.get(index);
		                		 index++;
		                	 } 
		                 }
		                 Board initial = new Board(blocks);
		     	        // solve the puzzle
		     	        AStar solver = new AStar(initial);
		     	        
		     	        System.out.print("Total nodes produced: " + solver.nodeCounter + "\n");
		     	        
		     	        // print solution to standard output
		     	        if (!solver.isSolvable())
		     	            System.out.println("No solution possible");
		     	        else {
		     	            System.out.println("Minimum number of moves = " + solver.moves());
		     	            long endTime = System.currentTimeMillis();
		     	            long elapsedTime = endTime-startTime;
		     	            System.out.print("Total runtime is: " + elapsedTime + "ms" + "\n");
		     	        }
		        	}
	        }
	        if(n ==2) {
	        	int counter = 0;
	        	while(counter < 9) {
					System.out.println("Please enter an integer 0-9 (no repeating) for the puzzle: ");
					int num = puzzlePieces.nextInt();
					 counter++;
					 for (int i = 0; i < N; i++) { 
	                	 for (int j = 0; j < N; j++) { 
	                			 blocks[i][j] = num;
	                		 
	                	 } 
	                 }
	            
	        	}
	        	
		        Board initial = new Board(blocks);
		        System.out.print(blocks.length);
		        // solve the puzzle
		        AStar solver = new AStar(initial);
		        
		        System.out.print("Total nodes produced: " + solver.nodeCounter + "\n");
		        
		        // print solution to standard output
		        if (!solver.isSolvable())
		            System.out.println("No solution possible");
		        else {
		            System.out.println("Minimum number of moves = " + solver.moves());
		            //long endTime = System.currentTimeMillis();
		            //long elapsedTime = endTime-startTime;
		           // System.out.print("Total runtime is: " + elapsedTime + "ms" + "\n");
		            for (Board board : solver.solutionPath())
		            {
		            	
		            	//(Used only during the output to txt sample solutions)
		            	//bw.write(board.toString());
		            	//bw.newLine();
		            	
		            }
		            
		        }
	       // bw.close();
	        }
        
        in.close();
    }
}