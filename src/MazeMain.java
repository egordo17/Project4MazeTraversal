/**
 * Main program to run a maze solver either in command line or
 * in a GUI
 * @author Manuel A. Perez-Quinones <perez.quinones@uncc.edu>
 */
public class MazeMain {

    public static void main(String[] args)
    {

        if (args.length == 0) {
            System.out.println("Usage:");
            System.out.println(" java MazeMain [-gui] inputfile");
            System.exit(1);
        }

        boolean gui = false;
        String inFile = null;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equalsIgnoreCase("-gui"))
                gui = true;
            else
                inFile = args[i];
        }

        if (inFile == null) {
            System.out.println("Usage:");
            System.out.println(" java MazeMain [-gui] inputFile");
            System.exit(1);
        }

        if (gui) {
            GMazeSolver labyrinth = new GMazeSolver(inFile);
        }
        else {
            try {
                Maze maze = new MazeSolver(inFile);
                System.out.println("TRAVERSE Maze:\n"+maze);
                if (maze.traverse())
                    System.out.println("Maze solved\n"+maze);
                else
                    System.out.println("Maze NOT solved\n"+maze);

                maze = new MazeSolver(inFile);
                System.out.println("PICK UP COINS Maze:\n"+maze);
                int gold = maze.pickupGoldCoins();
                System.out.println("Picked up coins: "+gold+"\n"+maze);
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace(System.out);
            }
        }
    }
}
/*
 * Copyright: This programming assignment specification and the provided sample
 * code are protected by copyright. The professor is the exclusive owner of
 * copyright of this material. You are encouraged to take notes and make copies
 * of the specification and the source code for your own educational use.
 * However, you may not, nor may you knowingly allow others to reproduce or
 * distribute the materials publicly without the express written consent of the
 * professor. This includes providing materials to commercial course material
 * suppliers such as CourseHero and other similar services. Students who
 * publicly distribute or display or help others publicly distribute or display
 * copies or modified copies of this material may be in violation of University
 * Policy 406, The Code of Student Responsibility
 * https://legal.charlotte.edu/policies/up-406.
 */
