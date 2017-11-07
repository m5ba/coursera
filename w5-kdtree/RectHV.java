import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class RectHV {
  private final double xmin;
  private final double ymin;
  private final double xmax;
  private final double ymax;
  public    RectHV(double xmin, double ymin, double xmax, double ymax) {
    if ((xmin > xmax) || (ymin > ymax)) {
      throw new java.lang.IllegalArgumentException();
    }
    this.xmin = xmin;
    this.xmax = xmax;
    this.ymin = ymin;
    this.ymax = ymax;
  }

  public  double xmin() {
    return xmin;
  }
  public  double ymin() {
    return ymin;
  }
  public  double xmax() {
    return xmax;
  }
  public  double ymax() {
    return ymax;
  }
  public boolean contains(Point2D p) {
    return (p.x() >= xmin && p.x() <= xmax && p.y() >= ymin && p.y() <= ymax);
  }
  public boolean intersects(RectHV that) {
    return  (inside(that.xmin(), that.ymin()) || 
            inside(that.xmin(), that.ymax()) || 
            inside(that.xmax(), that.ymin()) ||
            inside(that.xmax(), that.ymax()));
          }

  private boolean inside(double x, double y) {
    return (x >= xmin && x <= xmax && y >= ymin && y <= ymax);
  }

  public  double distanceTo(Point2D p) {
    return closest(p).distanceTo(p);
  }

  private Point2D closest(Point2D p) {
    Point2D res;
    if (p.x() >= xmin && p.x() <=xmax) {
      if (p.y() > ymax) {
        return new Point2D(p.x(), ymax);
      }
      else if (p.y() < ymin) {
        return new Point2D(p.x(), ymin);        
      }
      return new Point2D(p.x(), p.y());
    }
    if (p.y() >= ymin && p.y() <=ymax) {
      if (p.x() > xmax) {
        return new Point2D(xmax, p.y());
      }
      else if (p.x() < xmin) {
        return new Point2D(xmin, p.y());
      }
      return new Point2D(p.x(), p.y());
    }
    if (p.x() > xmax && p.y() > ymax) {
      return new Point2D(xmax, ymax);
    }
    if (p.x() > xmax && p.y() < ymin) {
      return new Point2D(xmax, ymin);
    }
    if (p.x() < xmin && p.y() < ymin) {
      return new Point2D(xmin, ymin);
    }
    return new Point2D(xmin, ymax);
  }
  public  double distanceSquaredTo(Point2D p) {
    return closest(p).distanceSquaredTo(p);
  }     // square of Euclidean distance from point p to closest point in rectangle 

  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (that == null) {
      return false;
    }
    return false;    
  }              // does this rectangle equal that object? 

  public    void draw() {
    double w = (xmax-xmin) / 2;
    double h = (ymax - ymin) / 2;
    StdDraw.rectangle(w+xmin, h+ymin, w, h);

  }                           // draw to standard draw 

  public  String toString() {
    return String.format("(%x(f.2 - %f.2) y(%f.2 - %f.2))]", xmin, xmax, ymin, ymax);
  }
}