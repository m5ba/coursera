public class Board {
  private final int[][] data;
  private final int n;
  private int nManhattan;
  private int nHamming;
  public Board(int[][] blocks) {
    n = 0;
    if (blocks == null) {
      throw new java.lang.IllegalArgumentException();
    }
    int r = blocks.length;
    if (r < 1) {
      throw new java.lang.IllegalArgumentException();
    }
    int n = blocks[0].length;
    if (r != n) {
      throw new java.lang.IllegalArgumentException();
    }
    data = blocks;
    nHamming = 0;
    nManhattan = 0;
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
    return null;
  }

  public boolean equals(Object y) {
    return false;
  }

  public Iterable<Board> neighbors() {
    return null;
  }

  public String toString() {
    return "";
  }

}