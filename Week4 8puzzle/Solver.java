import java.util.Arrays;
import java.util.Comparator;
import edu.princeton.cs.algs4.*;


/**
 * @author Mark
 *
 */
public class Solver {
  
  
  private Grid previous;
  private Grid twinprevious;
  
  private class Grid implements Comparable<Grid> {
    private Board board;
    private int moves;
    private Grid preGrid;
    //private int priority;
    
    public Grid(Board board) {
      this.board = board;
      moves = 0;
    }
    public int manhattan() {
      return board.manhattan();
    }
    public int hamming() {
      return board.hamming();
    }
    public int compareTo (Grid that) {
      if ( (this.manhattan() + this.moves) < (that.manhattan() + that.moves) ) {
        return -1;
      }
      if ( (this.manhattan() + this.moves) > (that.manhattan() + that.moves) ) {
        return 1;
      }
      return 0;
    }
  }
  
  /**
   * find a solution to the initial board (using the A* algorithm).
   */
  public Solver(Board initial) {
    if (initial == null) {
      throw new java.lang.NullPointerException();
    }
    MinPQ<Grid> pq;
    MinPQ<Grid> pqtwin;
    pq = new MinPQ<Grid>();
    pqtwin = new MinPQ<Grid>();
    Grid initialGrid = new Grid(initial);
    Grid initialGridtwin = new Grid(initial.twin());

    //initialGrid.priority = initialGrid.moves + initialGrid.board.manhattan();
    //pq.insert(initialGrid);
    previous = initialGrid; 
    //pqtwin.insert(initialGridtwin);
    twinprevious = initialGridtwin;
    
    while (!previous.board.isGoal() && !twinprevious.board.isGoal()) {
      for ( Board b : previous.board.neighbors()) {
        if (previous.preGrid != null && !b.equals(previous.preGrid.board)) {
          Grid temp = new Grid(b);
          temp.moves = previous.moves + 1;
          temp.preGrid = previous;
          //temp.priority = temp.moves + temp.board.manhattan();
          pq.insert(temp);
        }
        else if (previous.preGrid == null){
          Grid temp = new Grid(b);
          temp.moves = previous.moves + 1;
          temp.preGrid = previous;
          //temp.priority = temp.moves + temp.board.manhattan();
          pq.insert(temp);
        }
        
      }
      
      
      //twin board
      for ( Board a : twinprevious.board.neighbors()) {
        if (twinprevious.preGrid != null && !a.equals(twinprevious.preGrid.board)) {
          Grid temp = new Grid(a);
          temp.moves = twinprevious.moves + 1;
          temp.preGrid = twinprevious;
          //temp.priority = temp.moves + temp.board.manhattan();
          pqtwin.insert(temp);
        }
        else {
          Grid temp = new Grid(a);
          temp.moves = twinprevious.moves + 1;
          temp.preGrid = twinprevious;
          //temp.priority = temp.moves + temp.board.manhattan();
          pqtwin.insert(temp);
        }
      }
      
      previous = pq.delMin();
      twinprevious = pqtwin.delMin();
    }
  }
  
  /**
   * is the initial board solvable?
   */
  public boolean isSolvable() {
    if (previous.board.isGoal()) {
      return true;
    }
    else return false;
  }
  
  /**
   * min number of moves to solve initial board; -1 if unsolvable.
   */
  public int moves() {
    if (isSolvable()) {
      return previous.moves;
    }
    else return -1;
  }
  
  /**
   * sequence of boards in a shortest solution; null if unsolvable.
   */
  public Iterable<Board> solution() {
    if (isSolvable()) {
      Stack<Board> shortsolution = new Stack<Board>();
      Grid last = previous;
      shortsolution.push(last.board);
      while (last.preGrid != null) {
        last = last.preGrid;
        shortsolution.push(last.board);
      }
      return shortsolution;
    }
    else return null;
  }
  
  /**
   * solve a slider puzzle (given below).
   */
  public static void main(String[] args) {
    
    // create initial board from file
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
        StdOut.println("No solution possible");
    else {
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
            StdOut.println(board);
    }
    
  }
}