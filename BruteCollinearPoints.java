import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

  private final ArrayList<LineSegment> segments = new ArrayList<LineSegment>();
  private final ArrayList<LineSegment2> segments2 = new ArrayList<LineSegment2>();
  
  // finds all line segments containing 4 points
  public BruteCollinearPoints(Point[] points) {
    if (points == null || points.length == 0) {
      throw new java.lang.IllegalArgumentException();
    }
    for (Point p: points) {
      if (p == null) {
        throw new java.lang.IllegalArgumentException();
      }
    }
    Point[] pts = new Point[points.length];
    System.arraycopy(points, 0, pts, 0, points.length);
    Arrays.sort(pts);
    for (int i = 0; i < pts.length; i++) {
      if (i > 0 && (pts[i].compareTo(pts[i-1]) == 0)) {
        throw new java.lang.IllegalArgumentException();
      }      
    }
    for (int i = 0; i < points.length; i++) {
      for (int j = 0; j < points.length; j++) {
        if (j == i) {
          continue;
        }
        if (points[j] == null) {
          throw new java.lang.IllegalArgumentException();
        }
        double s0 = points[i].slopeTo(points[j]);
        for (int k = 0; k < points.length; k++) {
          if (k == i || k == j) {
            continue;
          }
          double s1 = points[i].slopeTo(points[k]);
          if (Math.abs(s0 - s1) > Double.MIN_NORMAL && !(s0 == Double.POSITIVE_INFINITY && s1 == Double.POSITIVE_INFINITY)) {
            continue;
          }
          for (int g = 0; g < points.length; g++) {
            if (g == i || g == j || g == k) {
              continue;
            }
            double s2 = points[i].slopeTo(points[g]);
            if (Math.abs(s0 - s2) < Double.MIN_NORMAL || (s0 == Double.POSITIVE_INFINITY && s2 == Double.POSITIVE_INFINITY)) {
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
              LineSegment2 ls =  new LineSegment2(p4[iMin], p4[iMax]);
              segments2.add(ls);
            }
          }
        }
      }
    }
    LineSegment2[] arr = new LineSegment2[segments2.size()];
    arr = segments2.toArray(arr);
    Arrays.sort(arr);
    if (arr.length > 0) {
      segments.add(arr[0].getLineSegment());
    }
    for (int i = 1; i < arr.length; i++) {
      if (arr[i-1].compareTo(arr[i]) != 0) {
        segments.add(arr[i].getLineSegment());
      }
    }
    segments2.clear();
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
      if (cm != 0) {
        return cm;
      }
      return p1.compareTo(that.p1);
    }    
  }
  
}