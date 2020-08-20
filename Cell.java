///////////////////////////////// FILE HEADER /////////////////////////////////
//
// Title:           UWMadison_CS540_Su20_P05
// This File:       Cell.java
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

import java.util.ArrayList;

/**
 * Class representing a cell in the Maze
 */
public class Cell {
  // fields
  private int x;
  private int y;
  private ArrayList<Cell> neighbors;
  private Cell parent;

  /**
   * Constructor for Cell
   * 
   * @param x x coordinate of maze
   * @param y y coordinate of maze
   */
  public Cell(int x, int y) {
    this.x = x;
    this.y = y;
    neighbors = null;
    parent = null;
  }

  /**
   * public access point for x
   * 
   * @return x coordinate value
   */
  public int getX() {
    return x;
  }

  /**
   * public access point for y
   * 
   * @return y coordinate value
   */
  public int getY() {
    return y;
  }

  /**
   * Public setter for neighbors
   * 
   * @param neighbors ArrayList of Cells contains information of neighboring cells
   */
  public void setNeighbors(ArrayList<Cell> neighbors) {
    this.neighbors = neighbors;
  }

  /**
   * public access point for neighbors
   * 
   * @return ArrayList contains information of neighboring cells
   */
  public ArrayList<Cell> getNeighbors() {
    return neighbors;
  }

  /**
   * Public setter for parent
   * 
   * @param parent parent of current cell in the path
   */
  public void setParent(Cell parent) {
    this.parent = parent;
  }

  /**
   * Public access point for parent
   * 
   * @return parent indicating previous Cell visited
   */
  public Cell getParent() {
    return parent;
  }
}