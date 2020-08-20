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
public class Cell implements Comparable<Cell> {
  // fields
  private int x;
  private int y;
  private ArrayList<Cell> neighbors;
  private Cell parent;
  // For A* Search
  private double g;
  private double f; // g + h

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

  /**
   * public access point for g
   * 
   * @return cost until this cell
   */
  public double getG() {
    return g;
  }

  /**
   * Public setter for g
   * 
   * @param g new cost until this cell
   */
  public void setG(double g) {
    this.g = g;
  }

  /**
   * public setter for f
   * 
   * @param f sum of cost until this cell and heuristic
   */
  public void setF(double f) {
    this.f = f;
  }

  /**
   * Compare f value of current Cell and target Cell
   * Used for A* Search
   * 
   * @param target comparing target Cell
   * @return 1 if current cell's f value is larger, -1 if current cell's f value is smaller, 0 for equal
   */
  @Override
  public int compareTo(Cell target) {
    if(this.f > target.f) {
      return 1;
    } else if(this.f < target.f) {
      return -1;
    } else {
      return 0;
    }
  }
}