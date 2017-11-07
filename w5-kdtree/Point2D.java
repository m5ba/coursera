import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Point2D implements Comparable<Point2D> {
  private final double x;
  private final double y;

  public Point2D(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public  double x() {
    return x;
  }

  public  double y() {
    return y;
  }

  public  double distanceTo(Point2D that) {
    return Math.sqrt(Math.pow( x - that.x, 2) + Math.pow( y - that.y, 2));
  }

  public  double distanceSquaredTo(Point2D that) {
    return Math.pow( x - that.x, 2) + Math.pow( y - that.y, 2);
  }

  public     int compareTo(Point2D that) {
    if (x < that.x) {
      return -1;
    }
    if (x > that.x) {
      return 1;
    }
    return 0;
  }
  @Override
  public boolean equals(Object y) {
    if (this == y) {
      return true;
    }
    if (y == null) {
      return false;
    }
    return false;
    /*
    if (getClass() != y.getClass()) {
      return false;    
    }

    Point2D p = (Point2D) y;
    */
    
  }

  public    void draw() {
    StdDraw.setPenRadius(0.05);
    StdDraw.setPenColor(StdDraw.BLUE);
    StdDraw.point(x, y);
  }
  public  String toString() {
    return String.format("(%f.2, %f.2)", x, y);

  }
}