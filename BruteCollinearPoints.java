import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

  private final ArrayList<LineSegment> segments = new ArrayList<LineSegment>();

  // finds all line segments containing 4 points
  public BruteCollinearPoints(Point[] points) {
    if( points == null || points.length==0 ) {
      throw new java.lang.IllegalArgumentException();
    }
    for (Point p: points) {
      if (p==null) {
        throw new java.lang.IllegalArgumentException();
      }
    }
    Point[] pts = new Point[points.length];
    System.arraycopy( points, 0, pts, 0, points.length );
    Arrays.sort( pts );
    for( int i = 0; i < pts.length; i++ ) {
      if (i > 0 && (pts[i].compareTo(pts[i-1])==0)) {
        throw new java.lang.IllegalArgumentException();
      }      
    }
    for (int i = 0; i < points.length; i++) {
      for (int j = 0; j < points.length; j++) {
        if (j==i) {
          continue;
        }
        if (points[j] == null) {
          throw new java.lang.IllegalArgumentException();
        }
        double s0 = points[i].slopeTo(points[j]);
        for (int k = 0; k < points.length; k++) {
          if (k==i || k==j) {
            continue;
          }
          double s1 = points[i].slopeTo(points[k]);
          if (Math.abs(s0-s1) > Double.MIN_NORMAL && !( s0 == Double.POSITIVE_INFINITY && s1 == Double.POSITIVE_INFINITY)) {
            continue;
          }
          for (int g = 0; g < points.length; g++) {
            if (g==i || g==j || g==k) {
              continue;
            }
            double s2 = points[i].slopeTo(points[g]);
            if (Math.abs(s0-s2) < Double.MIN_NORMAL || ( s0 == Double.POSITIVE_INFINITY && s2 == Double.POSITIVE_INFINITY)) {
              Point[] p4 = new Point[4];
              p4[0] = points[i];
              p4[1] = points[j];
              p4[2] = points[g];
              p4[3] = points[k];
              int iMin = 0;
              for (int h = 1; h < 4; h++) {
                if (p4[iMin].compareTo(p4[h]) < 0) {
                  iMin = h;
                }
              }
              int iMax = 0;
              for (int h = 1; h < 4; h++) {
                if (p4[iMax].compareTo(p4[h]) > 0) {
                  iMax = h;
                }
              }
              LineSegment ls =  new LineSegment( p4[iMin], p4[iMax] );
              int n =0;
              for (LineSegment s : segments) {
                if (s.toString().equals( ls.toString())) {
                  n++;
                  break;
                }
              }
              if (n == 0) {
                segments.add(ls);
              }
            }
          }
        }
      }
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