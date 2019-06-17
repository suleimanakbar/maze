package test.akbar.maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Maze {
    private int[][] maze;
    private static Coordinate start;
    private static Coordinate end;
    private char[][] maze_formatted;


    public char mazeSolver(Coordinate solver) {


        if (maze_formatted[solver.getX()][solver.getY()] == 'E') {
            System.out.print("End of maze found!" + "\n");
            return 'X';

        } else {

            char node = 0;

            //Traversed Nodes
            maze_formatted[solver.getX()][solver.getY()] = 'T';

            //print_formatted_maze();

            //Up
            int move = solver.getX() - 1;

            //Ensures the maze boundary
            if (move < 0) {
                move = maze_formatted.length - 1;
            }
            char up = maze_formatted[move][solver.getY()];
            if (up == 0 || up == 'E') {
                node = mazeSolver(new Coordinate(move, solver.getY()));
                maze_formatted[move][solver.getY()] = node;
                if (node == 'X') {
                    return 'X';
                }
            }

            //Down
            move = solver.getX() + 1;

            if (move >= maze_formatted.length) {
                move = 0;
            }
            char down = maze_formatted[move][solver.getY()];
            if (down == 0 || down == 'E') {
                node = mazeSolver(new Coordinate(move, solver.getY()));
                maze_formatted[move][solver.getY()] = node;
                if (node == 'X') {
                    return 'X';
                }
            }

            //Right
            move = solver.getY() + 1;

            if (move >= maze_formatted[0].length) {
                move = 0;
            }
            char right = maze_formatted[solver.getX()][move];
            if (right == 0 || right == 'E') {
                node = mazeSolver(new Coordinate(solver.getX(), move));
                maze_formatted[solver.getX()][move] = node;
                if (node == 'X') {
                    return 'X';
                }
            }

            //Left
            move = solver.getY() - 1;

            if (move < 0) {
                move = maze_formatted[0].length - 1;
            }
            char left = maze_formatted[solver.getX()][move];
            if (left == 0 || left == 'E') {
                node = mazeSolver(new Coordinate(solver.getX(), move));
                maze_formatted[solver.getX()][move] = node;
                if (node == 'X') {
                    return 'X';
                }
            }
        }
        //In case that no movements can be made return 'D'
        return 'D';
    }


    public void print_formatted_maze() {
        for (int i = 0; i < maze_formatted.length; i++) {
            for (int j = 0; j < maze_formatted[i].length; j++) {
                System.out.print(maze_formatted[i][j] + " ");
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

    public void mazeFormatter() {

        maze_formatted = new char[maze.length][maze[0].length];

        //x and y are flipped due to the [row][col] structure
        maze_formatted[start.getY()][start.getX()] = 'S';
        maze_formatted[end.getY()][end.getX()] = 'E';

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {

                if (maze[i][j] == 1) {
                    maze_formatted[i][j] = '#';
                }
            }
        }

        print_formatted_maze();
    }

    public void mazeFormatter2() {


        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze_formatted[i][j] == 'D' || maze_formatted[i][j] == 0) {
                    maze_formatted[i][j] = ' ';
                }
            }
        }
        maze_formatted[start.getY()][start.getX()] = 'S';
        maze_formatted[end.getY()][end.getX()] = 'E';
        print_formatted_maze();
    }

    public void mazeBuilder(String filename) throws FileNotFoundException {

        try (Scanner sc = new Scanner(new File(filename))) {

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

            //Read maze test
            print_maze();
            //Initialize formatting to 2d char array
            mazeFormatter();
            //Begin solving maze from start coordinates
            mazeSolver(start);
            //Format and print final solution
            mazeFormatter2();


        } catch (FileNotFoundException e) {
            System.out.print("Maze file not found");
            throw e;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {

        Maze maze = new Maze();
        maze.mazeBuilder("Samples/input.txt");

    }
}
