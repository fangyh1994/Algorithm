import java.util.Arrays;

import edu.princeton.cs.algs4.*;

/**
 * @author Mark
 *
 */
public class Board {
  
  private int gridLength;
  private int[][] blocks; 
  
  /**
   * construct a board from an n-by-n array of blocks.
   * (where blocks[i][j] = block in row i, column j)
   */
  public Board(int[][] blocks) {
    if (blocks == null) {
      throw new java.lang.NullPointerException();
    }
    gridLength = blocks[0].length;
    this.blocks = new int[gridLength][gridLength];
    for (int i = 0; i < gridLength;i++) {
      for (int j = 0; j< gridLength;j++) {
        this.blocks[i][j] = blocks[i][j];
      }
    }
  }
  
  /**
   * board dimension n.
   */
  public int dimension() {
    return gridLength;
  }
  
  /**
   * number of blocks out of place.
   */
  public int hamming() {
    int count = 0;
    for (int i = 0; i < gridLength; i++) {
      for (int j = 0; j < gridLength;j++) {
        if (blocks[i][j] != 0 && blocks[i][j] != i * gridLength + j + 1) {
          count++;
        }
      }
    }
    return count;
  }
  
  /**
   * sum of Manhattan distances between blocks and goal.
   */
  public int manhattan() {
    int count = 0;
    int rowdiff = 0;
    int coldiff = 0;
    for (int i = 0; i < gridLength; i++) {
      for (int j = 0; j < gridLength;j++) {
        if (blocks[i][j] != 0) {
          rowdiff = Math.abs( (blocks[i][j] - 1) / gridLength - i);
          coldiff = Math.abs( (blocks[i][j] - 1) % gridLength - j);
          count = count + rowdiff + coldiff;
        }
      }
    }
    return count;
  }
  
  /**
   * is this board the goal board?.
   */
  public boolean isGoal() {
    for (int i = 0; i < gridLength; i++) {
      for (int j = 0; j < gridLength;j++) {
        if (i == gridLength - 1 && j == gridLength - 1) {
          break;
        }
        if (blocks[i][j] != i * blocks[0].length + j + 1) {
          return false;
        }
      }
    }
    if (blocks[gridLength - 1][gridLength - 1] != 0) {
      return false;
    }
    return true;
  }
  
  /**
   * a board that is obtained by exchanging any pair of blocks.
   */
  public Board twin() {
    Board twinBoard;
    twinBoard = new Board(blocks);
    if (gridLength <= 1) {
      return null;
    }
    if (twinBoard.blocks[0][0] != 0 && twinBoard.blocks[0][1]!= 0) {
      exch (twinBoard.blocks, 0,0,0,1);
    }
    else {
      exch (twinBoard.blocks, 1,0,1,1);
    }
    return twinBoard;
  }
  
  /**
   * does this board equal y?.
   */
  public boolean equals(Object y) {
    if (y == this) return true;
    if (y == null) return false;
    if (y.getClass() != this.getClass()) return false;
    Board that = (Board) y;
    if (this.gridLength != that.gridLength) {
      return false;
    } else
    for (int i = 0; i < gridLength; i++) {
      for (int j = 0; j < gridLength;j++) {
        if (this.blocks[i][j] != that.blocks[i][j]) {
          return false;
        }
      }
    }
    
    
    return true;
  }
  
  /**
   * all neighboring boards.
   */
  public Iterable<Board> neighbors() {
    int row = 0;
    int col = 0;
    Board neighborBoard; 
    Stack<Board> neighbor = new Stack<Board>();
    
    //find the position of the blank square
    for (int i = 0; i < gridLength; i++) {
      boolean flag = false;
      for (int j = 0; j < gridLength;j++) {
        if (this.blocks[i][j] == 0) {
          flag = true;
          row = i;
          col = j;
          break;
        }
      }
      if (flag == true) {
        break;
      }
    }
    
    if (row > 0) {
      neighborBoard = new Board(blocks);
      exch(neighborBoard.blocks, row,col,row-1,col);
      neighbor.push(neighborBoard);
    }
    if (row < gridLength - 1) {
      neighborBoard = new Board(blocks);
      exch(neighborBoard.blocks, row,col,row+1,col);
      neighbor.push(neighborBoard);
    }
    if (col > 0) {
      neighborBoard = new Board(blocks);
      exch(neighborBoard.blocks, row,col-1,row,col);
      neighbor.push(neighborBoard);
    }
    if (col < gridLength - 1) {
      neighborBoard = new Board(blocks);
      exch(neighborBoard.blocks, row,col+1,row,col);
      neighbor.push(neighborBoard);
    }
    return neighbor;
  }
  
  /**
   * string representation of this board (in the output format specified below).
   */
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append(gridLength + "\n");
    for (int i = 0; i < gridLength; i++) {
        for (int j = 0; j < gridLength; j++) {
            s.append(String.format("%2d ", blocks[i][j]));
        }
        s.append("\n");
    }
    return s.toString();
  }
  
  private void exch(int a[][], int i, int j, int m, int n) {
    int temp = a[i][j];
    a[i][j] = a[m][n];
    a[m][n] = temp;
  }
  

  
  public static void main(String[] args) {
    /*
    In in = new In(args[0]);
    int N = in.readInt();
    int[][] blocks = new int[N][N];
    for (int i = 0; i < N; i++)
        for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    StdOut.print(initial.toString());
    StdOut.print(initial.twin().toString());
    StdOut.println(initial.hamming());
    StdOut.println(initial.manhattan());
    StdOut.println(initial.dimension());
    StdOut.println(initial.isGoal());
    
    for (Board b : initial.neighbors()) {
        StdOut.println(b.toString());
        //for (Board d : b.neighbors()) {
           // StdOut.println("===========");
           // StdOut.println(d.toString());
           // StdOut.println("===========");
       // }
        
    }
    StdOut.print(initial.toString());
    */
  }
}
