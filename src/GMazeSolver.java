/**
 * GMazeSolver.java
 * Author: Lewis/Chase
 *
 * Represents a maze to be solved recursively.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
@SuppressWarnings("serial")

/**
 * GMazeSolver, a slight modification of the maze program
 * from Lewis/Chase to show graphically a maze solver.
 * @author Lewis/Chase/Perez-Quinones
 */

public class GMazeSolver {

    private final int BOX_WIDTH = 100;
    private final int BORDER = 10;

    private JFrame win = null;
    private JComponent drawArea;
    private JLabel message;


    private boolean solved = false;

    private MazeSolver maze;
    private MazeSolver.CELL[][] savedGrid;


    /**
     * GMazeSolver - creates the graphical user interface and sets listeners
     * so that the maze can be evaluated.
     */
    public GMazeSolver(String inFile) 
    {
        maze = new MazeSolver(inFile);
        savedGrid();

        // create window
        win = new JFrame("Maze");
        win.getContentPane().setLayout(new BorderLayout());

        // Message panel on the North part of the border layout
        // showing a short message with the file name being
        // shown and a second message panel that will show
        // the results of the execution
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(new JLabel("File: "+inFile));
        message = new JLabel("");
        panel.add(message);
        win.getContentPane().add(panel, BorderLayout.NORTH);

        // The south part of the border layout shows
        // a row of buttons. Clear resets the grid to
        // the value before the traverse was run.
        // Traverse calls the traverse() method.
        // Pickup calls the pickupGoldCoins() method.
        panel = new JPanel();
        JButton clear = new JButton("Clear");
        clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                resetGrid();
                message.setText("");
                drawArea.repaint();
                solved = false;
            }
        });
        panel.add(clear);

        JButton traverse = new JButton("Traverse");
        traverse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                resetGrid();
                try {
                    if (maze.traverse())
                        message.setText("• Success");
                    else
                        message.setText("• Failed");
                    drawArea.repaint();
                }
                catch (IllegalAccessException e)
                {
                    e.printStackTrace(System.out);
                }
                solved = true;
            }
        });
        panel.add(traverse);

        JButton gold = new JButton("Pickup");
        gold.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                resetGrid();
                try {
                    int coins = maze.pickupGoldCoins();
                    message.setText("• Coins collected: "+coins);
                    drawArea.repaint();
                }
                catch (IllegalAccessException e)
                {
                    e.printStackTrace(System.out);
                }
                solved = true;
            }
        });
        panel.add(gold);
        win.getContentPane().add(panel, BorderLayout.SOUTH);

        // The center part of the border layout contains
        // the grid area of the maze.
        drawArea = new JComponent() {
            public void paintComponent(Graphics g) {
                MazeSolver.CELL[][] grid = maze.getGrid();
                for (int i = 0; i < grid.length; i++) {
                    for (int j = 0; j < grid[i].length; j++) {
                        // draw square
                        drawCell(g, i, j);
                    }
                }
            }

            public void drawCell(Graphics g, int row, int col) {
                MazeSolver.CELL[][] grid = maze.getGrid();
                if (grid[row][col] == MazeSolver.CELL.OPEN)
                    g.setColor(Color.white);
                else if (grid[row][col] == MazeSolver.CELL.WALL)
                    g.setColor(Color.black);
                else if (grid[row][col] == MazeSolver.CELL.VISITED)
                    g.setColor(Color.red);
                else if (grid[row][col] == MazeSolver.CELL.PATH)
                    g.setColor(Color.green);
                else if (grid[row][col] == MazeSolver.CELL.GOLDCOIN)
                    g.setColor(Color.yellow);

                g.fillRect(BORDER + col * BOX_WIDTH, BORDER 
                    + row * BOX_WIDTH, BOX_WIDTH, BOX_WIDTH);
                g.setColor(Color.black);
                g.drawRect(BORDER + col * BOX_WIDTH, BORDER 
                    + row * BOX_WIDTH, BOX_WIDTH, BOX_WIDTH);
            }
        };

        // TODO Change this to cycle through all values of cells
        drawArea.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent arg0) {
                if (!solved) {
                    int col = (arg0.getX() - BORDER) / BOX_WIDTH;
                    int row = (arg0.getY() - BORDER) / BOX_WIDTH;
                    MazeSolver.CELL[][] grid = maze.getGrid();
                    if (grid[row][col] == MazeSolver.CELL.OPEN)
                        grid[row][col] = MazeSolver.CELL.WALL;
                    else if (grid[row][col] == MazeSolver.CELL.WALL)
                        grid[row][col] = MazeSolver.CELL.OPEN;
                    savedGrid();
                    drawArea.repaint();
                }
            }
        });
        win.getContentPane().add(drawArea, BorderLayout.CENTER);

        // Set the window size to fit everything snuggly
        win.setSize(
            maze.getSize() * BOX_WIDTH + 2 * BORDER,
            maze.getSize() * BOX_WIDTH + 4 * BORDER + 70);
        win.setVisible(true);
    }

    /**
     * Saves the puzzle about to be used so that we can run the
     * same puzzle multiple times.
     */
    private void savedGrid() {
        MazeSolver.CELL[][] grid = maze.getGrid();
        // allocate new grid
        savedGrid = new MazeSolver.CELL[maze.getSize()][maze.getSize()];

        // ... and save the values
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[row].length; column++)
                savedGrid[row][column] = grid[row][column];
        }
    }

    /**
     * Copies back the saved grid into the grid variable.
     */
    private void resetGrid() {
        // Because g is returned as a reference, this
        // actually changes the maze object... this is bad,
        // it is a violation of encapsulation. Don't do
        // this at home.
        MazeSolver.CELL[][] g = maze.getGrid();
        for (int row = 0; row < g.length; row++)
            for (int column = 0; column < g[row].length; column++)
                g[row][column] = savedGrid[row][column];
        drawArea.repaint();
    }
}
