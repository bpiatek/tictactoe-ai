package pl.bpiatek.tictactoe;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import static pl.bpiatek.tictactoe.Position.TIC_TAC_TOE_DIMENSIONS;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

/**
 * Created by Bartosz Piatek on 20/01/2020
 */
@Component
class UI implements CommandLineRunner {

  private Position position = new Position();

  void runGame() {

    SwingUtilities.invokeLater(() -> {
      UI ui = new UI();

      JFrame window = new JFrame("Bartosz Piątek s439462 Kolko i krzyzyk");
      window.setDefaultCloseOperation(EXIT_ON_CLOSE);
      window.setLayout(new GridLayout(3, 3));
      final JButton[] buttons = new JButton[9];

      for (int i = 0; i < TIC_TAC_TOE_DIMENSIONS * TIC_TAC_TOE_DIMENSIONS; i++) {
        final int index = i;

        JButton button = new JButton();
        buttons[i] = button;
        button.setPreferredSize(new Dimension(100, 100));
        button.setBackground(Color.BLACK);
        button.setOpaque(true);
        button.setFont(new Font(null, Font.BOLD, 50));

        button.addMouseListener(new MouseListener() {
          @Override
          public void mouseClicked(MouseEvent e) {
            button.setText("" + ui.position.getTurn());
            ui.move(index);

            if(!ui.position.gameEnd()) {
              int bestMove = ui.position.bestMove();
              buttons[bestMove].setText("" + ui.position.getTurn());
              ui.move(bestMove);
            }

            if(ui.position.gameEnd()) {
              if(ui.position.isGameWon('x')) {
                JOptionPane.showMessageDialog(null, "Czlowiek wygral");
              } else if(ui.position.isGameWon('o')) {
                JOptionPane.showMessageDialog(null, "komputer wygral");
              } else {
                JOptionPane.showMessageDialog(null, "Remis");
              }
            }
          }

          @Override
          public void mousePressed(MouseEvent e) {

          }

          @Override
          public void mouseReleased(MouseEvent e) {

          }

          @Override
          public void mouseEntered(MouseEvent e) {

          }

          @Override
          public void mouseExited(MouseEvent e) {

          }
        });

        window.add(button);
      }

      window.pack();
      window.setVisible(true);
    });
  }

  private void move(int index) {
    position = position.move(index);
  }

  @Override
  public void run(String... args) throws Exception {
    this.runGame();
  }
}
