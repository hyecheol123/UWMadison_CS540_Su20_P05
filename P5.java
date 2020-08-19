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

    // close result file_writer
    result_file_writer.append("@answer_10\nNone");
    result_file_writer.close();
  }
}