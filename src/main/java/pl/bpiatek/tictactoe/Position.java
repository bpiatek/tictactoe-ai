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

  // wykonywanie ruchu
  public Position move(int index) {
    char[] boardClone = board.clone();
    // oznaczenie ruchu na tablicy
    boardClone[index] = turn;

    // zmiana ruchu na kolej przeciwnika
    return new Position(boardClone, turn == 'x' ? 'o' : 'x');
  }

  // obliczanie mozliwych ruchów
  public Integer[] possibleMoves() {
    List<Integer> ints = new ArrayList<>();
    for (int i = 0; i < board.length; i++) {
      // jesli pozycja na tablicy nie jest zajeta dodaj ja do mozliwych ruchow
      if (board[i] == ' ') {
        ints.add(i);
      }
    }

    Integer[] possibleMovesArray = new Integer[ints.size()];
    ints.toArray(possibleMovesArray);

    return possibleMovesArray;
  }


  // sprawdzanie czy gra jest wygrana
  public boolean isGameWon(char player) {
    for (int i = 0; i < TIC_TAC_TOE_DIMENSIONS; i++) {
      // warunki sprawdzajace linie poziome i pionowe
      if (isLineWon(player, i * TIC_TAC_TOE_DIMENSIONS, 1) || isLineWon(player, i, TIC_TAC_TOE_DIMENSIONS)) {
        return true;
      }
    }
    // warunki sprawdzajace przekatne
    if(isLineWon(player, TIC_TAC_TOE_DIMENSIONS - 1, TIC_TAC_TOE_DIMENSIONS - 1) ||
       isLineWon(player, 0, TIC_TAC_TOE_DIMENSIONS + 1)) {
      return true;
    }

    return false;
  }

  // sprawdzanie czy sa 3 'x' lub 'o' w rzedzie
  public boolean isLineWon(char player, int start, int step) {
    for (int i = 0; i < TIC_TAC_TOE_DIMENSIONS; i++) {
      if (board[start + step * i] != player) {
        return false;
      }
    }

    return true;
  }

  // sprawdzanie wagi ruchu
  public int minimax() {
    // sprawdzamy czy gra juz zostala wygrana czy nie lub czy jest remis
    // jak ktoras opcja jest prawidlowa to zwracamy od razu wynik
    if(isGameWon('x')) {
      return 100;
    } else if(isGameWon('o')) {
      return -100;
    } else if(possibleMoves().length == 0) {
      return 0;
    }

    Integer minOrMax  = null;
    // sprawdzamy mozliwosci w szeregu mozliwych ruchow
    for (Integer index : possibleMoves()) {

      int value = move(index).minimax();
      if((minOrMax == null) ||
         (turn == 'x' && minOrMax < value) ||
         (turn == 'o' && minOrMax > value)) {
        minOrMax = value;
      }
    }

    // dodaj lub odejmij jedno dla kazdego kroku
    return minOrMax + (turn == 'x' ? -1 : 1);
  }

  public int bestMove() {
    Integer minOrMax = null;
    // na poczatek najlepszy krok jest poza plansza
    int bestMove = -1;
    // dla kazdego ruchu obliczamy jego wartosc
    for (Integer index : possibleMoves()) {
      // wartosc ruchu
      Integer value = move(index).minimax();

      // jesli znajdujemy lepszy ruch to zapamietujemy jego wage
      // oraz wartosc jaki to ruch
      if((minOrMax == null) ||
         (turn == 'x' && minOrMax < value) ||
         (turn == 'o' && minOrMax > value)) {
        minOrMax = value;
        bestMove = index;
      }
    }

    // zwracamy najlepszy ruch
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
