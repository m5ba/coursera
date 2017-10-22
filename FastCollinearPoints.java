import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {

  private final ArrayList<LineSegment> segments = new ArrayList<LineSegment>();

  // finds all line segments containing 4 points
  public FastCollinearPoints(Point[] points) {
    if( points == null ) {
      throw new java.lang.IllegalArgumentException();
    }
    for( int i = 0; i < points.length; i++ ) {
      Point[] pts = new Point[points.length];
      System.arraycopy( points, 0, pts, 0, points.length );
      Arrays.sort( pts, points[i].slopeOrder() );
      double slLast = Double.NaN;
      ArrayList<Point> collinear = new ArrayList<Point>();
      for( int j = 1; j < points.length; j++ ) {
        double sl = points[i].slopeTo( pts[j] );
        if ( slLast!=Double.NaN && Math.abs( sl - slLast ) > Double.MIN_NORMAL ) {
          addSegment( collinear, points[i] );
          collinear.clear();
        }
        collinear.add( pts[j] );
        slLast = sl;
      }
      addSegment( collinear, points[i] );
    }
  }

  private void addSegment( ArrayList<Point> points, Point p0 ) {
    if( points.size()<3 ) {
      return;
    }
    points.add(p0);
    Point[] arr = new Point[points.size()];
    arr = points.toArray(arr);
    Arrays.sort( arr );
    LineSegment ls = new LineSegment( arr[ 0 ], arr[ arr.length - 1 ]);

    int n =0;
    for( LineSegment s : segments ) {
      if( s.toString().equals( ls.toString())) {
        n++;
        break;
      }
    }
    if( n == 0 ) {
      segments.add(ls);
    }
  }

  // the number of line segments
  public int numberOfSegments() {
    return segments.size();
  }

  // the line segments
  public LineSegment[] segments() {
    return segments.toArray(new LineSegment[segments.size()]);
  }

  public static void main(String[] args) {
    
    int n = StdIn.readInt();
    Point[] points = new Point[n];
    for (int i = 0; i < n; i++) {
      int x = StdIn.readInt();
      int y = StdIn.readInt();
      points[i] = new Point(x, y);
    }
    
    // draw the points
    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : points) {
      StdOut.println(p);
      p.draw();
    }
    StdDraw.show();

    // print and draw the line segments
    FastCollinearPoints collinear = new FastCollinearPoints(points);
    StdOut.println(String.format("Segments %d", collinear.numberOfSegments()));
    for (LineSegment segment : collinear.segments()) {
      StdOut.println(segment);
      segment.draw();
    }
    StdDraw.show();
    
  }
}