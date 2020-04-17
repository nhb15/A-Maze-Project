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

    //Constructor should initialize all the inital values
    public AStarGUI(int WIDTHNODES, int HEIGHTNODES){

        AStarNode[][] nodeArr = AStarDriver.getNodeArr();
        setTitle("Nate and Jake's A* Algorithm GUI");


        setLayout(new GridBagLayout());
        GridBagConstraints layoutConst = null;
        layoutConst = new GridBagConstraints();

        for (int i = 0; i < WIDTHNODES; i++){
            for (int j = 0; j < HEIGHTNODES; j++){
                int[] temp = {i,j};
                JButton button = new JButton();
                button.setActionCommand("" + i + ","+ j);
                button.setBackground(Color.gray);
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
        setSize(800,800);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent event){


        //This allows us to use constant time retrieval of which button is pushed
        String[] indices = event.getActionCommand().split(",");
        int xIdx = Integer.parseInt(indices[0]);
        int yIdx = Integer.parseInt(indices[1]);

        AStarNode[][] nodeArr = AStarDriver.getNodeArr();
        if (nodeArr[xIdx][yIdx].getIsObstacle()) {
            nodeArr[xIdx][yIdx].getButton().setBackground(Color.gray);
            nodeArr[xIdx][yIdx].setObstacle(false);
        }
        else {
            nodeArr[xIdx][yIdx].getButton().setBackground(Color.black);
            nodeArr[xIdx][yIdx].setObstacle(true);
        }

        //FIXME: REMOVE NODE FROM AVAILABLE PATHS?

        //FIXME: CHECK IF NODE WAS IN PATH, IF NOT THEN RECALCULATE PATH



    }

}
