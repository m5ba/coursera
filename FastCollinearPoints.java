import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

  private final ArrayList<LineSegment> segments = new ArrayList<LineSegment>();
  private final ArrayList<LineSegment2> segments2 = new ArrayList<LineSegment2>();
  
  public FastCollinearPoints(Point[] points) {
    if (points == null || points.length == 0) {
      throw new java.lang.IllegalArgumentException();
    }
    for (Point p: points) {
      if (p == null) {
        throw new java.lang.IllegalArgumentException();
      }
    }
    Point[] pts = new Point[points.length];
    System.arraycopy( points, 0, pts, 0, points.length );
    Arrays.sort( pts );
    for (int i = 0; i < pts.length; i++) {
      if (i > 0 && (pts[i].compareTo(pts[i-1]) == 0)) {
        throw new java.lang.IllegalArgumentException();
      }      
    }
    for (int i = 0; i < points.length; i++) {
      Arrays.sort( pts, points[i].slopeOrder() );
      double slLast = Double.NaN;
      ArrayList<Point> collinear = new ArrayList<Point>();
      for (int j = 1; j < points.length; j++) {
        if (points[j] == null) {
          throw new java.lang.IllegalArgumentException();
        }
        double sl = points[i].slopeTo( pts[j] );
        if ( !Double.isNaN(slLast) && Math.abs( sl - slLast ) > Double.MIN_NORMAL ) {
          addSegment( collinear, points[i] );
          collinear.clear();
        }
        collinear.add( pts[j] );
        slLast = sl;
      }
      addSegment( collinear, points[i] );
    }
    LineSegment2[] arr = new LineSegment2[segments2.size()];
    arr = segments2.toArray(arr);
    Arrays.sort(arr);
    if (arr.length > 0 ) {
      segments.add(arr[0].getLineSegment());
    }
    for (int i = 1; i < arr.length; i++) {
      if (arr[i-1].compareTo(arr[i]) != 0) {
        segments.add(arr[i].getLineSegment());
      }
    }
    segments2.clear();
  }

  private void addSegment( ArrayList<Point> points, Point p0 ) {
    if (points.size() < 3) {
      return;
    }
    points.add(p0);
    Point[] arr = new Point[points.size()];
    arr = points.toArray(arr);
    Arrays.sort(arr);
    LineSegment2 ls = new LineSegment2(arr[ 0 ], arr[ arr.length - 1 ]);
    segments2.add(ls);
    /*
    LineSegment ls = new LineSegment(arr[ 0 ], arr[ arr.length - 1 ]);

    int n = 0;
    for (LineSegment s : segments) {
      if (s.toString().equals( ls.toString())) {
        n++;
        break;
      }
    }
    if (n == 0) {
      segments.add(ls);
    }
    */
  }

  // the number of line segments
  public int numberOfSegments() {
    return segments.size();
  }

  // the line segments
  public LineSegment[] segments() {
    return segments.toArray(new LineSegment[segments.size()]);
  }

  private class LineSegment2 implements Comparable<LineSegment2> {
    private final Point p0;
    private final Point p1;
    

    public LineSegment2(Point p0, Point p1) {
      this.p0 = p0;
      this.p1 = p1;
    }

    public LineSegment getLineSegment() {
      return new LineSegment(p0, p1);
    }

    public int compareTo(LineSegment2 that) {
      int cm = p0.compareTo(that.p0);
      if (cm != 0 ) {
        return cm;
      }
      return p1.compareTo(that.p1);
    }    
  }
}