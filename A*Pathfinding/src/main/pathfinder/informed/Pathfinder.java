package main.pathfinder.informed;

import java.util.*;
// >> [AF] Lots of extraneous spaces here -- be wary of these, you have them in some of your methods as
// well -- in general, you should never have more than 2 spaces anywhere, and never more than 1 space 
// within a method (-0.5)










public class Pathfinder {
	
    /**
     * Given a MazeProblem, which specifies the actions and transitions available in the
     * search, returns a solution to the problem as a sequence of actions that leads from
     * the initial to a goal state.
     * 
     * @param problem A MazeProblem that specifies the maze, actions, transitions.
     * @return An ArrayList of Strings representing actions that lead from the initial to
     * the goal state, of the format: ["R", "R", "L", ...]
     */
	
    public static ArrayList<String> solve (MazeProblem problem) {
        PriorityQueue <SearchTreeNode> pQueue = new PriorityQueue<>(); //initializing frontier 
        Set<MazeState> set = new HashSet<>(); //initializing grave yard  (closed set)
        ArrayList<String> solved = new ArrayList<String>(); //initializing problem result array 
        
        //At this point, our goal is to find the keyState. We start off with the initialState as the root node of our search tree
        int future_cost = SearchTreeNode.computeFutureCostToKey(problem.getInitialState(), problem.getKeyState());
        SearchTreeNode root = new SearchTreeNode (problem.getInitialState(), null, null, 0, future_cost ,  future_cost);
        pQueue.add(root);
        set.add(problem.getInitialState());
        
        while (!pQueue.isEmpty()) {
            
        	SearchTreeNode currentNode = pQueue.remove();
        	set.add(currentNode.state);
        
        	//Base Case #1: we have found the key
        	if (currentNode.state.equals(problem.getKeyState())) {
        		pQueue.clear(); 
        		set.clear();
        		root = currentNode; //set root as the keyState     		
        		pQueue.add(root);
        		set.add(root.state);
        		
        		while (!pQueue.isEmpty()) {
    			currentNode = pQueue.remove();
        		Set<MazeState> goalStates = problem.getGoalStates(); // retrieve goalStates given in the maze 
        		
        		//Base case #2: we have found the key, and we have landed on one of the given goalStates 
				if (goalStates.contains(currentNode.state) ) {
					solved.addAll(retraceSteps(currentNode));
					return solved;
        			}
  
             			//node generation until goal state is found 
                	Map<String, MazeState> transitions = problem.getTransitions(currentNode.state);
                    for (Map.Entry<String, MazeState> entry: transitions.entrySet()) {
                    		//node generation: continues until key state is found 
                    if (!set.contains(entry.getValue())) {
                    	int h_cost = currentNode.getHistoricalCost() + problem.getCost(entry.getValue());
                    	int f_cost = SearchTreeNode.computeFutureCostToGoal(entry.getValue(), problem);
                    	int evalValue = h_cost + f_cost;
                    	SearchTreeNode child = new SearchTreeNode (entry.getValue(), entry.getKey(), currentNode, h_cost, f_cost, evalValue );
                    	pQueue.add(child);
                    		}
                    	}
        			}
        		}

        	
        	//node generation until key state is found
        	Map<String, MazeState> transitions = problem.getTransitions(currentNode.state);
        	for (Map.Entry<String, MazeState> entry: transitions.entrySet()) {
        		if (!set.contains(entry.getValue())) {
        			int h_cost = currentNode.getHistoricalCost() + problem.getCost(entry.getValue());
        			int f_cost = SearchTreeNode.computeFutureCostToKey(entry.getValue(), problem.getKeyState());
        			int evalValue = h_cost + f_cost;
        			SearchTreeNode child = new SearchTreeNode (entry.getValue(), entry.getKey(), currentNode, h_cost, f_cost, evalValue );
        			pQueue.add(child);
        			}
        		}    	
        	}
        return null; //no solution exists
} 
   
    /*
     * Helper method to retrace steps after goal has been found 
     * 
     * @param currentNode starting node to retrace steps from 
     * @return array of steps from the retracing process
     */
    private static ArrayList<String> retraceSteps (SearchTreeNode currentNode) {
       ArrayList <String> prev = new ArrayList<String>();
       while (currentNode.action != null) {
    	   prev.add(0, currentNode.action);
    	   currentNode = currentNode.parent;
       }
       return prev; 
    }    
}


