import java.util.ArrayList;
//import java.util.HashMap;
import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

  private final ArrayList<LineSegment> segments = new ArrayList<LineSegment>();

  // finds all line segments containing 4 points
  public BruteCollinearPoints(Point[] points) {
    if( points == null ) {
      throw new java.lang.IllegalArgumentException();
    }
    /*
    HashMap<String, Point> map = new HashMap<String, Point>();
    for( int i = 0; i < points.length; i++ ) {
      if( points[i] == null || map.	containsKey(points[i].toString()) ) {
        throw new java.lang.IllegalArgumentException();
      }
      map.put( points[i].toString(), points[i] );
    }
    */
    for( int i = 0; i < points.length; i++ ) {
      for( int j = 0; j < points.length; j++ ) {
        if( j==i ) {
          continue;
        }
        double s0 = points[i].slopeTo(points[j]);
        for( int k = 0; k < points.length; k++) {
          if( k==i || k==j ) {
            continue;
          }
          double s1 = points[i].slopeTo(points[k]);
          if( Math.abs(s0-s1) > Double.MIN_NORMAL && !( s0 == Double.POSITIVE_INFINITY && s1 == Double.POSITIVE_INFINITY) ) {
            continue;
          }
          for( int g = 0; g < points.length; g++ ) {
            if( g==i || g==j || g==k ) {
              continue;
            }
            double s2 = points[i].slopeTo(points[g]);
            if( Math.abs(s0-s2) < Double.MIN_NORMAL || ( s0 == Double.POSITIVE_INFINITY && s2 == Double.POSITIVE_INFINITY) ) {
              Point[] p4 = new Point[4];
              p4[0] = points[i];
              p4[1] = points[j];
              p4[2] = points[g];
              p4[3] = points[k];
              int iMin = 0;
              for( int h = 1; h < 4; h++ ) {
                if( p4[iMin].compareTo(p4[h]) < 0 ) {
                  iMin = h;
                }
              }
              int iMax = 0;
              for( int h = 1; h < 4; h++ ) {
                if( p4[iMax].compareTo(p4[h]) > 0 ) {
                  iMax = h;
                }
              }
              LineSegment ls =  new LineSegment( p4[iMin], p4[iMax] );
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

  public static void main(String[] args) {
    
    // read the n points from a file
    //StdIn in = new StdIn(args[0]);
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
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    StdOut.println(String.format("Segments %d", collinear.numberOfSegments()));
    for (LineSegment segment : collinear.segments()) {
      StdOut.println(segment);
      segment.draw();
    }
    StdDraw.show();
  }
}