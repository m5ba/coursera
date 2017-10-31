import java.util.ArrayList;

public class Board {
  private final int[][] data;
  private final int n;
  private final int nManhattan;
  private final int nHamming;
  private final int zeroR;
  private final int zeroC;
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
    data = new int[n][n];
    int zR = -1;
    int zC = -1;
    int nH = 0;
    int nM = 0;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        data[i][j] = blocks[i][j];
        int er = (data[i][j]-1) /n;
        int ec = (data[i][j]-1) % n;
        if (data[i][j] == 0) {
          zR = i;
          zC = j;
        }
        else {
          nM+=Math.abs(er-i)+Math.abs(ec-j);
          if (i!=er || j!=ec) {
            nH++;
          }
            
        }
      }
    }
    nManhattan = nM;
    nHamming = nH;
    zeroR = zR;
    zeroC = zC;
    hash = calcHash();
  }

  private int calcHash() {
    int hash = java.util.Arrays.deepHashCode( data );
    return hash;
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
    int idx0 = -1;
    int idx = 0;
    while (idx0 == -1) {
      if (data[getRow(idx)][getCol(idx)]>0) {
        idx0 = idx;
      }
      idx++;
    }
    int idx1 = -1;
    while (idx1 == -1) {
      if (data[getRow(idx)][getCol(idx)]>0) {
        idx1 = idx;
      }
      idx++;
    }
    return createBoard(getRow(idx0), getCol(idx0), getRow(idx1), getCol(idx1));
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
    Board t = (Board)y;
    if (hash!=t.hash || t.dimension() != n) {
      return false;
    }
    for (int i=0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (data[i][j] != t.data[i][j]) {
          return false;
        }
      }
    }
    return true;
  }

  private Board createBoard(int r0, int c0, int r1, int c1) {
    int[][] d = new int[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        d[i][j] = data[i][j];
      }
    }
    d[r0][c0] = data[r1][c1];
    d[r1][c1] = data[r0][c0];
    return new Board(d);
  }

  public Iterable<Board> neighbors() {
    ArrayList<Board> res = new ArrayList<Board>();
    if (zeroR > 0) {
      res.add(createBoard(zeroR, zeroC, zeroR-1, zeroC));
    }
    if (zeroR < (n-1)) {
      res.add(createBoard(zeroR, zeroC, zeroR+1, zeroC));
    }
    if (zeroC > 0) {
      res.add(createBoard(zeroR, zeroC, zeroR, zeroC-1));
    }
    if (zeroC < (n-1)) {
      res.add(createBoard(zeroR, zeroC, zeroR, zeroC+1));
    }
    return res;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(n);
    sb.append("\n");
    for (int i=0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        sb.append(String.format(" %d", data[i][j]));
      }
      sb.append("\n");
    }
    return sb.toString();
  }
}