package pl.bpiatek.tictactoe;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Created by Bartosz Piatek on 20/01/2020
 */
class PositionTest {

  @Test
  public void shouldCreatePosition() {
    // given when
    Position position = new Position();

    //then
    assertThat(position.getTurn()).isEqualTo('x');
    assertThat(position.toString()).isEqualTo("         ");
  }

  @Test
  public void shouldChangePlayerAfterMove() {
    // given
    Position position = new Position();

    // when
    Position move = position.move(1);

    // then
    assertThat(move.getTurn()).isEqualTo('o');
    assertThat(move.toString()).isEqualTo(" x       ");
  }

  @Test
  public void shouldDisplayPossibleMoves() {
    // given when
    Position pos = new Position().move(1).move(3).move(4);

    // then
    assertThat(pos.possibleMoves()).isEqualTo(new Integer[]{0, 2, 5, 6, 7, 8});
  }

  @Test
  public void shouldCheckIfGameIsWonHorizontally() {
    // player 'x'
    assertThat(new Position("xxx"
                            + "   "
                            + "   ")
                   .isGameWon('x')).isTrue();

    assertThat(new Position("   "
                            + "xxx"
                            + "   ")
                   .isGameWon('x')).isTrue();

    assertThat(new Position("   "
                            + "   "
                            + "xxx")
                   .isGameWon('x')).isTrue();

    //player 'o'
    assertThat(new Position("   "
                            + "ooo"
                            + "   ")
                   .isGameWon('o')).isTrue();

    assertThat(new Position("   "
                            + "ooo"
                            + "   ")
                   .isGameWon('o')).isTrue();

    assertThat(new Position("   "
                            + "   "
                            + "ooo")
                   .isGameWon('o')).isTrue();
  }

  @Test
  public void shouldCheckIfGameIsWonVertically() {
    //player 'o'
    assertThat(new Position("o  "
                            + "o  "
                            + "o  ")
                   .isGameWon('o')).isTrue();

    assertThat(new Position(" o "
                            + " o "
                            + " o ")
                   .isGameWon('o')).isTrue();

    assertThat(new Position("  o"
                            + "  o"
                            + "  o")
                   .isGameWon('o')).isTrue();

    // player 'x'
    assertThat(new Position("x  "
                            + "x  "
                            + "x  ")
                   .isGameWon('x')).isTrue();

    assertThat(new Position(" x "
                            + " x "
                            + " x ")
                   .isGameWon('x')).isTrue();

    assertThat(new Position("  x"
                            + "  x"
                            + "  x")
                   .isGameWon('x')).isTrue();
  }

  @Test
  public void shouldCheckIfGameIsWonDiagonally() {
    // player 'x'
    assertThat(new Position("x  "
                            + " x "
                            + "  x")
                   .isGameWon('x')).isTrue();

    assertThat(new Position("  x"
                            + " x "
                            + "x  ")
                   .isGameWon('x')).isTrue();

    // player 'o'
    assertThat(new Position("o  "
                            + " o "
                            + "  o")
                   .isGameWon('o')).isTrue();

    assertThat(new Position("  o"
                            + " o "
                            + "o  ")
                   .isGameWon('o')).isTrue();
  }

  @Test
  public void miniMaxShouldReturn100WhenXWon() {
    assertThat(new Position("xxx"
                            + "   "
                            + "   ").minimax()).isEqualTo(100);
  }

  @Test
  public void miniMaxShouldReturnNegative100WhenXWon() {
    assertThat(new Position("ooo"
                            + "   "
                            + "   ").minimax()).isEqualTo(-100);
  }

  @Test
  public void miniMaxShouldReturn0whenDraw() {
    assertThat(new Position("oxo"
                            + "xoo"
                            + "xox").minimax()).isEqualTo(0);
  }

  @Test
  public void miniMaxShouldReturnNegative99whenPlayerOHasOneStepToWin() {
    assertThat(new Position(" oo"
                            + "   "
                            + "   ", 'o').minimax()).isEqualTo(-99);
  }

  @Test
  public void miniMaxShouldReturn99whenPlayerXHasOneStepToWin() {
    assertThat(new Position("x   "
                            + " x "
                            + "   ").minimax()).isEqualTo(99);
  }

  @Test
  public void shouldCalculateBestMove() {
    assertThat(new Position(" xx"
                            + "   "
                            + "   ").bestMove()).isEqualTo(0);

    assertThat(new Position("o o"
                            + "   "
                            + "   ", 'o').bestMove()).isEqualTo(1);

    assertThat(new Position("o  "
                            + " o "
                            + "   ", 'o').bestMove()).isEqualTo(8);
  }

  @Test
  public void shouldIndicateIfGameEnds() {
    assertThat(new Position().gameEnd()).isFalse();

    assertThat(new Position("ooo"
                            + "   "
                            + "   ").gameEnd()).isTrue();

    assertThat(new Position("o  "
                            + " o "
                            + "  o").gameEnd()).isTrue();
  }
}
