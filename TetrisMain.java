package tetris;

public class TetrisMain {

    public static void main(String[] args){
        System.out.println("Hello, Tetris!");

        try {
            ProgramArgs a = ProgramArgs.parseArgs(args);
            Tetris tetris = new Tetris(a.getFPS(), a.getSpeed(), a.getSequence());
            tetris.setVisible(true);  //  Display Tetris Game
            tetris.setLocationRelativeTo(null);  //  Display in the middle
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }
}


