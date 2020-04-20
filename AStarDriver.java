package AStarMazeProject;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

/**
 * A PROJECT MADE BY NATE AND JAKE
 *
 * CITATIONS:
 *http://theory.stanford.edu/~amitp/GameProgramming/Heuristics.html#S7
 * https://www.educative.io/edpresso/what-is-the-a-star-algorithm
 *
 *
 */

public class AStarDriver<E  extends Comparable<E>> {

    private static final int WIDTHNODES = 20;
    private static final int HEIGHTNODES = 20;

    private static int numNodesVisited = 0;

    private static AStarNode[][] nodeArr = new AStarNode[WIDTHNODES][HEIGHTNODES];


    private static AStarNode startNode;
    private static AStarNode destNode;

    /**
     * Regardless of which data structure we use to track visitedNodes, let's track the actual size in numNodesVisited above
     */

    private static AStarNode[] visitedNodes = new AStarNode[WIDTHNODES * HEIGHTNODES];

    /**
     * FOR NOW, we COULD use the standard priority queue so that we can get the project rolling. But, I'm not sure it's what we need
     * //private static PriorityQueue<AStarNode> pqAdjacentNodes;
     */
    
    //create priority queue for open set
	PriorityQueue<AStarNode> open = new PriorityQueue<AStarNode>();
	//create hashmap for closed set
	HashMap<String, AStarNode> closed = new HashMap<String, AStarNode>();
    

    public static void main(String[] args) {

        for (int i = 0; i < WIDTHNODES; i++){
            for (int j = 0; j < HEIGHTNODES; j++){
                int[] temp = {i,j};
                AStarNode node = new AStarNode(temp);
                nodeArr[i][j] = node;
            }
        }

        startNode = nodeArr[0][0];
        destNode = nodeArr[WIDTHNODES - 1][HEIGHTNODES - 1];

        AStarGUI gui = new AStarGUI(WIDTHNODES, HEIGHTNODES);

        //pqAdjacentNodes.add(nodeArr[0][0]);

        /*We'll want to make a priority queue that takes Nodes with f as their priority value, as well as a seperate data structure to hold
            nodes we have already visited
        
         1) create priority queue that takes Nodes and uses their f value as the comparator (TOCHECK LIST)
                  below is from: https://docs.oracle.com/javase/7/docs/api/java/util/PriorityQueue.html
                  
           public PriorityQueue(int initialCapacity,
             Comparator<? super E> comparator)
                  Creates a PriorityQueue with the specified initial capacity that orders its elements according to the specified comparator.
         2) Create a data structure that is easily searched (VISITED LIST), which will hold the nodes we have already considered 
            This will be a good place to focus in on the report, I think, as it's a good demonstration of when we need a data structure
            that we can quickly search, but not necessarily remove from or alter
            
            From there, we'll want to initialize all the distances in each node to infinity, as well as initializing each node's
            previous pointer to null
            
            We first enter our start node to the Priority Queue and the visited hashmap (do we add the start node here or in the while loop?) 
            then enter a while loop until priority queue is empty that:
                1)Pops top prioritized value off of queue and assigns to current node
                2)Adds current to visited database, need to figure out hash method, right now have it written to use strings as a key
                3)BASE CASE: if current node is goal, return route by calling route building method (adding to list of tasks)
                4)Else, for each neighbor of current, check if neighbor is already in the visited hashmap. 
                        if yes ---> skip node
                        if no  ---> 1) evaluate and update costFromInitial, estimatedCostToDest, and totalCost for each neighbor
                                    2) add to priority queue
                                    
                                    

        */

        /**
         * TASKS TO FOCUS ON:
         *
         * 1. (NATE) Make list of nodes IN driver class, passable to GUI
         * 2. Make
         * 3. (NATE) Priority Queue that accepts F value as comparator (in driver)
         * 4. (JAKE) Decide on data structure for closed set (and have reasons for report) see lines 106-108
         * 5. (BOTH) Pass or update information on GUI once node is added to closed set (AKA the path - turn blue) https://www.redblobgames.com/pathfinding/a-star/introduction.html
         *      -unexplored, explored, actual path (if we do this, we also need to pass explored nodes - very similar to actual path information pass)
         *      -Consider best way to pass information from GUI back to driver for updated obstacles as well.
         * 6. (NATE) Create method to generate costFromInitial - (POSSIBLY: counting nodes in VISITED list + 1 - most likely)
         * 7. (NATE) Create method to generate heuristic (estimatedCostToDest) - Diagonal Distance - http://theory.stanford.edu/~amitp/GameProgramming/Heuristics.html#S7
         * 8. (JAKE) Create method to inserts STARTNODE (AND COMPLETES JAKE'S NOTES FROM ABOVE)
         * 9. (NATE) Allow obstacles to be reversed
         * 10. (NATE) lookup github making someone else an admin
         * 11. (JAKE) Build route building method that can be recursively called to build the route and print it out
         *
         * ONCE ABOVE IS DONE:
         * 10. Write a README
         * 11. Create Presentation (8-15)
         * 11. .If TIME ALLOWS, change heuristic to show different speeds/efficiency
         
         
         * 4. The closed or visited list needs to have both fast insertion and fast search, and a hashmap has both O(1) average for 
         *    both those functions (See big O cheat sheet). Only downside is hashmaps do not support access functions(unclear what that is tbh).
         * 
         */
    }
/**
     * function to build path from AStar stored values
     * uses stack to store nodes in path, using due to First In Last Out print ability
     * @param AStarNode start node
     * @param AStarNode end node
     * @return FIXME either the full Stack, or a recursive call to pop? Need to pass to GUI as well
     */
  public Stack routeBuilder(AStarNode start, AStarNode end) {
    	Stack<AStarNode> route = new Stack<AStarNode>(); //linked list to hold route
    	AStarNode current = end; //current portion of path being added to linkedlist
    	
    	while(current!=start) { //loops until start is hit
    		route.add(current); //add current node to list
    		current = current.getPrevious(); //update current to previous
    	}
    	
    	return route;//FIXME edit to properly print stack first in last out
    }
	
    /**
     * function to find neighbors of current node based on row and column position. 
     * Uses same technique found in array map method, assigning a temp int array to hold idxLocation, then 
     * subsequently updating row and col to the first and second values in that array. Stores values in set, which can then be easily
     * cycled through when being considered in the AStar algorithm itself. 
     * @param AStarNode
     * @return set composed of all neighbors of input node
     * @see https://stackoverflow.com/questions/43816484/finding-the-neighbors-of-2d-array
     */
    public Set neighborNodes(AStarNode current) {
    	Set<AStarNode> neighbors = null;
    	int row     = current.getIdxLocation()[0];//I think this should get the row value from the node
    	int col     = current.getIdxLocation()[1];//should get column value from node

    	for(int i = row-1; i<=row+1; ++i) {//scans through all nearby row positions
    		for(int j = col-1; j<=col+1; ++j) {//scans through all nearby row positions
    			if((i!=row) && (j!=col)) {//checks if considered node is equal to current node, if not continues to next line
    				if(inBounds(i, j)) { //checks if considered node is within bounds of graph
    					int [] temp = {i, j};

    					//FIXME: Do we really want to be creating a new node here? We already have nodes craeted, I think we just need to grab it
    					AStarNode node = new AStarNode(temp);
    					neighbors.add(node);
    				}
    			}
    		}
    	}
    	return neighbors;
    }
	

    /**
     * method to check if considered location is within given map
     * @param row, current row position, int
     * @param col, current col position, int
     * @return boolean; true if within map, false otherwise
     * @see https://stackoverflow.com/questions/43816484/finding-the-neighbors-of-2d-array
     */
    public boolean inBounds (int row, int col) {
    	if (row < 0 || col < 0) {
    		return false;
    	}
    	if (row > WIDTHNODES || col > HEIGHTNODES) {
    		return false;
    	}
    	return true;
    }
	/**
	 * AStar algorithm that finds optimal path by putting all neighbor nodes not already considered
	 * into a priority queue with the priority value being the total cost, found from a sum of the heuristic
	 * and the cost from initial value. Once goal is reached, routebuilder is called. 
	 * @param start: starting node in map
	 * @param goal: target node in map
	 */
	public void AStar(AStarNode start, AStarNode goal){
		
		//add start node to open list
		open.add(start);
		int tracker = 575;//initialize tracker variable used for hash key value
		//while open is not empty, poll value from pqueue and assign to current
		while(!open.isEmpty()) {
			current = open.poll();
			listofNeighbors = current.neighborNodes(current);
			for (@SuppressWarnings("unused") AStarNode node: listofNeighbors){
				//base case, if current is node, call route builder and quit for loop
				if(current==destNode) {
					routeBuilder(startNode, current);
					break;
				}
				//checks if current value is in closed list and or an obstacle, if not adds to list
				if(!closed.containsValue(current) && !current.isObstacle()) {

					//FIXME: Are we using each cost individually? Or just the total cost? We may be able to consolidate some of this code
					current.setCostFromInitial(current.findCostFromInitial());//updates current node's cost from initial value
					current.setEstimatedCostToDest(current.findEstimatedCostToDest());//updates current node's estimated cost to destination
					closed.put(tracker, current);//adds current value to closed list using tracker as key for hash
					open.add(current);//add current value to open priority queue
					current.setTotalCost(current.findEstimatedCostToDest(), current.findCostFromInitial());//update current total cost with previously computed cost to destination and cost from initial
					++tracker;
				}
			}
		}
    	        
    }

    public static AStarNode[][] getNodeArr() {
        return nodeArr;
    }

    public static void setNodeArr(AStarNode[][] nodeArr) {
        AStarDriver.nodeArr = nodeArr;
    }

    public static int getNumNodesVisited() {
        return numNodesVisited;
    }

    public static void setNumNodesVisited(int numNodesVisited) {
        AStarDriver.numNodesVisited = numNodesVisited;
    }

    public static AStarNode getStartNode() {
        return startNode;
    }

    public static void setStartNode(AStarNode startNode) {
        AStarDriver.startNode = startNode;
    }

    public static AStarNode getDestNode() {
        return destNode;
    }

    public static void setDestNode(AStarNode destNode) {
        AStarDriver.destNode = destNode;
    }

}
