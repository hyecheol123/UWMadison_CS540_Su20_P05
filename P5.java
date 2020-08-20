///////////////////////////////// FILE HEADER /////////////////////////////////
//
// Title:           UWMadison_CS540_Su20_P05
// This File:       P5.java
// Files:           P5.java, MazeParser.java, Cell.java
// External Class:  None
//
// GitHub Repo:    https://github.com/hyecheol123/UWMadison_CS540_Su20_P05
//
// Author
// Name:            Hyecheol (Jerry) Jang
// Email:           hyecheol.jang@wisc.edu
// Lecturer's Name: Young Wu
// Course:          CS540 (LEC 002 / Epic), Summer 2020
//
///////////////////////////// OUTSIDE REFERENCE  //////////////////////////////
//
// List of Outside Reference
//   1.
//
////////////////////////////////// KNOWN BUGS /////////////////////////////////
//
// List of Bugs
//   1.
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 * Class with main method for P5
 */
public class P5 {
  // Constants
  private static final int HEIGHT = 58;
  private static final int WIDTH = 57;
  private static final String SVG_PATH = "maze.svg";

  /**
   * Main method for P5
   * 
   * @param args Command Line Arguments (CLAs)
   */
  public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
    // Get net_id and result file location
    Scanner console_scnr = new Scanner(System.in);
    System.out.print("Enter Result File Location(Name): ");
    String result_file_loc = console_scnr.nextLine();
    System.out.println("Need net_id to properly format result file");
    System.out.print("Enter Your UWMadison net_id: ");
    String net_id = console_scnr.nextLine();
    console_scnr.close();
    // Initialize result_file_writer
    BufferedWriter result_file_writer = new BufferedWriter(new FileWriter(new File(result_file_loc)));
    // Format header of result file
    result_file_writer.append("Outputs:\n@id\n" + net_id + "\n");
    result_file_writer.flush();

    // Parse Image
    MazeParser maze_parser = new MazeParser(SVG_PATH, WIDTH, HEIGHT);
    Cell[] start_finish_cells = maze_parser.generateMaze();
    Cell start = start_finish_cells[0];
    Cell finish = start_finish_cells[1];
    System.out.println("Finish Parsing SVG Image");

    // Q1: Enter the plot of the maze
    result_file_writer.append("@plot\n");
    result_file_writer.append(maze_parser.getMazePlotString());
    result_file_writer.flush();
    System.out.println("Finish Q1");

    // Q2: Enter the successor matrix of the maze
    result_file_writer.append("@succ\n");
    result_file_writer.append(maze_parser.getSuccessorMatrixString());
    result_file_writer.flush();
    System.out.println("Finish Q2");

    // Q5: Enter the list of states searched by BFS
    boolean[][] is_visited = new boolean[WIDTH][HEIGHT]; // matrix to save BFS visited Cell information
    System.out.println("BFS Visited Node Count: " + bfs(start, finish, is_visited)); // search path by bfs
    result_file_writer.append("@bfs\n");
    for(int row = 0; row < HEIGHT; row++) {
      for(int col = 0; col < WIDTH; col++) {
        if(is_visited[col][row] == true) {
          result_file_writer.append("1");
        } else {
          result_file_writer.append("0");
        }
        if(col != (WIDTH - 1)) {
          result_file_writer.append(",");
        }
      }
      result_file_writer.append("\n");
    }
    result_file_writer.flush();
    System.out.println("Finish Q5");

    // Q6: Enter the list of states searched by DFS
    is_visited = new boolean[WIDTH][HEIGHT]; // matrix to save DFS visited Cell information
    dfs(start, finish, is_visited, false); // run DFS Algorithm
    int dfs_count = 0;
    result_file_writer.append("@dfs\n");
    for(int row = 0; row < HEIGHT; row++) {
      for(int col = 0; col < WIDTH; col++) {
        if(is_visited[col][row] == true) {
          result_file_writer.append("1");
          dfs_count++;
        } else {
          result_file_writer.append("0");
        }
        if(col != (WIDTH - 1)) {
          result_file_writer.append(",");
        }
      }
      result_file_writer.append("\n");
    }
    result_file_writer.flush();
    System.out.println("BFS Visited Node Count: " + dfs_count);
    System.out.println("Finish Q6");

    // Q3: Enter the action sequence
    result_file_writer.append("@solution\n");
    result_file_writer.append(getSolutionPath(finish));
    result_file_writer.append("\n");
    result_file_writer.flush();
    System.out.println("Finish Q3");

    // close result file_writer
    result_file_writer.append("@answer_10\nNone");
    result_file_writer.close();
  }

  /**
   * BFS algorithm
   * 
   * @param start Cell indicates starting point of the maze
   * @param finish Cell indicates finish point of the maze
   * @param is_visited 2D boolean matrix indicating whether each cell has visited or not
   * @return Number of visited cells while performing BFS
   */
  private static int bfs(Cell start, Cell finish, boolean[][] is_visited) {
    int num_visited = 0;
    LinkedList<Cell> queue = new LinkedList<>();
    boolean reached = false;

    // Start by adding start cell on the queue
    queue.add(start);
    is_visited[start.getX()][start.getY()] = true;
    num_visited++;

    // Terminal Condition: Queue is empty, or reached to the end of maze
    while(!queue.isEmpty() && !reached) {
      Cell current = queue.poll(); // get head element from queue (remove the head)

      // Check for termination
      if(current.getX() == finish.getX() && current.getY() == finish.getY()) {
        reached = true;
        continue; // no need to check for neighbors
      }

      ArrayList<Cell> neighbors = current.getNeighbors();
      for(Cell neighbor : neighbors) {
        // only put neighboring Cell to the queue when it is not visited
        if(is_visited[neighbor.getX()][neighbor.getY()] == false) {
          is_visited[neighbor.getX()][neighbor.getY()] = true;
          queue.add(neighbor);
          num_visited++;
        }
      }
    }

    return num_visited;
  }

  /**
   * DFS Algorithm with path finding
   * 
   * @param current current Cell
   * @param finish finishing Cell
   * @param is_visited 2D boolean matrix indicating whether each cell has visited or not
   * @param reached whether the algorithm reached to termination or not
   * @return 
   */
  private static boolean dfs(Cell current, Cell finish, boolean[][] is_visited, boolean reached) {
    // current Cell is visited
    is_visited[current.getX()][current.getY()] = true;

    // check for termination (reached finish cell)
    if(((current.getX() == finish.getX()) && (current.getY() == finish.getY()))) {
      reached = true;
    }

    // retrive parent
    Cell parent = current.getParent();

    // For all successor, call DFS recursively
    ArrayList<Cell> successors = current.getNeighbors();
    for(Cell successor : successors) {
      if(reached == false) { // when reached to the final state, no need to search more
        if(parent == null) { // no need to check parents' index
          successor.setParent(current); // set parent of succssor (current is successor's parent)
          reached = dfs(successor, finish, is_visited, reached); // call dfs with successor
        } else {
          // should not call dfs on parent
          if(!((parent.getX() == successor.getX()) && parent.getY() == successor.getY())) {
            successor.setParent(current); // set parent of succssor (current is successor's parent)
            reached = dfs(successor, finish, is_visited, reached); // call dfs with successor
          }
        }
      } else {
        break;
      }
    }

    return reached;
  }

  /**
   * Backtrack parent Cell from finish Cell and generate solution path String
   * 
   * @param finish Cell indicates finish point of the maze
   * @return solution path
   */
  private static String getSolutionPath(Cell finish) {
    String path = "";
    Cell current = finish;

    while(current.getParent() != null) { // until reaching to the starting Cell
      int parent_x = current.getParent().getX();
      int parent_y = current.getParent().getY();

      // Check for U, D, L, R and modify String
      if((parent_x == current.getX()) && (parent_y == current.getY() - 1)) { // D
        path = "D" + path;
      } else if((parent_x == current.getX()) && (parent_y == current.getY() + 1)) { // U
        path = "U" + path;
      } else if((parent_x == current.getX() - 1) && (parent_y == current.getY())) { // R
        path = "R" + path;
      } else { // L
        path = "L" + path;
      }

      // go to parent
      current = current.getParent();
    }

    return path;
  }
}