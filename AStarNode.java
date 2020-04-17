package AStarMazeProject;

import javax.swing.*;

public class AStarNode {

    private double costFromInitial; //synonymous to g in literature
    private double estimatedCostToDest; //synonymous to h, the heuristic, in literature
    private double totalCost; //synonymous to f, the sum of two variables above
    //Sorted stack - research heuristic ideas and bring

    private JButton button; //This button will allow the user to click on it to include an obstacle
                            //The color correlates to whether it is unused (white/gray) , part of the path (blue), or is an obstacle (black)

    private boolean isObstacle; //The tracker for whether a node is an obstacle or a potential path member

    private int[] idxLocation;

    /**
     * Constructor to setup a new node, includes index location in gui
     * @param idxLocation in [x,y] format
     */
    public AStarNode(int[] idxLocation, JButton button){
        this.idxLocation = idxLocation;
        this.button = button;

        this.costFromInitial = Double.POSITIVE_INFINITY;
        this.estimatedCostToDest = Double.POSITIVE_INFINITY;
        this.totalCost = Double.POSITIVE_INFINITY;

    }

    public void setCostFromInitial(AStarNode node){
        //FIXME:
        //node.costFromInitial = some sort of cost analysis from starting node. Should be exact
    }

    public void setEstimatedCostToDest(AStarNode node){
        //FIXME:
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

    }



    public boolean isObstacle() {
        return isObstacle;
    }

    public void setObstacle(boolean obstacle) {
        isObstacle = obstacle;
        //FIXME: Change color of block
    }

    public JButton getButton(){
        return this.button;
    }

}
