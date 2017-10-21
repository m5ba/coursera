import java.util.Comparator;
import java.lang.Comparable;

public class Point implements Comparable<Point> {
  private final int x;
  private int y;
  public Point(int x, int y) {
    this.x=x;
    this.y=y;
  }

  public int getX() {
    return x;    
  }

  public int getY() {
    return y;
  }

  public void draw() {

  }

  public void drawTo(Point that) {

  }

  public String toString() {
    return "";
  }

  public int compareTo(Point that) {
    if( y == that.getY() ) {
      return x - that.getX();
    }
    return y - that.getY();
  }

  public double slopeTo(Point that) {
    if( x == that.getX() && y == that.getY() ) {
      return Double.NEGATIVE_INFINITY;
    }
    else if ( x == that.getX() ) {
      return Double.POSITIVE_INFINITY;
    }
    return ( that.getY() - y) / ( that.getX() - x );   
  }

  public Comparator<Point> slopeOrder() {
    return null;
  }
}