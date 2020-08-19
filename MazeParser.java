///////////////////////////////// FILE HEADER /////////////////////////////////
//
// Title:           UWMadison_CS540_Su20_P05
// This File:       MazeParser.java
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

import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Class to parse Maze SVG Image
 * 
 * Provided by TAs
 */
public class MazeParser {
  // class variables
  private int image_width;
  private int image_height;
  private int[][] image;
  private int maze_width;
  private int maze_height;
  private HashMap<String, ArrayList<String>> maze;

  /**
   * Constructor of MazeParser
   * 
   * @param file_path maze svg image file path
   * @param maze_width width of maze
   * @param maze_height height of maze
   */
  public MazeParser(String file_path, int maze_width, int maze_height)
      throws ParserConfigurationException, IOException, SAXException {
    // Set variables
    this.maze_width = maze_width;
    this.maze_height = maze_height;
    this.maze = new HashMap<>();

    // Parse SVG Image
    // SVG file has structure of XML document
    DocumentBuilderFactory db_factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = db_factory.newDocumentBuilder();
    Document doc = db.parse(new File(file_path));

    doc.getDocumentElement().normalize();

    // Get image width and height
    Element svg_node = (Element) doc.getDocumentElement();
    this.image_width = Integer.valueOf(svg_node.getAttribute("width").toString());
    this.image_height = Integer.valueOf(svg_node.getAttribute("height").toString());

    // Initialize image matrix
    this.image = new int[image_height][image_width];

    // Get all elements with the line tag
    NodeList node_list = doc.getElementsByTagName("line");

    // Identify wall of maze
    for(int index = 0; index < node_list.getLength(); index++) {
      Node node = node_list.item(index);

      // Get each line's starting and ending points
      int x1 = Integer.valueOf(((Element) node).getAttribute("x1").toString()) - 1;
      int y1 = Integer.valueOf(((Element) node).getAttribute("y1").toString()) - 1;
      int x2 = Integer.valueOf(((Element) node).getAttribute("x2").toString()) - 1;
      int y2 = Integer.valueOf(((Element) node).getAttribute("y2").toString()) - 1;

      // Update image matrix (line segment indicating the wall of maze)
      for(int i = x1; i < x2 + 1; i++) {
        for(int j = y1; j < y2 + 1; j++) {
          image[j][i] = 1;
        }
      }
    }
  }

  /**
   * Find all neighbors for all cells in the maze and store the value in the hash map.
   * It uses parsed image array to identify where the walls are in the maze
   * 
   * @return Array of two Cells, each cell represent start cell and finish cell of the maze
   */
  public Cell[] generateMaze(){
    // ignore padding
    int delta_height = (image_height - 3) / maze_height;
    int delta_width = (image_width - 3) / maze_width;

    // Iterate through every cell in maze and identify neighbors and walls around it
    for(int row = 0; row < maze_height; row++) {
      for(int col = 0; col < maze_width; col++) {
        String cell_coordinate = String.valueOf(col + "," + row); // incidates cell location
        ArrayList<String> neighbors = new ArrayList<>(); // will store U, D, L, R
                                                         // indicating the neighboring cells of each direction
        // top
        if(image[row*delta_height+1][col*delta_width+1+delta_width/2] == 0) {
          neighbors.add("U");
        }
        // bottom
        if(image[(row+1)*delta_height+1][col*delta_width+1+delta_width/2] == 0) {
					neighbors.add("D");
        }
        // left
        if(image[row*delta_height+1+delta_height/2][col*delta_width+1] == 0) {
          neighbors.add("L");
        }
        // right
        if(image[row*delta_height+1+delta_height/2][(col+1)*delta_width+1] == 0) {
          neighbors.add("R");
        }

        // Add to Maze HashMap
        maze.put(cell_coordinate, neighbors);
      }
    }

    // Find start and finish point of the maze
    int[] start_coordinate = findStartCoordinates();
    int[] finish_coordinate = findFinishCoordinates();

    // create Cell objects for all cells in the maze, initialize neighbors
    return generateCells(start_coordinate, finish_coordinate);
  }

  /**
   * Find starting position of the maze
   * 
   * @return array contains two integer: x and y coordinate of starting postiion
   */
  private int[] findStartCoordinates() {
    int[] start_coordinate = new int[2];
    start_coordinate[1] = 0; // y coordinate of the starting point, assuming that it is at tht top

    for(int i = 0; i < maze_width; i++) {
      ArrayList<String> neighbors = maze.get("0," + String.valueOf(i));

      if(neighbors != null) { // for the cell whose neighbors exist,
        // search for the cell that does not have wall on the top
        if(neighbors.contains("U")) {
          start_coordinate[0] = i;
          break; // only need to find the first existence
        }
      }
    }

    return start_coordinate;
  }

  /**
   * Find finish point of the maze
   * 
   * @return array contains two integer: x and y coordinate of finish point
   */
  private int[] findFinishCoordinates() {
    int[] finish_coordinate = new int[2];
    finish_coordinate[1] = maze_height - 1; // y coordiante of finish point, assuming that it is at the bottom

    for(int i = 0; i < maze_width; i++) {
      ArrayList<String> neighbors = maze.get(String.valueOf(maze_height - 1) + "," + String.valueOf(i));

      if(neighbors != null) { // for the cells whose neighbors exist,
        // search for the cell that does not have wall on the bottom
        if(neighbors.contains("D")) {
          finish_coordinate[0] = i;
          break; // only need to find the first existence
        }
      }
    }

    return finish_coordinate;
  }

  /**
   * Initializing all Cell instances of the maze
   * This method will link the list of neighbors to every cell in the maze
   * 
   * @param start_coordiante int array contains two number: x and y coordinate of starting point
   * @param finish_coorindate int array contains two number: x and y coordinate of finishing point
   * @return Cell array contains start and finish Cell
   */
  private Cell[] generateCells(int[] start_coordinate, int[] finish_coordinate) {
    Cell[] start_finish_cells = new Cell[2];

    // create Cell objects for all cells in the maze
    HashMap<String, Cell> cells_map = new HashMap<>(); // place to temporary save created Cells
    for(int row = 0; row < maze_height; row++) {
      for(int col = 0; col < maze_width; col++) {
        // create new cell for the location
        Cell current_cell = new Cell(col, row);
        cells_map.put(String.valueOf(current_cell.getX() + "," + current_cell.getY()), current_cell);

        // Check for start and finish location
        if(row == start_coordinate[1] && col == start_coordinate[0]) { // start
          start_finish_cells[0] = current_cell;
        } else if(row == finish_coordinate[1] && col == finish_coordinate[0]) { // finish
          start_finish_cells[1] = current_cell;
        }
      }
    }

    // for all cells, create list of neighbors
    for(String key: maze.keySet()) {
      Cell current_cell = cells_map.get(key);
      // neighbors of current_cell
      ArrayList<String> neighbors = maze.get(key);
      ArrayList<Cell> neighboring_cells = new ArrayList<>();
      
      // iterate through neighbors and find proper adjacent cell
      for(int neighbor_index = 0; neighbor_index < neighbors.size(); neighbor_index++) {
        Cell adjacent_cell;
        // Retrieve Current Cell's position
        int sep_index = key.indexOf(",");
        int x = Integer.valueOf(key.substring(sep_index + 1, key.length())); // column
        int y = Integer.valueOf(key.substring(0, sep_index)); // row

        // Check for neighbors
        if(neighbors.get(neighbor_index) == "U") {
          if(y == 0) { // for the top-most row, it does not have neighbors upward
            continue;
          }
          adjacent_cell = cells_map.get(String.valueOf((y - 1) + "," + x));
        } else if(neighbors.get(neighbor_index) == "D") {
          if(y == maze_height - 1) { // for the bottom-most row, it does not have neighbors down
            continue;
          }
          adjacent_cell = cells_map.get(String.valueOf(y + 1) + "," + x);
        } else if(neighbors.get(neighbor_index) == "L") {
          adjacent_cell = cells_map.get(String.valueOf(y + "," + (x - 1)));
        } else { // Right
          adjacent_cell = cells_map.get(String.valueOf(y + "," + (x + 1)));
        }

        neighboring_cells.add(adjacent_cell);
      }
      current_cell.setNeighbors(neighboring_cells);
    }

    return start_finish_cells;
  }

  /**
   * Generate string of maze plot
   * using | for vertical walls and -- for horizontal walls and + for intersections
   * 
   * @return maze plot string
   */
  public String getMazePlotString() {
    StringBuilder sb = new StringBuilder();

    for(int row = 0; row < maze_height; row++) {
      // Checking Upper Cells
      sb.append("+");
      for(int col = 0; col < maze_width; col++) {
        ArrayList<String> neighbors = maze.get(String.valueOf(col + "," + row));

        // Check for existence of "U" in neighbors array
        if(neighbors.contains("U")) {
          // not having wall above
          sb.append("  +");
        } else {
          // having wall above
          sb.append("--+");
        }
      }
      sb.append("\n");

      // Checking Right Cells
      sb.append("|");
      for(int col = 0; col < maze_width; col++) {
        ArrayList<String> neighbors = maze.get(String.valueOf(col + "," + row));

        // Check for existence of "R" in neighbors array
        if(neighbors.contains("R")) {
          // not having wall rightside
          sb.append("   ");
        } else {
          // having wall rightside
          sb.append("  |");
        }
      }
      sb.append("\n");
    }

    // Check for Bottom Wall (Exit)
    sb.append("+");
    for(int col = 0; col < maze_width; col++) {
      ArrayList<String> neighbors = maze.get(String.valueOf(col + "," + (maze_height - 1)));

      // Check for existence of "D" in neighbors array
      if(neighbors.contains("D")) {
        // not having wall below
        sb.append("  +");
      } else {
        // having wall below
        sb.append("--+");
      }
    }
    sb.append("\n");

    return sb.toString(); // generate string
  }

  /**
   * Generate stirng of successor matrix of the maze
   * contains h lines, each line containing w strings, comma separated,
   * each strings represent the list of possible successors, a subset of the characters "U" "D" "L" "R"
   * 
   * @return successor matrix string
   */
  public String getSuccessorMatrixString() {
    StringBuilder sb = new StringBuilder();

    for(int row = 0; row < maze_height; row++) {
      for(int col = 0; col < maze_width; col++) {
        ArrayList<String> neighbors = maze.get(String.valueOf(col + "," + row));

        // Iterate through all successors
        for(String successor : neighbors) {
          switch(successor) {  // Able to use String in switch statement (JDK 7 or after)
            case "U": sb.append("U");
                      break;
            case "D": sb.append("D");
                      break;
            case "L": sb.append("L");
                      break;
            case "R": sb.append("R");
                      break;
            default: System.out.println("Error, Invalid Successor");
                     System.exit(1);
          }
        }

        // Separated by comma
        if(col != maze_width - 1) {
          sb.append(",");
        }
      }

      sb.append("\n"); // add new line
    }

    return sb.toString();
  }
}