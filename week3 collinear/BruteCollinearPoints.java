/**
 * 
 */
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
/**
 * @author Mark
 *
 */
public class BruteCollinearPoints {
  
  private LineSegment[] segment;
  
  /**
   * finds all line segments containing 4 points.
   */
  public BruteCollinearPoints(Point[] points) {
    
    // check if the argument is null or if the argument contains a  repeated point.
    check(points);
    
    ArrayList<LineSegment> line = new ArrayList<>();
    int length = points.length;
    for (int i = 0; i < length - 3; i++) {
      for (int k = i + 1; k < length - 2; k++) {
        for (int m = k + 1; m < length - 1; m++) {
          for (int n = m + 1; n < length; n++) {
            if (   (points[i].slopeTo(points[k]) == points[i].slopeTo(points[m]) )&&
                   (points[i].slopeTo(points[k]) == points[i].slopeTo(points[n]) ) ) {
              Point[] order = getOrder (points[i],points[k],points[m],points[n]);
              order[0].drawTo(order[3]);
              line.add(new LineSegment(order[0],order[3]));
              }
          }
        }
      }
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
  
  private Point[] getOrder (Point p1, Point p2, Point p3, Point p4) {
    Point[] order = new Point[4];
    order[0] = p1;
    order[1] = p2;
    order[2] = p3;
    order[3] = p4;
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 3; j++) {
        if (order[j].compareTo(order[j+1]) > 0) {
          Point index = order[j];
          order[j] = order[j+1];
          order[j+1] = index;
        }
      }
    }
    return order;
  }
  
  //check if the argument to the constructor is null or if any point in the array is null.
  //or if the argument to the constructor contains a repeated point.
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
