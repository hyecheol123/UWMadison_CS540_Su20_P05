# UWMadison_CS540_Su20_P05
Assignment Webpage: http://pages.cs.wisc.edu/~yw/CS540P5S20E.htm  
Repository for the 55th programming assignment of UW-Madison CS540 Summer 2020 course (Introduction to Artificial Intelligence)

## Goals
Create random maze and compare multiple uninformed and informed search algorithms


## Dataset
Create h = 58 by w = 57 rectangular orthogonal maze by parsing an image from the [Maze Generator](http://www.mazegenerator.net/)

### Data Pre-processing
Parse `svg` format image file to text
  - vertical wall: `|`
  - horizontal wall: `--`
  - intersection: `+`


## Tasks
- Parse maze
  - Related Question: Q1, Q2
- Uninformed Search (BFS, DFS)
  - Related Question: Q3, Q5, Q6
- Informed Search (A*)
  - Related Question: Q7, Q8, Q9


## Questions
- **Q1**  
  (`plot`) Enter the plot of the maze
- **Q2**  
  (`succ`) Enter the successor matrix of the maze (h lines, each line containing w strings, comma separated, each strings represent the list of possible successors, a subset of the characters "U" "D" "L" "R").
- **Q3**  
  (`solution`) Enter the action sequence (one line, a sequence of characters "U" "D" "L" "R", no comma in between them).
- **Q4**  
  (`plot_solution`) Enter the plot of the maze with the solution
- **Q5**  
  (`bfs`) Enter the list of states searched by BFS (h lines, each line containing w integers either 0 or 1, 1 means searched, comma separated).
- **Q6**  
  (`dfs`) Enter the list of states searched by DFS (h lines, each line containing w integers either 0 or 1, 1 means searched, comma separated).
- **Q7**  
  (`distances`) Enter the Manhattan distances to the goal for each cell in the maze (h lines, each line containing w integers, comma separated).
- **Q8**  
  (`a_manhattan`) Enter the list of states searched by A* with Manhattan distance to the goal as the heuristic (h lines, each line containing w integers either 0 or 1, 1 means searched, comma separated).
- **Q9**  
  (`a_euclidean`) Enter the list of states searched by A* with Euclidean distance to the goal as the heuristic (h lines, each line containing w integers either 0 or 1, 1 means searched, comma separated).