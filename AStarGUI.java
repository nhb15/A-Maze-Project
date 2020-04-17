package AStarMazeProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * WHEN A USER ADDS A BLOCK, CHECK IF THE BLOCK IS IN THE CURRENT ROUTE. IF NOT, DO NOT RECALCULATE. IF SO, RECALCULATE.
 *
 * POTENTIALLY USING A GRAPH FOR GUI INSTEAD OF NODES? NEED TO CALCULATE EDGES AND WOULD HELP COST FROM BASE
 */

public class AStarGUI extends JFrame implements ActionListener {

    private final int WIDTHNODES = 20;
    private final int HEIGHTNODES = 20;

    private AStarNode[][] nodeArr = new AStarNode[WIDTHNODES][HEIGHTNODES];

    //Constructor should initialize all the inital values
    public AStarGUI(){

        setTitle("Nate and Jake's A* Algorithm GUI");


        setLayout(new GridBagLayout());
        GridBagConstraints layoutConst = null;
        layoutConst = new GridBagConstraints();

        /**
         * FIXME: Do we want an array of nodes? or our nodes hold the index?
         */


        for (int i = 0; i < WIDTHNODES; i++){
            for (int j = 0; j < HEIGHTNODES; j++){
                int[] temp = {i,j};
                JButton button = new JButton();
                button.setActionCommand("" + i + ","+ j);
                button.setBackground(Color.gray);
                button.addActionListener(this);
                //buttonArr[i][j] = button;

                AStarNode node = new AStarNode(temp, button);
                nodeArr[i][j] = node;

                layoutConst = new GridBagConstraints();
                layoutConst.gridx = i;
                layoutConst.gridy = j;

                layoutConst.ipady = 15;
                add(button, layoutConst);
            }
        }


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,800);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent event){


        //This allows us to use constant time retrieval of which button is pushed
        String[] indices = event.getActionCommand().split(",");
        int xIdx = Integer.parseInt(indices[0]);
        int yIdx = Integer.parseInt(indices[1]);

        nodeArr[xIdx][yIdx].getButton().setBackground(Color.black);
        nodeArr[xIdx][yIdx].setObstacle(true);
        //FIXME: REMOVE NODE FROM AVAILABLE PATHS?

        //FIXME: CHECK IF NODE WAS IN PATH, IF NOT THEN RECALCULATE PATH


        //if (event.getSource() == )
        //obtain the node, change the color, make it an obstacle or not an obstacle

    }

    /**
     * Have a button to click to add obstacles,
     * have a button that allows for recalculation
     */


}
