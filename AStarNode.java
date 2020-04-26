package AStarMazeProject;

import javax.swing.*;
import java.util.Set;

public class AStarNode implements Comparable<AStarNode>{

    private double costFromInitial; //synonymous to g in literature, the cost from startNode
    private double estimatedCostToDest; //synonymous to h, the heuristic, in literature, the calculated cost to destNode

    private double totalCost; //synonymous to f, the sum of two variables above

    private AStarNode previous; //used to assist in route building by linking nodes in the path found to one another

    private JButton button; //This button will allow the user to click on it to include an obstacle
                            //The color correlates to whether it is unused (white) , part of the path (blue), was considered(gray), or is an obstacle (black)

    private boolean isObstacle; //The tracker for whether a node is an obstacle or a potential path member

    private int[] idxLocation; //The index for a node in the map

    private static Set<AStarNode> listofNeighbors; //The potential 8 nodes in the map neighboring this node

    /**
     * Constructor to setup a new node, includes index location in gui
     * @param idxLocation in [x,y] format
     */
    public AStarNode(int[] idxLocation){
        this.idxLocation = idxLocation;

        /**
         * Setting these costs to infinity since they will be updated before ever conisdered
         */
        this.costFromInitial = Double.POSITIVE_INFINITY;
        this.estimatedCostToDest = Double.POSITIVE_INFINITY;
        this.totalCost = Double.POSITIVE_INFINITY;

    }


    public void setCostFromInitial(double cost){

        this.costFromInitial = cost;
    }

    /**
     * This method sets a node's cost from our starting location. It grabs the number of nodes visited already and adds 1, since if this node is picked it will be that value.
     * Technically, this +1 doesn't actually matter as long as we're consistent, but it makes sense.
     * @return
     */
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

    }

    public void setEstimatedCostToDest(double cost){

        this.estimatedCostToDest = cost;
    }


    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    /**
     * The total cost is the sum of the costFromInitial and the estimatedCostToDestination
     * @return totalCost
     */
    public double findTotalCost(){
        return (findCostFromInitial() + findEstimatedCostToDest());
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


    /**
     * This equals method overrides any other methods to return true if the nodes compared have identical map indices.
     * @param obj of another node
     * @return boolean on node equality
     */
    @Override
    public boolean equals(Object obj){
        AStarNode node = (AStarNode)obj;

        if (this.getIdxLocation().equals(node.getIdxLocation())){
            return true;
        }

        return false;

    }

    /**
     * compareTo overrides the comparable interface as necessary to allow for object comparison
     * This allows the smallest cost node to hit the top of the priority queue
     * @param node
     * @return the comparison value based on each node (see: Double.compare for more info)
     */
    @Override
    public int compareTo(AStarNode node){
        return Double.compare(this.totalCost, node.getTotalCost());
    }



}
