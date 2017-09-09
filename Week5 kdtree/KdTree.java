/**
 * 
 */
import edu.princeton.cs.algs4.*;
/**
 * @author Mark
 *
 */
public class KdTree {
  
  private Node root;
  private int size;
  private RectHV rect;    // the axis-aligned rectangle corresponding to this node
  private Node lb;        // the left/bottom subtree
  private Node rt;        // the right/top subtree
  /**
   * construct an empty KdTree.
   */
  public KdTree() {
    root = null;
    size = 0;
  }
  
  /**
   * Is the KdTree empty?
   */
  public boolean isEmpty() {
    return root == null;
  }
  
  /**
   * Number of nodes in the KdTree.
   */
  public int size() {
    return size;
  }
  
  /**
   * Insert a new node into a KdTree. 
   */
  public void insert(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }
    if (root != null) {
      root = insertVer(root, p, root.rect);
    } else if (root == null) {
      root = insertVer(root, p, null);
    }
  }
  private Node insertVer(Node n, Point2D p, RectHV rect) {
    // if root = null, create new node
    if (n == null) {
      size++;
      return new Node(p, rect);
    }
    int cmp = Point2D.X_ORDER.compare(n.p, p);
    if (n.p.equals(p)) {
      return n;
    }
    if (cmp > 0) {
      n.lb = insertHori(n.lb, p, new RectHV(rect.xmin(),rect.ymin(),n.p.x(),rect.ymax()));
    } else {
      n.rt = insertHori(n.rt, p, new RectHV(n.p.x(),rect.ymin(),rect.xmax(),rect.ymax()));
    }
    return n;
  }
  private Node insertHori(Node n, Point2D p, RectHV rect) {
    if (n == null) {
      size++;
      return new Node(p, rect);
    }
    int cmp = Point2D.Y_ORDER.compare(n.p, p);
    if (n.p.equals(p)) {
      return n;
    }
    if (cmp > 0) {
      n.lb = insertVer(n.lb, p, new RectHV(rect.xmin(),rect.ymin(),rect.xmax(),n.p.y()));
    } else {
      n.rt = insertVer(n.rt, p, new RectHV(rect.xmin(),n.p.y(),rect.xmax(),rect.ymax()));
    }
    return n;
  }
  
  /**
   * does the set contain point p? 
   */
  public boolean contains(Point2D p) {
    return contains(root, p, true);
  }
  private boolean contains(Node n, Point2D p, boolean isVer) {
    if (n == null) {return false;}
    if (n.p.equals(p)) {return true;}
    int cmp = 0;
    if (isVer) {cmp = Point2D.X_ORDER.compare(n.p, p);}
    else       {cmp = Point2D.Y_ORDER.compare(n.p, p);}
    if (cmp > 0) {return contains(n.lb, p, !isVer);}
    else {return contains(n.rt, p, !isVer);}
  }
  
  /**
   * all points that are inside the rectangle.
   */
  public Iterable<Point2D> range(RectHV rect) {
    Stack<Point2D> s = new Stack<Point2D>();
    range(root, rect, s);
    return s;
  }
  
  private void range(Node n, RectHV rect, Stack<Point2D> s) {
    if (rect.contains(n.p)) {
      s.push(n.p);
    }
    if (n.lb != null && n.lb.rect.intersects(rect)) {
      range(n.lb, rect, s);
    }
    if (n.rt != null && n.rt.rect.intersects(rect)) {
      range(n.rt, rect, s);
    }
  }
  
  //a nearest neighbor in the set to p: null if set is empty
  public Point2D nearest(Point2D p) {
      if (isEmpty()) return null;
      return nearest(root, p, root.p, true);
  }
  
  private Point2D nearest(Node x, Point2D p, Point2D mp, boolean vert) {
    Point2D min = mp;

    if (x == null)    return min;
    if (p.distanceSquaredTo(x.p) < p.distanceSquaredTo(min))
        min = x.p;

    // choose the side that contains the query point first
    if (vert) {
        if (x.p.x() < p.x()) {
            min = nearest(x.rt, p, min, !vert);
            if (x.lb != null
                    && (min.distanceSquaredTo(p)
                        > x.lb.rect.distanceSquaredTo(p)))
                min = nearest(x.lb, p, min, !vert);
        } else {
            min = nearest(x.lb, p, min, !vert);
            if (x.rt != null
                    && (min.distanceSquaredTo(p)
                     > x.rt.rect.distanceSquaredTo(p)))
                min = nearest(x.rt, p, min, !vert);
        }
    } else {
        if (x.p.y() < p.y()) {
            min = nearest(x.rt, p, min, !vert);
            if (x.lb != null
                    && (min.distanceSquaredTo(p)
                        > x.lb.rect.distanceSquaredTo(p)))
                min = nearest(x.lb, p, min, !vert);
        } else {
            min = nearest(x.lb, p, min, !vert);
            if (x.rt != null
                    && (min.distanceSquaredTo(p)
                        > x.rt.rect.distanceSquaredTo(p)))
                min = nearest(x.rt, p, min, !vert);
        }
    }
    return min;
}

/*
  private Point2D nearest(Node n, Point2D p, Point2D mp) {
    Point2D goal = mp;

    if (n == null)    return goal;
    if (p.distanceTo(n.p) < p.distanceTo(goal))
      goal = n.p;

    // choose the side that contains the query point first
    //always choose the subtree that is on the same side 
    //of the splitting line as the query point as the first subtree to explore
    if (n.rt != null && n.rt.rect.contains(p)) {
      goal = nearest(n.rt, p, goal);
      // a node is searched only if it might contain a point 
      //that is closer than the best one found so far.
        if (n.lb != null
                && (goal.distanceTo(p)
                    > n.lb.rect.distanceTo(p)))
          goal = nearest(n.lb, p, goal);
    } else {
      goal = nearest(n.lb, p, goal);
        if (n.rt != null
                && (goal.distanceTo(p)
                 > n.rt.rect.distanceTo(p)))
          goal = nearest(n.rt, p, goal);
    }
    
    return goal;
}
 */ 
  private void draw(Node n,boolean isVertical) {
    
    if (n.lb != null) draw(n.lb, !isVertical);
    if (n.rt != null) draw(n.rt, !isVertical);
    
    if (isVertical) {
      StdDraw.setPenColor(StdDraw.RED);
      StdDraw.setPenRadius(.005);
      StdDraw.line(n.p.x(), n.rect.ymin(), n.p.x(), n.rect.ymax()); 
    } else {
      StdDraw.setPenColor(StdDraw.BLUE);
      StdDraw.setPenRadius(.005);
      StdDraw.line(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.p.y());
    }
    StdDraw.setPenColor(StdDraw.BLACK);
    StdDraw.setPenRadius(.015);
    StdDraw.point(n.p.x(),n.p.y());  
  }
  
  public void draw() {
    StdDraw.setPenRadius(.005);
    StdDraw.rectangle(0.5, 0.5, 0.5,0.5);
    draw(this.root, true);
  }

  
  private static class Node {
    private Point2D p;      // the point
    private RectHV rect;    // the axis-aligned rectangle corresponding to this node
    private Node lb;        // the left/bottom subtree
    private Node rt;        // the right/top subtree
    public Node(Point2D p, RectHV rect) {
      RectHV r = rect;
      if (r == null) {
        r = new RectHV(0, 0, 1, 1);
      }
      this.rect   = r;
      this.p      = p;
    }
 }
  public static void main(String[] args) {
    /*In in = new In(args[0]);      // input file
    KdTree kdtree = new KdTree();
    while (!in.isEmpty()) {
      double i = in.readDouble();
      double j = in.readDouble();
      kdtree.insert(new Point2D(i,j));
      //kdtree.draw();
    }
    Point2D query = new Point2D(0.6, 0.6);
    StdDraw.setPenRadius(.035);
    kdtree.nearest(query).draw();
    */
    /*RectHV rect = new RectHV(0.50,0.05,0.95,0.95);
    StdDraw.setPenRadius(.015);
    for (Point2D p : kdtree.range(rect))
      p.draw();
      */
    /*
    KdTree kdtree = new KdTree();
    kdtree.insert(new Point2D(0.7,0.2));
    kdtree.draw();
    kdtree.insert(new Point2D(0.5,0.4));
    kdtree.draw();
    kdtree.insert(new Point2D(0.2,0.3));
    kdtree.draw();
    kdtree.insert(new Point2D(0.4,0.7));
    kdtree.draw();
    kdtree.insert(new Point2D(0.9,0.6));
    kdtree.draw();
    Point2D query = new Point2D(0.1, 0.1);
    StdDraw.setPenRadius(.035);
    kdtree.nearest(query).draw();
    */
  }
}
