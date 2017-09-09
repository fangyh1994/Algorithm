/**
 * 
 */
import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;
/**
 * @author Mark
 *
 */
public class FastCollinearPoints {
  
  private LineSegment[] segment;
  
  /**
   * finds all line segments containing 4 or more points.
   */
  public FastCollinearPoints(Point[] points) {
    
    // check if the argument is null or if the argument contains a  repeated point.
    check(points);
    
    Point start;
    Point end;
    Point[] order; 
    ArrayList<LineSegment> line = new ArrayList<>();
    Point[] copy = Arrays.copyOfRange(points, 0, points.length);
    for (int i = 0;i < points.length; i++) {
      Arrays.sort(copy, points[i].slopeOrder());
      double previousSlope = points[i].slopeTo(copy[0]); // previous slope
      int collinearcount = 2;//collinear points number
      for (int j = 1; j < copy.length; j++) {
        if (points[i].slopeTo(copy[j]) == previousSlope) {
          collinearcount++;
        }
        if (points[i].slopeTo(copy[j]) != previousSlope || (j == copy.length - 1) ) {
          if (collinearcount >= 4) { // 4 or more points are collinear.
            //start = copy[j-collinearcount+1];
            //end = copy[j-1];
            order = new Point[collinearcount];
            order = Arrays.copyOfRange(copy, j + 1 - collinearcount, j + 1);//copyofRange[)
            if (j == copy.length - 1) {
              order[0] = points[i];
            } else {
              order[collinearcount - 1] = points[i];  //collinear points are (points[i],copy[j-collinearcount+1]....copy[j-1])
            }
            Arrays.sort(order);
            //order[0].drawTo(order[collinearcount - 1]);
            /********* testing ********/
            
            /******* ******/
            LineSegment repeat = new LineSegment(order[0], order[collinearcount - 1]);
            boolean ifrepeat = false;
           // if (!line.contains(repeat) ) 
              /*for (int s = 0; s < line.size();s++) {
                if () {
                  ifrepeat = true;
                }
              }*/
              if (ifrepeat == false) {
                line.add(new LineSegment(order[0],order[collinearcount - 1]));
              }
             
              /*for (int k = 0; k < order.length; k++) {
                StdOut.print(order[k] + " -> ");
              }
              StdOut.println("");
              */
            
          }
          collinearcount = 2;
          previousSlope = copy[j].slopeTo(points[i]);
        }
      }
      copy = Arrays.copyOfRange(points, i+1, points.length);
      //StdOut.println();
    }
    segment = new LineSegment[line.size()];
    line.toArray(segment);
  }
  
  /**
   * the number of line segments.
   */
  public int numberOfSegments() {
    return segment.length;
  }
  
  /**
   * the line segments.
   */
  public LineSegment[] segments() {
    return segment;
  }
  private void check(Point[] points) {
    for (int i = 0;i < points.length; i++) {
      if (points[i] == null) {
        throw new java.lang.NullPointerException();
      }
    }
    for (int i = 0; i < points.length; i++) {
      for (int j = i + 1; j < points.length; j++) {
        if (points[i].compareTo(points[j]) == 0) {
          throw new java.lang.IllegalArgumentException();
        }
      }
    }
  }
}
