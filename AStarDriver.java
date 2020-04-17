package AStarMazeProject;

/**
 * A PROJECT MADE BY NATE AND JAKE
 *
 * CITATIONS:
 *http://theory.stanford.edu/~amitp/GameProgramming/Heuristics.html#S7
 * https://www.educative.io/edpresso/what-is-the-a-star-algorithm
 *
 *
 */

public class AStarDriver {

    public static void main(String[] args) {

        AStarGUI gui = new AStarGUI();
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
            
            We first enter our start node to the Priority Queue and the visited database (do we add the start node here or in the while loop?) 
            then enter a while loop until priority queue is empty that:
                1)Pops top prioritized value off of queue and assigns to current node
                2)Adds all nodes that are non obstacles and adjacent to current node to both the prioritized queueu as well as 
                  the visited database
                3)

        */

        /**
         * TASKS TO FOCUS ON:
         *
         * 1. (NATE) Make list of nodes IN driver class, passable to GUI
         * 2. Make
         * 3. (NATE) Priority Queue that accepts F value as comparator (in driver)
         * 4. (JAKE) Decide on data structure for closed set (and have reasons for report)
         * 5. (BOTH) Pass or update information on GUI once node is added to closed set (AKA the path - turn blue) https://www.redblobgames.com/pathfinding/a-star/introduction.html
         *      -unexplored, explored, actual path (if we do this, we also need to pass explored nodes - very similar to actual path information pass)
         *      -Consider best way to pass information from GUI back to driver for updated obstacles as well.
         * 6. (NATE) Create method to generate costFromInitial - (POSSIBLY: counting nodes in VISITED list + 1 - most likely)
         * 7. (NATE) Create method to generate heuristic (estimatedCostToDest) - Diagonal Distance - http://theory.stanford.edu/~amitp/GameProgramming/Heuristics.html#S7
         * 8. (JAKE) Create method to inserts STARTNODE (AND COMPLETES JAKE'S NOTES FROM ABOVE)
         * 9. (NATE) Allow obstacles to be reversed
         * 10. (NATE) lookup github making someone else an admin
         *
         * ONCE ABOVE IS DONE:
         * 10. Write a README
         * 11. Create Presentation (8-15)
         * 11. .If TIME ALLOWS, change heuristic to show different speeds/efficiency
         */
    }


}
