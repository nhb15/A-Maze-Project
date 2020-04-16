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
    }


}
