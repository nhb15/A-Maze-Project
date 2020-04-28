package AStarMazeProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Stack;

public class AStarGUI extends JFrame implements ActionListener {


    //Constructor should initialize all the inital values
    public AStarGUI(int WIDTHNODES, int HEIGHTNODES) {

        /**
         * Setup basic GUI structures:
         */
        setTitle("Nate and Jake's A* Algorithm GUI");
        setLayout(new GridBagLayout());
        GridBagConstraints layoutConst = null;
        layoutConst = new GridBagConstraints();

        /**
         * Grab the nodeArr by reference to add buttons in the nested for loop
         */
        AStarNode[][] nodeArr = AStarDriver.getNodeArr();

        for (int i = 0; i < WIDTHNODES; i++) {
            for (int j = 0; j < HEIGHTNODES; j++) {
                int[] temp = {i, j};
                JButton button = new JButton();
                button.setActionCommand("" + i + "," + j);
                button.setBackground(Color.white);
                button.addActionListener(this); //Allows the buttons to perform actions based on user input

                nodeArr[i][j].setButton(button);

                /**
                 * Place the buttons as you would expect on the map:
                 */
                layoutConst = new GridBagConstraints();
                layoutConst.gridx = i;
                layoutConst.gridy = j;

                layoutConst.ipady = 15;
                add(button, layoutConst);
            }
        }

        /**
         * More default GUI behavior additions:
         */
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {

        //This getActionCommand allows us to use constant time retrieval of which button is pushed
        String[] indices = event.getActionCommand().split(",");
        int xIdx = Integer.parseInt(indices[0]);
        int yIdx = Integer.parseInt(indices[1]);

        //Retrieve the nodeArray map again:
        AStarNode[][] nodeArr = AStarDriver.getNodeArr();

        /**
         * If our node is nothing, and the user clicks on it, let's turn it black and show it as an obstacle.
         *
         * If our node is on the ROUTE and the user clicks on it, we need to turn it black AND recalculate our route.
         *
         * IF our node is already an obstacle and the user clicks on it, let's turn it back to the color it was before and remove the obstacle.
         * For now, let's always recalculate the route.
         *
         */
        AStarNode node = nodeArr[xIdx][yIdx];
        boolean isObstacle = node.getIsObstacle();

        //If the user clicks on a non-obstacle, swap it to an obstacle. If it was on the route, we'll need to adjust our path.
        if (!isObstacle) {

            node.setObstacle(true);

            if (AStarDriver.getRoute().contains(node)) {

                //let's clear out the route, the considered nodes, and anything else critical before making a new one:
                clearAll();

                /**
                 * Create a new path by calling AStar again, which builds the route within its own call
                 */
                AStarDriver.AStar(AStarDriver.getStartNode(), AStarDriver.getDestNode());

                //With our new path, update the GUI to represent it.
                updateGUI(AStarDriver.getRoute(), AStarDriver.getClosed());
            }
            //Once we update path, make sure the node we actually clicked is black and shows itself correctly.
            node.getButton().setBackground(Color.black);


        } //If it actually is an obstacle, we'll definitely need to recalculate the route. The code below matches the code above, except for switching the obstacle off.
        else if (isObstacle) {

            node.setObstacle(false);

            //let's clear out the route, the considered nodes, and anything else critical before making a new one:
            clearAll();

            /**
             * Create a new path by calling AStar again, which builds the route within its own call
             */
            AStarDriver.AStar(AStarDriver.getStartNode(), AStarDriver.getDestNode());

            //With our new path, update the GUI to represent it.
            updateGUI(AStarDriver.getRoute(), AStarDriver.getClosed());

        }

    }

    /**
     * Updates the color of the route/path with blue nodes on the GUI
     *
     * @param route is constructed in the driver
     */
    public void updateRouteGUI(Stack<AStarNode> route) {

        //Since we need route later for GUI checking, let's run it through a tempRoute copy instead of the real deal(since pop will remove objects)
        Stack<AStarNode> tempRoute = (Stack<AStarNode>) route.clone();

        while (!tempRoute.isEmpty()) {
            AStarNode node = tempRoute.pop();
            node.getButton().setBackground(Color.blue);
        }

    }

    /**
     * Updates the considered nodes in the GUI to be gray
     * @param closed is the considered nodes list
     */
    public void updateConsideredGUI(HashMap<String, AStarNode> closed) {

        for (AStarNode node : closed.values()) {
            node.getButton().setBackground(Color.gray);
        }

    }

    /**
     * function collates all the GUI updating functions for cleaner code in the main listener
     * @param route holds the path
     * @param closed holds the considered nodes
     */

    public void updateGUI(Stack<AStarNode> route, HashMap<String, AStarNode> closed) {

        for (int i = 0; i < AStarDriver.WIDTHNODES; i++) {
            for (int j = 0; j < AStarDriver.HEIGHTNODES; j++) {

                AStarNode node = AStarDriver.getNodeArr()[i][j];
                if (!node.getIsObstacle()){
                    node.getButton().setBackground(Color.white);
                }
            }
        }

        updateConsideredGUI(closed);

        updateRouteGUI(route);

        AStarDriver.setStartEndPurple();

        revalidate();
    }

    /**
     * function clears any necessary data structures in AStar in order to restart the GUI path.
     * Could live in AStarDriver since that data lives there but is primarily only ever needed in the GUI.
     */
    public void clearAll(){
        AStarDriver.getClosed().clear();
        AStarDriver.getOpen().clear();
        AStarDriver.getRoute().clear();
    }
}
