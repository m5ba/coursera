import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class BoardTest {
  public static void main(String[] args) {
    final int n = 3;
    int[][] data = new int[n][n];
    for (int i = 0;i<n;i++) {
      for(int j=0;j<n;j++){
        data[i][j] = n*i+j+1;
      }
    }
    data[2][2]=0;
    Board b = new Board(data);
    StdOut.println(b);
    StdOut.println(String.format("dim=%d; manhattan=%d; namming=%d;", b.dimension(), b.manhattan(), b.hamming()));
    b = b.twin();
    StdOut.println(b);
    StdOut.println(String.format("dim=%d; manhattan=%d; namming=%d;", b.dimension(), b.manhattan(), b.hamming()));
    for (Board bb : b.neighbors()) {
      StdOut.println(bb);
      StdOut.println(String.format("dim=%d; manhattan=%d; namming=%d;", b.dimension(), b.manhattan(), b.hamming()));
    }

    System.out.println(b.twin());
  }
}
