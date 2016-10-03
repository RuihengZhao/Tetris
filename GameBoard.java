package tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameBoard extends JPanel implements ActionListener{

    JLabel update_score;
    int score = 0;

    String seq = "";
    int FPS = 0;

    Timer timer;

    boolean gameStarted = false;
    boolean stopFalling = false;
    boolean gamePaused = false;

    String[][] board;   //  Contain every single grid in Game Board

    int[][] I = new int[][]{ {0, -1}, {0, 0}, {0, 1}, {0, 2} };
    int[][] L = new int[][]{ {0, -1}, {0, 0}, {0, 1}, {1, 1} };
    int[][] J = new int[][]{ {-1, 1}, {0, 1}, {0, 0}, {0, -1} };
    int[][] T = new int[][]{ {-1, 0}, {0, 0}, {1, 0}, {0, 1} };
    int[][] O = new int[][]{ {0, 0}, {1, 0}, {0, 1}, {1, 1} };
    int[][] S = new int[][]{ {-1, 1}, {0, 1}, {0, 0}, {1, 0} };
    int[][] Z = new int[][]{ {-1, 0}, {0, 0}, {0, 1}, {1, 1} };

    int[][][] I_positions = new int[][][] {
            { {1, 0}, {0, 0}, {-1, 0}, {-2, 0} },
            { {0, -1}, {0, 0}, {0, 1}, {0, 2} },
            { {1, 0}, {0, 0}, {-1, 0}, {-2, 0} },
            { {0, -1}, {0, 0}, {0, 1}, {0, 2} }
    };

    int[][][] L_positions = new int[][][] {
            { {1, 0}, {0, 0}, {-1, 0}, {1, -1} },
            { {0, -1}, {0, 0}, {0, 1}, {-1, -1} },
            { {1, 0}, {0, 0}, {-1, 0}, {-1, 1} },
            { {0, -1}, {0, 0}, {0, 1}, {1, 1} }
    };

    int[][][] J_positions = new int[][][] {
            { {1, 1}, {1, 0}, {0, 0}, {-1, 0} },
            { {1, -1}, {0, 1}, {0, 0}, {0, -1} },
            { {-1, -1}, {-1, 0}, {0, 0}, {1, 0} },
            { {-1, 1}, {0, 1}, {0, 0}, {0, -1} }
    };

    int[][][] T_positions = new int[][][] {
            { {0, -1}, {0, 0}, {0, 1}, {1, 0} },
            { {-1, 0}, {0, 0}, {1, 0}, {0, -1} },
            { {-1, 0}, {0, 0}, {0, -1}, {0, 1} },
            { {-1, 0}, {0, 0}, {1, 0}, {0, 1} }
    };

    int[][][] O_positions = new int[][][] {
            { {0, 0}, {1, 0}, {0, 1}, {1, 1} },
            { {0, 0}, {1, 0}, {0, 1}, {1, 1} },
            { {0, 0}, {1, 0}, {0, 1}, {1, 1} },
            { {0, 0}, {1, 0}, {0, 1}, {1, 1} }
    };

    int[][][] S_positions = new int[][][] {
            { {-1, -1}, {-1, 0}, {0, 0}, {0, 1} },
            { {-1, 1}, {0, 1}, {0, 0}, {1, 0} },
            { {-1, -1}, {-1, 0}, {0, 0}, {0, 1} },
            { {-1, 1}, {0, 1}, {0, 0}, {1, 0} }
    };

    int[][][] Z_positions = new int[][][] {
            { {0, -1}, {0, 0}, {-1, 0}, {-1, 1} },
            { {-1, 0}, {0, 0}, {0, 1}, {1, 1} },
            { {0, -1}, {0, 0}, {-1, 0}, {-1, 1} },
            { {-1, 0}, {0, 0}, {0, 1}, {1, 1} }
    };

    int[][][] Pieces = new int[][][]{I, L, J, T, O, S, Z};
    int[][] currentPiece = new int[4][2];

    String movingPiece = "N";
    int coordX = 0;
    int coordY = 0;
    int index = 0;
    int left_index = 0;
    int right_index = 2;

    public GameBoard(Tetris t, int fps, double speed, String sequence){
        //System.out.println(String.valueOf("GameBoard"));

        setFocusable(true);
        setBackground(new Color(153, 153, 153));

        timer = new Timer((int)speed * 50, this);

        seq = sequence;
        FPS = fps;
        update_score = t.getScore();
        board = new String[24][10];

        addKeyListener(new TetrisKeyAdapter());
        addMouseListener(new TetrisMouseAdapter());
    }

    public void actionPerformed(ActionEvent e) {
        if (stopFalling){
            stopFalling = false;
            newPiece();
        }else {
            keepFalling();
        }
    }

    public void start(){
        //System.out.println(String.valueOf("start"));

        for (int i = 0; i < 24; i++){
            for (int j = 0; j < 10; j++){
                board[i][j] = "N";
            }
        }

        gameStarted = true;
        gamePaused = false;
        stopFalling = false;

        index = 0;
        left_index = 0;

        score = 0;
        update_score.setText(String.valueOf(score));

        newPiece();

        timer.start();
    }

    private void pause(){
        if (gameStarted){
            if (!gamePaused){  //  First time click Pause -> Pause
                gamePaused = true;
                timer.stop();
                //  Show Continue on Button
            }else {  //  Game already paused -> Continue
                gamePaused = false;
                timer.start();
                //  Show Pause on Button
            }
        }

        repaint();
    }

    private  void getPiecebySeq(){
        if (index == seq.length()){
            index = 0;
        }

        char pieceName = seq.charAt(index);
        index++;

        switch (pieceName){
            case 'I':
                currentPiece = Pieces[0];
                movingPiece = "I";
                break;
            case 'L':
                currentPiece = Pieces[1];
                movingPiece = "L";
                break;
            case 'J':
                currentPiece = Pieces[2];
                movingPiece = "J";
                break;
            case 'T':
                currentPiece = Pieces[3];
                movingPiece = "T";
                break;
            case 'O':
                currentPiece = Pieces[4];
                movingPiece = "O";
                break;
            case 'S':
                currentPiece = Pieces[5];
                movingPiece = "S";
                break;
            case 'Z':
                currentPiece = Pieces[6];
                movingPiece = "Z";
                break;
        }
    }

    private void newPiece(){
        //System.out.println(String.valueOf("newPiece"));

        getPiecebySeq();

        int m = -1;
        if (movingPiece == "I" || movingPiece == "L" || movingPiece == "J"){
            m = -2;
        }

        left_index = 0;
        coordX = 10 / 2;
        coordY = 24 + m;

        isGameOver();
    }

    private void isGameOver(){
        //System.out.println(String.valueOf("isGameOver"));

        boolean movable = move(currentPiece, coordX, coordY);
        if (!movable){
            timer.stop();
            gameStarted = false;
            //  Show Play Again on Button
        }
    }

    private void keepFalling(){
        //System.out.println(String.valueOf("keepFalling"));

        boolean moved = move(currentPiece, coordX, coordY - 1);
        if (!moved) updateBoard();
    }

    private void drop(){
        boolean moved = true;

        while (moved){
            moved = move(currentPiece, coordX, coordY - 1);
            if (!moved) break;
        }

        updateBoard();
    }

    private boolean move(int[][] p, int x, int y){
        //System.out.println(String.valueOf("move"));

        for (int i = 0; i < 4; i++){
            int newX = x + p[i][0];
            int newY = y - p[i][1];
            if (newX < 0 || newX >= 10 || newY < 0 || newY >= 24 || board[newY][newX] != "N") return false;
        }

        currentPiece = p;
        coordX = x;
        coordY = y;

        repaint();
        return true;
    }

    public int[][] rotate(String direction){
        //System.out.println(String.valueOf("rotateLeft"));

        int[][] l = new int[4][2];
        int[][] r = new int[4][2];

        switch (movingPiece){
            case "I":
                l = I_positions[left_index];
                r = I_positions[right_index];
                break;
            case "L":
                l = L_positions[left_index];
                r = L_positions[right_index];
                break;
            case "J":
                l = J_positions[left_index];
                r = J_positions[right_index];
                break;
            case "T":
                l = T_positions[left_index];
                r = T_positions[right_index];
                break;
            case "O":
                l = O_positions[left_index];
                r = O_positions[right_index];
                break;
            case "S":
                l = S_positions[left_index];
                r = S_positions[right_index];
                break;
            case "Z":
                l = Z_positions[left_index];
                r = Z_positions[right_index];
                break;
        }

        left_index++;
        if (left_index == 4){
            left_index = 0;
        }
        right_index--;
        if (right_index == -1){
            right_index = 3;
        }

        if (direction == "Left"){
            currentPiece = l;
            repaint();
            return l;
        }else {
            currentPiece = r;
            repaint();
            return r;
        }

    }

    private void removeFullLines(){
        for (int i = 23; i >= 0; i--){
            boolean fullLine = true;

            for (int j = 0; j < 10; j++){
                if (board[i][j] == "N") fullLine = false;
            }

            if (fullLine){
                score++;
                update_score.setText(String.valueOf(score));

                for (int k = i; k < 23; k++){
                    for (int l = 0; l < 10; l++){
                        board[k][l] = board[k + 1][l];
                    }
                }
            }
        }

        repaint();
    }

    private void updateBoard(){
        //System.out.println(String.valueOf("updateBoard"));

        for (int i = 0; i < 4; i++){  //  Store the new piece into Board
            int x = coordX + currentPiece[i][0];
            int y = coordY - currentPiece[i][1];
            board[y][x] = movingPiece;
        }

        //  Print the Board
        printBoard();

        removeFullLines();

        if (!stopFalling) newPiece();
    }

    private void printBoard(){  //  For Testing
        for (int i = 23; i >= 0; i--){
            for (int j = 0; j < 10; j++){
                //System.out.print(board[i][j]+"  ");
            }
            //System.out.println();
        }
    }

    private Color getColor(String p){
        Color color = new Color(0, 0, 0);

        switch (p){
            case "I":
                color = new Color(18, 133, 247);
                break;
            case "L":
                color = new Color(244, 133, 21);
                break;
            case "J":
                color = new Color(110, 110, 245);
                break;
            case "T":
                color = new Color(248, 109, 248);
                break;
            case "O":
                color = new Color(251, 251, 8);
                break;
            case "S":
                color = new Color(8, 251, 8);
                break;
            case "Z":
                color = new Color(245, 21, 21);
                break;
        }

        return color;
    }

    public void paint(Graphics g){
        //System.out.println(String.valueOf("paint"));

        super.paint(g);

        int squareWidth = (int)size().getWidth() / 10;
        int squareHeight = (int)size().getHeight() / 24;

        //  Paint all the shapes that have been dropped to the bottom
        for (int i = 0; i < 24; i++){
            for (int j = 0; j < 10; j++){
                String p = board[24 - i - 1][j];
                if(p != "N"){
                    int x = j * squareWidth;
                    int y = 17 + i * squareHeight;

                    g.setColor(getColor(p));
                    g.fillRect(x + 1, y + 1, squareWidth - 1, squareHeight - 1);
                }
            }
        }

        //  Paint the falling piece
        for (int i = 0; i < 4; i++){
            int x = coordX + currentPiece[i][0];
            int y = coordY - currentPiece[i][1];

            x = x * squareWidth;
            y = 17 + (22 - y) * squareHeight;

            g.setColor(getColor(movingPiece));
            g.fillRect(x + 1, y + 1, squareWidth - 1, squareHeight - 1);
        }
    }

    class TetrisKeyAdapter extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            if (!gameStarted) return;

            int keycode = e.getKeyCode();

            switch (keycode){
                case KeyEvent.VK_P:
                    pause();
                    break;
                case KeyEvent.VK_LEFT:
                    move(currentPiece, coordX - 1, coordY);
                    break;
                case KeyEvent.VK_NUMPAD4:
                    move(currentPiece, coordX - 1, coordY);
                    break;
                case KeyEvent.VK_RIGHT:
                    move(currentPiece, coordX + 1, coordY);
                    break;
                case KeyEvent.VK_NUMPAD6:
                    move(currentPiece, coordX + 1, coordY);
                    break;
                case KeyEvent.VK_UP:
                    rotate("Right");
                    break;
                case KeyEvent.VK_X:
                    rotate("Right");
                    break;
                case KeyEvent.VK_NUMPAD1:
                    rotate("Right");
                    break;
                case KeyEvent.VK_NUMPAD5:
                    rotate("Right");
                    break;
                case KeyEvent.VK_NUMPAD9:
                    rotate("Right");
                    break;
                case KeyEvent.VK_CONTROL:
                    rotate("Left");
                    break;
                case KeyEvent.VK_Z:
                    rotate("Left");
                    break;
                case KeyEvent.VK_NUMPAD3:
                    rotate("Left");
                    break;
                case KeyEvent.VK_NUMPAD7:
                    rotate("Left");
                    break;
                case KeyEvent.VK_SPACE:
                    drop();
                    break;
                case KeyEvent.VK_NUMPAD8:
                    drop();
                    break;
            }
        }
    }

    class TetrisMouseAdapter extends MouseAdapter{
        boolean clicked = false;

        int x = 0;
        int y = 0;

        int newX = 0;
        int newY = 0;

        public void mouseClicked(MouseEvent e){
            //System.out.println(String.valueOf("mouseClicked"));

            clicked = true;

            x = e.getX();
            y = e.getY();

            //System.out.println(String.valueOf(String.valueOf(x)));
            //System.out.println(String.valueOf(String.valueOf(y)));

        }

        public void mouseWheelMoved(MouseWheelEvent e){
            //System.out.println(String.valueOf("mouseWheelMoved"));
            rotate("Right");
        }
    }

}
