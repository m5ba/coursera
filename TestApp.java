import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class TestApp {
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