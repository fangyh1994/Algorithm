/**
 * 
 */
import edu.princeton.cs.algs4.*;
/*
 * @author Mark
 *
 */
public class PointSET {
   
  private SET<Point2D> set;
   /**
    * construct an empty set of points.
    */
   public PointSET() {
     set = new SET<Point2D>();
   }
   
   /**
    * is the set empty?.
    */
   public boolean isEmpty() {
     return set.isEmpty();
   }
   
   /**
    * number of points in the set.
    */
   public int size() {
     return set.size();
   }
   
   /**
    * add the point to the set (if it is not already in the set).
    */
   public void insert(Point2D p) {
     set.add(p);
   }
   
   /**
    * does the set contain point p? 
    */
   public boolean contains(Point2D p) {
     return set.contains(p);
   }
   
   /**
    * draw all points to standard draw.
    */
   public void draw() {
     for (Point2D p : set)
       StdDraw.point(p.x(), p.y());

   StdDraw.show();
   }
   
   /**
    * all points that are inside the rectangle.
    */
   public Iterable<Point2D> range(RectHV rect) {
     Stack<Point2D> s = new Stack<Point2D>();
     for (Point2D p: set) {
       if (rect.contains(p)) {
         s.push(p);
       }
     }
     return s;
   }
   
   /**
    * a nearest neighbor in the set to point p; null if the set is empty .
    */
   public Point2D nearest(Point2D p) {
     if (set.isEmpty()) {
       return null;
     }
     double distance;
     double min = p.distanceTo(set.max());
     Point2D goal = set.max();
     for (Point2D point: set) {
       distance = p.distanceTo(point);
       if (distance < min) {
         min = distance;
         goal = point;
       }
     }
     return goal;
   }
 
   /**
    * unit testing of the methods (optional).
    */
   public static void main(String[] args) {
     
   }
}
