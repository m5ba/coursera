import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SolverTest {
  public static void main(String[] args) {

    final int n = 3;
    int[][] data = new int[n][n];
    for (int i = 0;i<n;i++) {
      for(int j=0;j<n;j++){
        data[i][j] = n*i+j;
      }
    }
    Board b = new Board(data);
    //b = b.twin();

    StdOut.println(b);
    long t0 = System.currentTimeMillis();
    Solver slvr = new Solver(b);
    long t1 = System.currentTimeMillis();
    StdOut.println(String.format("solution took: %d", t1-t0));
    StdOut.println(slvr.isSolvable());
    StdOut.println(String.format("moves: %d", slvr.moves()));

    int c = 0;
    for(Iterator<Board> bIter = slvr.solution().iterator(); bIter.hasNext();) {
      if(c>2)
        break;
      System.out.println("solution");
      Board b0 = bIter.next();
      StdOut.println(b0.manhattan());
      StdOut.println(b0);
      c++;
    } 
    
  }
}