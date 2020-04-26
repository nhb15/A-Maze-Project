package AStarMazeProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Stack;

/**
 * WHEN A USER ADDS A BLOCK, CHECK IF THE BLOCK IS IN THE CURRENT ROUTE. IF NOT, DO NOT RECALCULATE. IF SO, RECALCULATE.
 *
 * POTENTIALLY USING A GRAPH FOR GUI INSTEAD OF NODES? NEED TO CALCULATE EDGES AND WOULD HELP COST FROM BASE
 */

public class AStarGUI extends JFrame implements ActionListener {


    //Constructor should initialize all the inital values
    public AStarGUI(int WIDTHNODES, int HEIGHTNODES) {

        AStarNode[][] nodeArr = AStarDriver.getNodeArr();
        setTitle("Nate and Jake's A* Algorithm GUI");


        setLayout(new GridBagLayout());
        GridBagConstraints layoutConst = null;
        layoutConst = new GridBagConstraints();

        for (int i = 0; i < WIDTHNODES; i++) {
            for (int j = 0; j < HEIGHTNODES; j++) {
                int[] temp = {i, j};
                JButton button = new JButton();
                button.setActionCommand("" + i + "," + j);
                button.setBackground(Color.white);
                button.addActionListener(this);

                nodeArr[i][j].setButton(button);

                layoutConst = new GridBagConstraints();
                layoutConst.gridx = i;
                layoutConst.gridy = j;

                layoutConst.ipady = 15;
                add(button, layoutConst);
            }
        }


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {


        //This allows us to use constant time retrieval of which button is pushed
        String[] indices = event.getActionCommand().split(",");
        int xIdx = Integer.parseInt(indices[0]);
        int yIdx = Integer.parseInt(indices[1]);

        AStarNode[][] nodeArr = AStarDriver.getNodeArr();
        /**
         * If our node is nothing, and the user clicks on it, let's turn it black and show it as an obstacle.
         *
         * If our node is on the ROUTE and the user clicks on it, we need to turn it black AND recalculate our route.
         *
         * IF our node is already an obstacle and the user clicks on it, let's turn it back to the color it was before and remove the obstacle.
         * FIXME: Do we want to do advanced calculations here to determine if we should recalculate route or just always do it?
         * For now, let's always recalculate the route.
         *
         */
        AStarNode node = nodeArr[xIdx][yIdx];
        boolean isObstacle = node.getIsObstacle();
        //System.out.println(AStarDriver.getRoute().peek().getIsObstacle());
        if (!isObstacle) {

            node.setObstacle(true);

            if (AStarDriver.getRoute().contains(node)) {


                //update path
                clearAll();

                AStarDriver.AStar(AStarDriver.getStartNode(), AStarDriver.getDestNode());
                //AStarDriver.routeBuilder(AStarDriver.getStartNode(), AStarDriver.getDestNode());

                updateGUI(AStarDriver.getRoute(), AStarDriver.getClosed());
            }
            //Once we update path, make sure the node we actually clicked is black and shows itself correctly.
            node.getButton().setBackground(Color.black);


        } else if (isObstacle) {

            node.setObstacle(false);
            node.getButton().setBackground(Color.WHITE);
            clearAll();

            AStarDriver.AStar(AStarDriver.getStartNode(), AStarDriver.getDestNode());

            updateGUI(AStarDriver.getRoute(), AStarDriver.getClosed());

        }

        //FIXME: REMOVE NODE FROM AVAILABLE PATHS?

        //FIXME: CHECK IF NODE WAS IN PATH, IF NOT THEN RECALCULATE PATH


    }

    /**
     * Updates the color of the route with blue nodes on the GUI
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

    //FIXME: NEED A NODES CONSIDERED UPDATE AS WELL

    public void updateConsideredGUI(HashMap<String, AStarNode> closed) {

        for (AStarNode node : closed.values()) {
            node.getButton().setBackground(Color.gray);
        }

    }

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

    public void clearAll(){
        AStarDriver.getClosed().clear();
        AStarDriver.getOpen().clear();
        AStarDriver.getRoute().clear();
    }
}
