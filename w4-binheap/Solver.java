import java.lang.Comparable;
import edu.princeton.cs.algs4.MinPQ;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.Integer;

public class Solver {
  private final List<Board> solution;
  private final int moves;

  public Solver(Board initial) {

    if (initial == null) {
      throw new java.lang.IllegalArgumentException();
    }

    BoardsPool boardsPool = new BoardsPool();
    MinPQ<Board2> minPQ = new MinPQ<Board2>();
    Board2 root = new Board2(boardsPool.create(initial, 0), 0, null);    
    Board2 twin = new Board2(boardsPool.create(initial.twin(), 0), 0, null);    
    minPQ.insert(root);
    minPQ.insert(twin);
    Board2 current = minPQ.delMin();
    
    current = minPQ.delMin();
    while (current != null && current.node().manhattan() > 0) {
      current.node().setProcessed();
      current.createNeighbors(boardsPool, minPQ);
     
      current = null;
      while (minPQ.size() > 0 && current==null) {
        current = minPQ.delMin();
        if(current.node().processed()) {
          current = null;
        }
      }
      
    }
    if (current != null) {
      int m = current.moves();
      Board[] res = new Board[m+1];
      for (int i = current.moves(); i >= 0; i--) {
        res[i] = current.node().board();
        current = current.parent();
      }
      if (!res[0].equals(root)) {
        moves = -1;
        solution = null;
      }
      else {
        moves = m;
        solution = Arrays.asList(res);
      }
    } 
    else {
      moves = -1;
      solution = null;
    }
  }

  public boolean isSolvable() {
    return (solution != null);
  }

  public int moves() {
    return moves;
  }

  public Iterable<Board> solution() {
    return solution;
    /*
    if(solution == null) {
      return null;
    }
    Board[] res = new Board[moves+1];
    Board2 b = solution;
    for (int i = moves; i >= 0; i--) {
      res[i] = b.node().board();
      b = b.parent();
    }
    return Arrays.asList(res);
    */
  }


  private class Board2 implements Comparable<Board2> {

    private final int nMoves;
    private final BoardNode node;
    private final Board2 parent;
    private final int priority;
    

    public Board2(BoardNode node, int nMoves, Board2 parent) {
      this.node = node;
      this.nMoves = nMoves;
      this.parent = parent;
      this.priority = nMoves + node.manhattan();
    }

    public void createNeighbors(BoardsPool boardsPool, MinPQ<Board2> minPq) {
      for (Iterator<Board> bIter = node.board().neighbors().iterator(); bIter.hasNext();) {
        Board b0 = bIter.next();
        BoardNode nd = boardsPool.find(b0);
        if (nd == null) {
          nd = boardsPool.create(b0, nMoves+1);
          Board2 selector = new Board2(nd, nMoves+1, this);
          minPq.insert(selector);
        }
        else if (nd.minMoves > (nMoves+1) && !nd.processed()) {
          Board2 selector = new Board2(nd, nMoves+1, this);
          minPq.insert(selector);
          nd.setMinMoves(nMoves+1);          
        }
      }
    }

    public Board2 parent() {
      return parent;
    }

    public int priority() {
      return priority;
    }

    public int compareTo(Board2 b) {
      if (priority() < b.priority()) {
        return -1;
      }
      else if (priority() > b.priority()) {
        return 1;
      }
      return 0;
    }

    public BoardNode node() {
      return node;
    }

    public int moves() {
      return nMoves;
    }
  }

  private class BoardNode {
    private final Board board;
    private final int manhattan;
    private boolean processed = false;
    private BoardNode next = null;
    private int minMoves;
    public BoardNode(Board b, int moves) {
      this.board = b;
      this.manhattan = b.manhattan();
      this.minMoves = moves;
    }

    public int minMoves() {
      return minMoves;
    }

    public void setMinMoves(int moves) {
      this.minMoves = moves;
    }

    public Board board() {
      return board;
    }

    public int manhattan() {
      return manhattan;
    }

    
    public boolean processed() {
      return processed;
    }

    public void setProcessed() {
      processed = true;
    }
    

    public BoardNode next() {
      return next;
    }

    public void setNext(BoardNode node) {
      next = node;
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
      BoardNode t = (BoardNode)y;
      return (manhattan() == t.manhattan() && 
              board.equals(t.board));
     }
  }


  private class BoardsPool {

    private BoardNode first = null;


    public BoardNode create(Board b, int moves) {
      BoardNode  res = new BoardNode(b, moves);
      if (first == null) {
        first = res;
        return res;
      }
      int m = res.manhattan();
      if (m < first.manhattan()) {
        res.setNext(first);
        first = res;
        return res;
      }
      BoardNode current = first;
      while (current != null) {
        if (current.next() != null && current.next().manhattan() > m) {
          res.setNext(current.next());
          current.setNext(res);
          return res;
        }
        else if (current.next() == null)
        {
          current.setNext(res);
          return res;
        }
        current = current.next();
      }
      return null;
    }

    public BoardNode find(Board b) {
      BoardNode current = first;
      int m = b.manhattan();
      while (current != null) {
        if(current.manhattan() > m) {
          return null;
        }
        if(current.manhattan() == m && current.board().equals(b)) {
          return current;
        }        
        current = current.next();
      }
      return null;
    }    
  }  
}
