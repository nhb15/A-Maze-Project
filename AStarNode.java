package AStarMazeProject;

import javax.swing.*;

public class AStarNode {

    private int costFromInitial; //synonymous to g in literature
    private int estimatedCostToDest; //synonymous to h, the heuristic, in literature
    private int totalCost; //synonymous to f, the sum of two variables above
    //Sorted stack - research heuristic ideas and bring

    private JButton button; //This button will allow the user to click on it to include an obstacle
                            //The color correlates to whether it is unused (white/gray) , part of the path (blue), or is an obstacle (black)

    private boolean isObstacle; //The tracker for whether a node is an obstacle or a potential path member

    private int[] idxLocation;

    public void setCostFromInitial(AStarNode node){
        //FIXME:
        //node.costFromInitial = some sort of cost analysis from starting node. Should be exact
    }

    public void setEstimatedCostToDest(AStarNode node){
        //FIXME:
        //node.estimatedCostToDest = heuristic to final node - estimated since we don't know if there are obstacles in the way
    }



    public boolean isObstacle() {
        return isObstacle;
    }

    public void setObstacle(boolean obstacle) {
        isObstacle = obstacle;
        //FIXME: Change color of block
    }
}
