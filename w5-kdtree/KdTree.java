import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {
  private final SET<Point2D> set = new SET<Point2D>();
  public KdTree() {
    
  }

  public boolean isEmpty() {
    return set.isEmpty();
  }

  public int size() {
    return set.size();
  }

  public void insert(Point2D p) {
    if (p == null) {
      throw new java.lang.IllegalArgumentException();
    }
    if (!set.contains(p)) {
      set.add(p);
    }
  }

  public boolean contains(Point2D p) {
    if (p == null) {
      throw new java.lang.IllegalArgumentException();
    }
    return set.contains(p);
  }

  public void draw() {
    for (Point2D p : set) {
      p.draw();
    }
  }
  
  public Iterable<Point2D> range(RectHV rect) {
    if (rect == null) {
      throw new java.lang.IllegalArgumentException();
    }
    SET<Point2D> res = new SET<Point2D>();
    for (Point2D p : set) {
      if (rect.contains(p)) {
        res.add(p);
      }
    }
    return res;
  }

  
  public Point2D nearest(Point2D p) {
    if ( p==null ) {
      throw new java.lang.IllegalArgumentException();
    }
    double min = Double.MAX_VALUE;
    Point2D res = null;
    for (Point2D p0 : set) {
      double d = p0.distanceTo(p);
      if (d < min) {
        min = d;
        res = p0;
      }
    }
    return res;
  }            
  /*
  public static void main(String[] args) {

  }*/
  
}