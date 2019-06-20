package com.maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Maze {
    private int[][] maze;
    private static Coordinate start;
    private static Coordinate end;
    private char[][] mazeFormatted;

    /* mazeSolver solves the maze by traversing through all possible paths
     * in the maze till the end is found. The solver checks for viable moves
     * in the up, down, right and left directions. The traversed nodes are marked
     * to prevent looped movements. Furthermore, the mazeSolver will backtrack in
     * the case of a dead end. //(DFS solver)
     * */

    public char mazeSolver(Coordinate solver) {

        if (mazeFormatted[solver.getX()][solver.getY()] == 'E') {
            System.out.print("End of maze found!\n");
            return 'X';

        } else {

            char node = 0;
            int move = 0;

            //Traversed Nodes
            mazeFormatted[solver.getX()][solver.getY()] = 'T';

            print_formatted_maze();


            //Down movement
            move = solver.getX() + 1;

            if (move >= mazeFormatted.length) {
                move = 0;
            }
            char down = mazeFormatted[move][solver.getY()];
            if (down == 0 || down == 'E') {
                node = mazeSolver(new Coordinate(move, solver.getY()));
                mazeFormatted[move][solver.getY()] = node;
                print_formatted_maze();
                if (node == 'X') {
                    return 'X';
                }
            }

            //Right movement
            move = solver.getY() + 1;

            if (move >= mazeFormatted[0].length) {
                move = 0;
            }
            char right = mazeFormatted[solver.getX()][move];
            if (right == 0 || right == 'E') {
                node = mazeSolver(new Coordinate(solver.getX(), move));
                mazeFormatted[solver.getX()][move] = node;
                print_formatted_maze();
                if (node == 'X') {
                    return 'X';
                }
            }

            //Left movement
            move = solver.getY() - 1;

            if (move < 0) {
                move = mazeFormatted[0].length - 1;
            }
            char left = mazeFormatted[solver.getX()][move];
            if (left == 0 || left == 'E') {
                node = mazeSolver(new Coordinate(solver.getX(), move));
                mazeFormatted[solver.getX()][move] = node;
                if (node == 'X') {
                    return 'X';
                }
            }

            //Up movement
            move = solver.getX() - 1;

            if (move < 0) {
                move = mazeFormatted.length - 1;
            }
            char up = mazeFormatted[move][solver.getY()];
            if (up == 0 || up == 'E') {
                node = mazeSolver(new Coordinate(move, solver.getY()));
                mazeFormatted[move][solver.getY()] = node;
                if (node == 'X') {
                    return 'X';
                }
            }
        }
        /* In the case that no movements can be made return 'D'
         * */
        return 'D';
    }


    public void print_formatted_maze() {
        for (int i = 0; i < mazeFormatted.length; i++) {
            for (int j = 0; j < mazeFormatted[i].length; j++) {
                System.out.print(mazeFormatted[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void print_maze() {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                System.out.print(maze[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /* Formats the maze by converting the 2D int array
     *  to a 2D char array and sets the start and end
     *  positions
     **/

    public void mazeFormatter() {

        mazeFormatted = new char[maze.length][maze[0].length];

        //x and y are flipped due to the [row][col] structure
        mazeFormatted[start.getY()][start.getX()] = 'S';
        mazeFormatted[end.getY()][end.getX()] = 'E';

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {

                if (maze[i][j] == 1) {
                    mazeFormatted[i][j] = '#';
                }
            }
        }
        print_formatted_maze();
    }

    /*
     * mazePrint formats the maze solution and prints it
     * */

    public void mazePrint() {

        for (int i = 0; i < mazeFormatted.length; i++) {
            for (int j = 0; j < mazeFormatted[i].length; j++) {
                if (mazeFormatted[i][j] == 'D' || mazeFormatted[i][j] == 0) {
                    mazeFormatted[i][j] = ' ';
                }
            }
        }
        mazeFormatted[start.getY()][start.getX()] = 'S';
        mazeFormatted[end.getY()][end.getX()] = 'E';

        for (int i = 0; i < mazeFormatted.length; i++) {
            for (int j = 0; j < mazeFormatted[i].length; j++) {
                System.out.print(mazeFormatted[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /* The mazeBuilder reads the input maze file and generates a 2d maze
     *  it then solves the maze and displays it on the console
     *  */
    public void mazeBuilder() throws FileNotFoundException {

        Scanner input_path = new Scanner(System.in);
        System.out.print("Maze file path: ");
        String mazeFile = input_path.nextLine();
        input_path.close();

        try (Scanner sc = new Scanner(new File(mazeFile))) {

            int width = sc.nextInt();
            int height = sc.nextInt();
            maze = new int[height][width];

            //Defines start/end coordinates
            start = new Coordinate(sc.nextInt(), sc.nextInt());
            end = new Coordinate(sc.nextInt(), sc.nextInt());

            //Reads maze from input file
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    maze[i][j] = sc.nextInt();
                }
            }

            //Read maze test -- REMOVE
            print_maze();

            //Initialize formatting to 2d char array
            mazeFormatter();

            //Begin solving maze from start coordinates
            char complete = mazeSolver(start);

            if (complete != 'X') {
                System.out.print("The maze does not have a solution.\n");
                return;
            }

            //Format and print final solution
            mazePrint();


        } catch (FileNotFoundException e) {
            System.out.print("Maze file not found\n");
            throw e;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {

        Maze maze = new Maze();
        maze.mazeBuilder();

    }
}
