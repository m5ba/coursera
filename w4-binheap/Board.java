import java.util.ArrayList;

public class Board {
  private final int[] data;
  private final int n;
  private final int nManhattan;
  private final int nHamming;
  private final int zeroIdx;
  private final int hash;
  public Board(int[][] blocks) {
    if (blocks == null) {
      throw new java.lang.IllegalArgumentException();
    }
    int r = blocks.length;
    if (r < 1) {
      throw new java.lang.IllegalArgumentException();
    }
    n = blocks[0].length;
    if (r != n) {
      throw new java.lang.IllegalArgumentException();
    }
    data = new int[n*n];
    int zIdx = -1;
    int nH = 0;
    int nM = 0;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        int idx = i*n+j;
        data[idx] = blocks[i][j];
        int er = (data[idx]-1) /n;
        int ec = (data[idx]-1) % n;
        if (data[idx] == 0) {
          zIdx = idx;
        }
        else {
          nM += Math.abs(er - i) + Math.abs(ec - j);
          if (i != er || j != ec) {
            nH++;
          }
            
        }
      }
    }
    nManhattan = nM;
    nHamming = nH;
    zeroIdx = zIdx;
    hash = java.util.Arrays.hashCode(data);
  }

  private int getRow(int idx) {
    return idx/n;
  }

  private int getCol(int idx) {
    return idx % n;
  }
                                         
  public int dimension() {
    return n;
  }

  public int hamming() {
    return nHamming;
  }

  public int manhattan() {
    return nManhattan;
  }

  public boolean isGoal() {
    return (nManhattan == 0);
  }

  public Board twin() {
    int r = (getRow(zeroIdx) > 0 ? 0 : 1);
    return createBoard(getRow(r), getCol(0), getRow(r), getCol(1));
  }
  
  @Override
  public boolean equals(Object y) {
    if (this == y) {
      return true;
    }
    if (y == null) {
      return false;
    }
    if (getClass() != y.getClass()) {
      return false;    
    }
    Board t = (Board) y;
    return  hash == t.hash && 
            hamming() == t.hamming() &&
            manhattan() == t.manhattan() &&
            dimension() == t.dimension() &&
            zeroIdx == t.zeroIdx;
  }

  private Board createBoard(int r0, int c0, int r1, int c1) {
    int[][] d = new int[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        d[i][j] = data[i * n + j];
      }
    }
    d[r0][c0] = data[r1 * n + c1];
    d[r1][c1] = data[r0 * n + c0];
    return new Board(d);
  }

  public Iterable<Board> neighbors() {
    ArrayList<Board> res = new ArrayList<Board>();
    int zeroR = getRow(zeroIdx);
    int zeroC = getCol(zeroIdx);
    if (zeroR > 0) {
      res.add(createBoard(zeroR, zeroC, zeroR - 1, zeroC));
    }
    if (zeroR < (n-1)) {
      res.add(createBoard(zeroR, zeroC, zeroR + 1, zeroC));
    }
    if (zeroC > 0) {
      res.add(createBoard(zeroR, zeroC, zeroR, zeroC - 1));
    }
    if (zeroC < (n-1)) {
      res.add(createBoard(zeroR, zeroC, zeroR, zeroC + 1));
    }
    return res;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(n);
    sb.append("\n");
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        sb.append(String.format(" %d", data[i * n + j]));
      }
      sb.append("\n");
    }
    return sb.toString();
  }
}