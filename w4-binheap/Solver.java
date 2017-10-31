import java.lang.Comparable;
import edu.princeton.cs.algs4.MinPQ;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Iterator;

public class Solver {
  private final Board2 solution;
  private final int moves;

  public Solver(Board initial) {

    if (initial == null) {
      throw new java.lang.IllegalArgumentException();
    }

    BoardsPool boardsPool = new BoardsPool();
    Board2 current = boardsPool.create(initial, 0, null);    
    MinPQ<Board2> minPQ = new MinPQ<Board2>();

    BoardsPool boardsPoolTwined = new BoardsPool();
    Board2 currentTwined = boardsPool.create(initial.twin(), 0, null);    
    MinPQ<Board2> minPQTwined = new MinPQ<Board2>();
    
    while (current != null && current.manhattan() > 0) {
      Process1Step(current, boardsPool, minPQ);
      Process1Step(currentTwined, boardsPoolTwined, minPQTwined);
      if (currentTwined.manhattan() == 0) {
        moves = -1;
        solution = null;
        return; 
      }

      if (minPQ.size() > 0) {
        current = minPQ.delMin();
      }
      else {
        current = null;
      }
      if (minPQTwined.size() > 0) {
        currentTwined = minPQTwined.delMin();
      }
      else {
        currentTwined = null;
      }
    }
    if (current != null) {
      moves = current.moves();
      solution = current;
    } 
    else {
       moves = -1;
       solution = null;
    }
  }

  private void Process1Step(Board2 current, BoardsPool boardsPool, MinPQ<Board2> minPQ) {
    if (current == null) {
      return;
    }
    current.createNeighbors(boardsPool);
    for (Iterator<Board2> bIter = current.neighbors(); bIter.hasNext();) {
      Board2 b2 = bIter.next();
      boolean found = false;
      for (Iterator<Board2> bIter1 = b2.neighbors(); bIter1.hasNext();) {
        Board2 b21 = bIter1.next();          
        for (Iterator<Board2> bIter2 = b21.neighbors(); bIter2.hasNext();) {
          Board2 b22 = bIter2.next();
          if(b22.processed() && b22.equals(b2) && b22.moves()<=b2.moves()) {
            found = true;
            break;
          }
        }
          
      }
      if( !found ) {
        b2.setProcessed();
        minPQ.insert(b2);
      }
    }
  }

  public boolean isSolvable() {
    return (solution != null);
  }

  public int moves() {
    return moves;
  }

  public Iterable<Board> solution() {
    if(solution == null) {
      return null;
    }
    Board[] res = new Board[moves+1];
    Board2 b = solution;
    for (int i = moves; i >= 0; i--) {
      res[i] = b.board();
      b = b.parent();
    }
    return Arrays.asList(res);
  }


  private class Board2 implements Comparable<Board2> {

    private final int nMoves;
    private final Board board;
    private final Board2 parent;
    private final int priority;
    private Board2 linked = null;
    private final int manhattan;
    private final int hamming;
    private boolean processed;
    private final ArrayList<Board2> neighbors  = new  ArrayList<Board2>();;
    

    public Board2(Board board, int nMoves, Board2 parent) {
      this.board = board;
      this.nMoves = nMoves;
      this.parent = parent;
      this.manhattan = board().manhattan();
      this.hamming = board().hamming();
      this.priority = nMoves + this.manhattan;
      this.processed = false;
    }

    public void createNeighbors(BoardsPool boardsPool) {
      for (Iterator<Board> bIter = board.neighbors().iterator(); bIter.hasNext();) {
        Board b0 = bIter.next();
        Board2 b2 = boardsPool.find(b0);
        if (b2 == null) {
          b2 = boardsPool.create(b0, nMoves+1, this);
          neighbors.add(b2);
        }
        else if (b2.moves() > (nMoves+1)) {
          b2 = boardsPool.create(b0, nMoves+1, this);
          neighbors.add(b2);
        }
        //neighbors.add(b2);
      }
    }

    public Iterator<Board2> neighbors() {
      return neighbors.iterator();
    }

    public boolean equals(Board2 b) {
      return (hamming() == b.hamming() && manhattan() == b.manhattan() && board.equals(b.board()));
    }

    public boolean processed() {
      return processed;
    }

    public void setProcessed() {
      processed = true;
    }

    public Board2 parent() {
      return parent;
    }

    public Board2 linked() {
      return linked;
    }

    public void setLinked(Board2 b) {
      linked = b;
    }

    public int priority() {
      return priority;
    }


    public int manhattan() {
      return manhattan;
    }

    
    public int hamming() {
      return hamming;
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

    public Board board() {
      return board;
    }

    public int moves() {
      return nMoves;
    }
  }


  private class BoardsPool {

    private Board2 first = null;

    public Board2 create(Board b, int moves, Board2 parent) {
      Board2  res = new Board2(b, moves, parent );
      if (first == null) {
        first = res;
        return res;
      }
      int m = b.manhattan();
      if (m < first.manhattan()) {
        res.setLinked(first);
        first = res;
        return res;
      }
      Board2 current = first;
      while (current != null) {
        if (current.linked() != null && current.linked().manhattan() > m) {
          res.setLinked(current.linked());
          current.setLinked(res);
          return res;
        }
        else if (current.linked() == null)
        {
          current.setLinked(res);
          return res;
        }
        current = current.linked();
      }
      return null;
    }


    public Board2 find(Board b) {
      Board2 current = first;
      int m = b.manhattan();
      while (current != null) {
        if(current.manhattan() > m) {
          return null;
        }
        if(current.manhattan() == m && current.hamming() == b.hamming() && current.board().equals(b)) {
          return current;
        }        
        current = current.linked();
      }
      return null;
    }
    
  }
  
  
}