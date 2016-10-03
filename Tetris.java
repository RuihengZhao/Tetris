package tetris;

import javax.swing.*;
import java.awt.*;

public class Tetris extends JFrame{

    JLabel score;

    public Tetris(int fps, double speed, String sequence) {
        /*
        //  Splash Screen
        JWindow window = new JWindow();
        JTextArea instructions = new JTextArea(
                "                                    Tetris\n"+
                        "\n"+
                        "Move Left\tLEFT Arrow\tNumpad 4\n" +
                        "Move Right\tRIGHT Arrow\tNumpad 6\n" +
                        "Drop\tSpace Bar\tNumpad 8\n" +
                        "Rotate Right\tUP Arrow, X\tNumpad 1, 5, 9\n" +
                        "Rotate Left\tControl, Z\tNumpad 3, 7\n" +
                        "Pause\tP\t "
        );

        //window.getContentPane().add(new JLabel("Tetris", SwingConstants.CENTER));
        window.getContentPane().add(instructions);
        window.setBounds(0, 0, 280, 130);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.setVisible(false);
        */

        setTitle("Tetris");  //  Game Name
        setSize(500, 800);  //  Game Window Size
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        score = new JLabel();  //  Score
        score.setPreferredSize(new Dimension(100, 600));
        score.setHorizontalAlignment(SwingConstants.CENTER);
        add(score, BorderLayout.EAST);  //  Add Score to Game Window

        //  Create a Game Board
        tetris.GameBoard game_board = new GameBoard(this, fps, speed, sequence);
        game_board.setPreferredSize(new Dimension(400, 800));
        add(game_board);  //  Add Game Board to Game Window
        game_board.start();  //  Start Game
    }

    public JLabel getScore(){  //  For Game Board to update score
        return score;
    }
}
