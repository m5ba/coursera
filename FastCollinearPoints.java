import java.util.ArrayList;
import java.util.Arrays;

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


}