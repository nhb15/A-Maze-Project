package AStarMazeProject;

import java.awt.*;
import java.util.*;

/**
 * A PROJECT MADE BY NATE AND JAKE
 *
 * CITATIONS:
 *http://theory.stanford.edu/~amitp/GameProgramming/Heuristics.html#S7
 * https://www.educative.io/edpresso/what-is-the-a-star-algorithm
 *
 *
 */

public class AStarDriver{

	/**
	 * WIDTHNODES and HEIGHTNODES hold the size of the GUI and node array for path finding
	 */
    public static final int WIDTHNODES = 20;
    public static final int HEIGHTNODES = 20;

	/**
	 * numNodesVisited tracks how many nodes are in our path, used in analysis of the cost
	 */
	private static int numNodesVisited = 0;

	/**
	 * nodeArr is a standard 2D array that holds all our AStarNodes
	 */
    private static AStarNode[][] nodeArr = new AStarNode[WIDTHNODES][HEIGHTNODES];

	/**
	 * startNode and destNode allow us to change where our path starts and ends. They initialize to [0,0] and [5,5], but could be anything.
	 */
    private static AStarNode startNode;
    private static AStarNode destNode;

	/**
	 * temp and current are AStarNodes used in the AStar method AND the routebuilder to keep track of which node we are
	 * CURRENTLY processing and which node we need to save to pass to AStarNode.PREVIOUS (temp)
	 */
	private static AStarNode temp;
	private static AStarNode current;

	/**
	 * route is an AStarNode Stack holding the nodes in our path, already found
	 */
	private static Stack<AStarNode> route = new Stack<AStarNode>(); //linked list to hold route

	/**
	 * open holds POTENTIAL successor nodes(for example, neighbor nodes that qualify) for pathbuilding in AStar
	 * The PriorityQueue sorts based on AStarNode.totalCost where the first node has the lowest cost
	 * open is cleared out on each subsequenet node
	 */
	private static PriorityQueue<AStarNode> open = new PriorityQueue<AStarNode>();

	/**
	 * closed holds already considered nodes (for example, a neighbor node that did NOT have the lowest cost in
	 * PriorityQueue open. These nodes are not considered again in the same AStar calculation.
	 */
	private static HashMap<String, AStarNode> closed = new HashMap<String, AStarNode>();


    public static void main(String[] args) {

		/**
		 * This nested for loop initializes the node array with new nodes. This is used and matches the GUI.
		 */
		for (int i = 0; i < WIDTHNODES; i++){
				for (int j = 0; j < HEIGHTNODES; j++){
                	int[] temp = {i,j};
                	AStarNode node = new AStarNode(temp);
                	nodeArr[i][j] = node;
            }
        }

		/**
		 * Here, we initialize startNode, startNodes cost(to be TECHNICALLY correct), and destNode to our desired values.
		 */
        startNode = nodeArr[0][0];
        startNode.setTotalCost(0);

        destNode = nodeArr[12][12];


        AStarGUI gui = new AStarGUI(WIDTHNODES, HEIGHTNODES);

		/**
		 * Below are example obstacles that the GUI/program can start with. We can change these at will or maybe randomize them
		 * for show and tell.
		 */
		nodeArr[1][1].setObstacle(true);
		nodeArr[3][3].setObstacle(true);
		nodeArr[3][4].setObstacle(true);
		nodeArr[4][5].setObstacle(true);
		nodeArr[4][6].setObstacle(true);

		nodeArr[1][1].getButton().setBackground(Color.BLACK);
		nodeArr[3][3].getButton().setBackground(Color.BLACK);
		nodeArr[3][4].getButton().setBackground(Color.BLACK);
		nodeArr[4][5].getButton().setBackground(Color.BLACK);
		nodeArr[4][6].getButton().setBackground(Color.BLACK);



		/**
		 * Calling AStar is obviously our main call in the driver here - this will start calculating the route
		 */
		AStar(startNode, destNode);


		/**
		 * Once AStar finishes, we can update our GUI. This will update the considered nodes (closed), the path itself(route), and start/end nodes if necessary.
		 */
		gui.updateGUI(route, closed);

    }

/**
     * function to build path from AStar calculated and stored node PREVIOUS values
     * uses ROUTE stack to store nodes in path, using due to First In Last Out print ability
     * @param AStarNode start node
     * @param AStarNode end node
     */
  public static void routeBuilder(AStarNode start, AStarNode end) {

	  /**
	   * set AStarNode current to begin at the END of the path, and work our way backwards using the previous property.
	   */
	  AStarNode current = end; //current portion of path being added to linkedlist
    	
	  while(current!=start) { //loops until start is hit
	  		route.add(current); //add current node to list
    		current = current.getPrevious(); //update current to previous
	  }
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

    //FIXME: CHECK this for indexOutOfBounds
    private static Set neighborNodes(AStarNode current) {

		/**
		 * neighbors can be a Set as each one needs to be unique - and a hashset allows for constant time retrieval.
		 */
		Set<AStarNode> neighbors = new HashSet<AStarNode>();

		/**
		 * Let's grab the index information from the node to determine its neighbors
		 */
    	int row = current.getIdxLocation()[0];//I think this should get the row value from the node
    	int col = current.getIdxLocation()[1];//should get column value from node

		/**
		 * This nested for loop rotates through all 8 potential array neighbors and checks if they are in bounds of the array AND if they are an obstacle
		 */
		for(int i = row-1; i<=row+1; ++i) {//scans through all nearby row positions
    		for(int j = col-1; j<=col+1; ++j) {//scans through all nearby row positions
    			if((i!=row) || (j!=col)) {//checks if considered node is equal to current node, if not continues to next line
    				if(inBounds(i, j) && !nodeArr[i][j].getIsObstacle()) { //checks if considered node is within bounds of graph AND if the node is an obstacle
						//If the node passes all these checks, we can add it to our set.
    					neighbors.add(nodeArr[i][j]);
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
    public static boolean inBounds (int row, int col) {
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
	public static void AStar(AStarNode start, AStarNode goal){
		
		//add start node to open list
		open.add(start);

		int tracker = 575;//initialize tracker variable used for hash key value

		//while open is not empty, poll value from pqueue and assign to current
		while(!open.isEmpty()) {


			current = open.poll();

			/**
			 * Since we already assume startNode as a numNodeVisited, increment the counter if current is anything but startNode.
			 */
			if (current != startNode) {
				numNodesVisited++;
			}

			/**
			 * This if/else statement sets the previous values of the nodes to the appropriate value
			 * If current is startNode, that means we're on the first run of our algorithm, and there's nothing to set for previous
			 */
			if (current.equals(startNode)){
				temp = current;
			}
			else {
				//Temp holds the previous node, so we can set it to previous
				current.setPrevious(temp);
				temp = current;
			}

			/**
			 * Let listOfNeighbors be the local copy of neighbors
			 */
			Set<AStarNode> listOfNeighbors = neighborNodes(current);
			int numNeighbors = listOfNeighbors.size();

			/**
			 * We need to clear out potential nodes in open each node iteration
			 * example: on our first run, if startNode is 0,0 - a neighbor is 1,0. But it might still be in
			 * open list by node 5,5 if we don't clear it.
			 */
			open.clear();

			/**
			 * For each node in list of neighbors, let's:
			 * 1. check if this neighbor IS the destination. If so, build the route.
			 * 2. IF this node was not already considered(since a node could be neighbor to more than one object in the path),
			 * find the cost associated with the node and add it to the considered list and potential open list.
			 */
			for (@SuppressWarnings("unused") AStarNode node: listOfNeighbors){

				//base case, if node is destination, call route builder and quit for loop
				if(node.equals(destNode)) {
					destNode.setPrevious(temp);
					routeBuilder(startNode, node);
					return;
				}
				//checks if current value is in closed list, if not, calculates cost and adds to list
				if(!closed.containsValue(node)) {

					node.setTotalCost(node.findTotalCost());//update current total cost with computed cost to destination and cost from initial

					/**FOR DEBUGGING PURPOSES OR FUTURE GUI DEVELOPMENT:
					 * INITIAL COST
					 * COST FROM DEST
					 */
					node.setCostFromInitial(node.findCostFromInitial());
					node.setEstimatedCostToDest(node.findEstimatedCostToDest());

					closed.put("" + tracker, node);//adds current value to closed list using tracker as key for hash

					open.add(node);//add current value to open priority queue

					++tracker; //for hash
				}

			}

		}

		System.out.println( "Whoops! Something went wrong - Looks like our path is unreachable or the path must consider a node on the closed path. " +
				"Please open up the path to allow this implementation of A* to run again. ");
    	        
    }

	/**
	 *Below are ALL the getters and setters necessary for the algorithm.
	 * The getters retrieve that variable or data structure, and the setters alter the value.
	 */
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

	public static Stack<AStarNode> getRoute() {
		return route;
	}

	public static HashMap<String, AStarNode> getClosed() {
		return closed;
	}

	public static PriorityQueue<AStarNode> getOpen() {
		return open;
	}

	public static void setOpen(PriorityQueue<AStarNode> open) {
		AStarDriver.open = open;
	}

	/**
	 * This method re-colors the start and end node in the GUI. This COULD live in the GUI, but thought the driver
	 * would be OK since the nodes live here.
	 */
	public static void setStartEndPurple(){
		destNode.getButton().setBackground(Color.MAGENTA);
		startNode.getButton().setBackground(Color.MAGENTA);
	}

	/**
	 * function clears any necessary data structures in AStar in order to restart the GUI path.
	 */
	public static void clearAll(){

		route.clear();
		open.clear();
		closed.clear();
		/**
		for (int i = 0; i < WIDTHNODES; i++) {
			for (int j = 0; j < HEIGHTNODES; j++) {
				nodeArr[i][j].setPrevious(null);
			}
		}
		 */
	}

}
