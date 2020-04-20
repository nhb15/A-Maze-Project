package AStarMazeProject;

import javax.swing.*;

public class AStarNode {

    private double costFromInitial; //synonymous to g in literature
    private double estimatedCostToDest; //synonymous to h, the heuristic, in literature
    private double totalCost; //synonymous to f, the sum of two variables above
    //Sorted stack - research heuristic ideas and bring
    private AStarNode previous; //used to assist in route building by linking nodes in the path found to one another

    private JButton button; //This button will allow the user to click on it to include an obstacle
                            //The color correlates to whether it is unused (white/gray) , part of the path (blue), or is an obstacle (black)

    private boolean isObstacle; //The tracker for whether a node is an obstacle or a potential path member

    private int[] idxLocation;

    /**
     * Constructor to setup a new node, includes index location in gui
     * @param idxLocation in [x,y] format
     */
    public AStarNode(int[] idxLocation){
        this.idxLocation = idxLocation;

        this.costFromInitial = Double.POSITIVE_INFINITY;
        this.estimatedCostToDest = Double.POSITIVE_INFINITY;
        this.totalCost = Double.POSITIVE_INFINITY;

    }

    /**
     * This method sets a node's cost from our starting location. It grabs the number of nodes visited already and adds 1, since if this node is picked it will be that value.
     * Technically, this +1 doesn't actually matter as long as we're consistent, but it makes sense.
     */
    public void setCostFromInitial(int cost){

        this.costFromInitial = cost;
    }

    public double findCostFromInitial(){
        return AStarDriver.getNumNodesVisited() + 1;
    }

    /**
     * findEstimatedCostToDest gives the h value, (aka heuristic) by calculating efficiences from moving from the current
     * node to the destination node
     * @return heuristic cost value
     */
    public double findEstimatedCostToDest(){

        double xChange = Math.abs(idxLocation[0] - AStarDriver.getDestNode().idxLocation[0]);
        double yChange = Math.abs(idxLocation[1] - AStarDriver.getDestNode().idxLocation[1]);
        double costToMoveSquares = 1;
        double diagonalSavings = Math.sqrt(2);

        //This formula is from Stanford's theory crafting: http://theory.stanford.edu/~amitp/GameProgramming/Heuristics.html#S7
        return costToMoveSquares * (xChange + yChange) + (diagonalSavings - 2 * costToMoveSquares) * Math.min(xChange, yChange);
        /**
         * The idea is "D"(from the source) is the cost for moving to an adjacent square. In our case, there's no cost associated with it so we use 1, but we put it in a variable for clarity.
         * We multiple the cost to move by the actual change in graph indices, but SUBTRACT the saved costs from using any diagonal paths. This means it's
         * MORE EFFICIENT to use diagonal paths and the algorithm promotes that.
         */




        //node.estimatedCostToDest = heuristic to final node - estimated since we don't know if there are obstacles in the way
        /**
         * POSSIBLE HEURISTIC IDEAS:
         *
         * 1. We do a combination of hypotenuse of the array values AND a search ahead of each node (a few nodes ahead to see if there are obstacles?)
         *
         * 2. We follow what's on the stanford link: http://theory.stanford.edu/~amitp/GameProgramming/Heuristics.html#S7 - would depend on whether we want diagonal control
         *
         * 3. In a probe ahead of the node, we could check not ONLY for obstacles but also the edge of the map ( or do we just make the edge of the map an immovable obstacle?)
         */

        /**
         * function heuristic(node) =
         *     dx = abs(node.x - goal.x)
         *     dy = abs(node.y - goal.y)
         *     return D * (dx + dy) + (D2 - 2 * D) * min(dx, dy)
         */



    }

    public void setEstimatedCostToDest(int cost){

        this.estimatedCostToDest = cost;
    }



    public boolean isObstacle() {
        return isObstacle;
    }

    public void setObstacle(boolean obstacle) {
        isObstacle = obstacle;

    }

    public boolean getIsObstacle(){
        return this.isObstacle;
    }

    public JButton getButton(){
        return this.button;
    }

    public void setButton(JButton button) {
        this.button = button;
    }
    
    public AStarNode getPrevious(){
        return this.previous;
    }
    
    public void setPrevious(AStarNode previous){
        this.previous = previous;
    }

    public int[] getIdxLocation() {
        return idxLocation;
    }

    public void setIdxLocation(int[] idxLocation) {
        this.idxLocation = idxLocation;
    }


}
