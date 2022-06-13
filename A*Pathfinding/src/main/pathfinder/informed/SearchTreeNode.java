package main.pathfinder.informed;
import java.lang.Math;
import java.util.Set;

/**
 * SearchTreeNode that is used in the Search algorithm to construct the Search
 * tree.
 */
class SearchTreeNode implements Comparable<SearchTreeNode>{
    // [!] TODO: You're free to modify this class to your heart's content
    
    MazeState state;
    String action;
    SearchTreeNode parent;
    int historicalCost;
    int futureCost;
    int evalValue;
    
    
    /**
     * Constructs a new SearchTreeNode to be used in the Search Tree.
     * 
     * @param state The MazeState (row, col) that this node represents.
     * @param action The action that *led to* this state / node.
     * @param parent Reference to parent SearchTreeNode in the Search Tree.
     */
    
    
    //constructor
    public SearchTreeNode (MazeState state, String action, SearchTreeNode parent, int historicalCost, int futureCost, int evalValue) {
        this.state = state;
        this.action = action;
        this.parent = parent;
        this.historicalCost = historicalCost;
        this.futureCost = futureCost;
        this.evalValue = evalValue; 
    }
     
    public int getEvalValue () {
    	return this.evalValue;
    }
   
    
    public int getHistoricalCost() {
    	return this.historicalCost;
    }
  
    
    //Method#1: to calculate the Manhattan distance from initial state to a given goal state 
    public static int computeFutureCostToGoal(MazeState current, MazeProblem problem) {
    	Set<MazeState> goalStates = problem.getGoalStates();
    	//move this to future cost 
    	int smallestHeuristic = Integer.MAX_VALUE;
    	//find the goal state that results in the smallest heuristic 
    	for (MazeState goalState: goalStates ) {
    		int ManhattanDistance = Math.abs(goalState.col - current.col)  + Math.abs(goalState.row - current.row);
    		if (smallestHeuristic > ManhattanDistance) {
    			smallestHeuristic = ManhattanDistance;
    		}
    	}
    	return smallestHeuristic;
    }
    
    
    //Method #2: compute futureCost to Key State (heuristic value)
    public static int computeFutureCostToKey(MazeState current, MazeState key) {
    	return ( Math.abs(key.col - current.col) + Math.abs(key.row - current.row) );
    }
    
    
	@Override 
	public int compareTo(SearchTreeNode other) {
		return this.getEvalValue() - other.getEvalValue(); //will be a negative number
	}  
}