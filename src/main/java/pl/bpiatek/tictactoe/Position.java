package pl.bpiatek.tictactoe;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bartosz Piatek on 20/01/2020
 */
@Getter
class Position {

  public static int TIC_TAC_TOE_DIMENSIONS = 3;

  private char[] board;
  private char turn;

  public Position() {
    this.board = "         ".toCharArray();
    this.turn = 'x';
  }

  public Position(String board) {
    this.board = board.toCharArray();
    this.turn = 'x';
  }

  public Position(String board, char turn) {
    this.board = board.toCharArray();
    this.turn = turn;
  }

  public Position(char[] board, char turn) {
    this.board = board;
    this.turn = turn;
  }

  public Position move(int index) {
    char[] boardClone = board.clone();
    boardClone[index] = turn;

    return new Position(boardClone, turn == 'x' ? 'o' : 'x');
  }

  // calculate which moves the player can make
  public Integer[] possibleMoves() {
    List<Integer> ints = new ArrayList<>();
    for (int i = 0; i < board.length; i++) {
      // if the position is not taken yet by 'o' or 'x' add it ass possible move
      if (board[i] == ' ') {
        ints.add(i);
      }
    }

    Integer[] possibleMovesArray = new Integer[ints.size()];
    ints.toArray(possibleMovesArray);

    return possibleMovesArray;
  }


  public boolean isGameWon(char player) {
    for (int i = 0; i < TIC_TAC_TOE_DIMENSIONS; i++) {
      // conditions for horizontal and vertical lines
      if (isLineWon(player, i * TIC_TAC_TOE_DIMENSIONS, 1) || isLineWon(player, i, TIC_TAC_TOE_DIMENSIONS)) {
        return true;
      }
    }
    // condition for diagonal lines
    if(isLineWon(player, TIC_TAC_TOE_DIMENSIONS - 1, TIC_TAC_TOE_DIMENSIONS - 1) ||
       isLineWon(player, 0, TIC_TAC_TOE_DIMENSIONS + 1)) {
      return true;
    }

    return false;
  }

  public boolean isLineWon(char player, int start, int step) {
    for (int i = 0; i < TIC_TAC_TOE_DIMENSIONS; i++) {
      if (board[start + step * i] != player) {
        return false;
      }
    }

    return true;
  }

  public int minimax() {
    if(isGameWon('x')) {
      return 100;
    } else if(isGameWon('o')) {
      return -100;
    } else if(possibleMoves().length == 0) {
      return 0;
    }

    Integer minOrMax  = null;
    for (Integer index : possibleMoves()) {
      int value = move(index).minimax();
      if((minOrMax == null) ||
         (turn == 'x' && minOrMax < value) ||
         (turn == 'o' && minOrMax > value)) {
        minOrMax = value;
      }
    }

    // subtract or add one for every step
    return minOrMax + (turn == 'x' ? -1 : 1);
  }

  public int bestMove() {
    Integer minOrMax = null;
    int bestMove = -1;
    for (Integer index : possibleMoves()) {
      Integer value = move(index).minimax();
      if((minOrMax == null) ||
         (turn == 'x' && minOrMax < value) ||
         (turn == 'o' && minOrMax > value)) {
        minOrMax = value;
        bestMove = index;
      }
    }

    return bestMove;
  }

  @Override
  public String toString() {
    return new String(board);
  }

  public boolean gameEnd() {
    return isGameWon('x') || isGameWon('o') || possibleMoves().length == 0;
  }
}
