import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.lang.Math;

public class TicTacToe extends JFrame implements ActionListener {

    JButton buttons[][] = new JButton[3][3];
    JButton start;

    ImageIcon X;
    ImageIcon O;
    ImageIcon ltr;
    String botLetter;
    String playerLetter;

    int turnCounter;

    String letter;
    String board[][] = new String[3][3];

    int BOARD_WIDTH = 800;
    int BOARD_LENGTH = 800;

    boolean bot;
    boolean botFirst;

    public TicTacToe() {
        super("Tic Tac Toe");

        start = new JButton("Start");

        letter = "";

        botFirst = false;

        X = new ImageIcon("Xshape.png");
        O = new ImageIcon("Oshape.png");
        ltr = new ImageIcon();

        initializeGame();

        initializeButtons();

        checkBot();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setTurn();

        displayLetter(e);

        if (!bot) {
            finish(checkWin(board));
        }
    }

    public void checkBot() {
        if (JOptionPane.showConfirmDialog(null, "Do you wish to play with a bot?") == JOptionPane.YES_OPTION) {
            bot = true;
        } else {
            bot = false;
        }

        if (botFirst) {
            botLetter = "X";
            playerLetter = "O";

            if (bot) {
                botTurn();
            }
        } else {
            botLetter = "O";
            playerLetter = "X";
        }
    }

    public void initializeGame() {
        setSize(BOARD_WIDTH, BOARD_LENGTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 3));
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public void initializeButtons() {
        for (int i = 0; i < 3; i++) {
            for (int k = 0; k < 3; k++) {
                board[i][k] = "";

                buttons[i][k] = new JButton();
                add(buttons[i][k]);

                buttons[i][k].setEnabled(true);
                buttons[i][k].setIcon(null);
                buttons[i][k].addActionListener(this);
            }
        }
    }

    public void restartButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = "";

                buttons[i][j].setEnabled(true);
                buttons[i][j].setIcon(null);
            }
        }
    }

    public void setTurn() {
        turnCounter++;

        if (turnCounter % 2 == 1) {
            ltr = X;
            letter = "X";
        } else {
            ltr = O;
            letter = "O";
        }
    }

    public void displayLetter(ActionEvent e) {
        boolean moveMade = false;

        for (int i = 0; i < 3; i++) {
            for (int k = 0; k < 3; k++) {
                if (e.getSource() == buttons[i][k]) {
                    board[i][k] = letter;

                    buttons[i][k].setIcon(ltr);
                    buttons[i][k].setDisabledIcon(ltr);
                    buttons[i][k].setEnabled(false);

                    moveMade = true;
                    break;
                }
            }
            if (moveMade) {
                break;
            }
        }

        if (moveMade) {
            if (bot) {
                boolean win = checkWin(board);
                finish(win);

                if (!win && turnCounter != 9) {
                    botTurn();
                }
            }
        }
    }

    public void botTurn() {
        setTurn();

        int[] position = findBestMove(board);
        JButton bestMove = buttons[position[0]][position[1]];

        board[position[0]][position[1]] = letter;

        bestMove.setIcon(ltr);
        bestMove.setDisabledIcon(ltr);
        bestMove.setEnabled(false);

        finish(checkWin(board));
    }

    public int[] findBestMove(String[][] board) {
        int bestVal = -1000;
        int moveVal;
        int bestX = 0;
        int bestY = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == "") {
                    board[i][j] = botLetter;
                    moveVal = miniMax(board, 0, -1000, 1000, false);
                    board[i][j] = "";

                    if (moveVal > bestVal) {
                        bestVal = moveVal;
                        bestX = i;
                        bestY = j;
                    }
                }
            }
        }
        System.out.println(bestX + " " + bestY);
        System.out.println(bestVal);
        return new int[] { bestX, bestY };
    }

    public int miniMax(String[][] board, int depth, int alpha, int beta, boolean isBot) {
        if (checkWin(board)) {
            if (getWinString(board).equals(botLetter + botLetter + botLetter)) {
                return 10 - depth;
            }

            if (getWinString(board).equals(playerLetter + playerLetter + playerLetter)) {
                return -10 + depth;
            }
        }

        if (depth + turnCounter == 9) {
            return 0;
        }

        if (isBot) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == "") {
                        board[i][j] = botLetter;
                        alpha = Math.max(alpha, miniMax(board, depth + 1, alpha, beta, !isBot));
                        board[i][j] = "";

                        if (beta <= alpha) {
                            return beta;
                        }
                    }
                }
            }
            return alpha;
        } else {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == "") {
                        board[i][j] = playerLetter;
                        beta = Math.min(beta, miniMax(board, depth + 1, alpha, beta, !isBot));
                        board[i][j] = "";
                        if (beta <= alpha) {
                            return alpha;
                        }
                    }
                }
            }
            return beta;
        }
    }

    public boolean checkWin(String[][] board) {
        String line = null;

        for (int i = 0; i < 3; i++) {
            line = board[0][i] + board[1][i] + board[2][i];

            if (line.equals("XXX") || line.equals("OOO")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            line = board[i][0] + board[i][1] + board[i][2];

            if (line.equals("XXX") || line.equals("OOO")) {
                return true;
            }
        }

        line = board[0][0] + board[1][1] + board[2][2];

        if (line.equals("XXX") || line.equals("OOO")) {
            return true;
        }

        line = board[2][0] + board[1][1] + board[0][2];

        if (line.equals("XXX") || line.equals("OOO")) {
            return true;
        }

        return false;
    }

    public String getWinString(String[][] board) {
        String line = null;

        for (int i = 0; i < 3; i++) {
            line = board[0][i] + board[1][i] + board[2][i];

            if (line.equals("XXX") || line.equals("OOO")) {
                return line;
            }
        }

        for (int i = 0; i < 3; i++) {
            line = board[i][0] + board[i][1] + board[i][2];

            if (line.equals("XXX") || line.equals("OOO")) {
                return line;
            }
        }

        line = board[0][0] + board[1][1] + board[2][2];

        if (line.equals("XXX") || line.equals("OOO")) {
            return line;
        }

        line = board[2][0] + board[1][1] + board[0][2];

        if (line.equals("XXX") || line.equals("OOO")) {
            return line;
        }

        return null;
    }

    public void finish(boolean win) {
        if (win) {
            JOptionPane.showMessageDialog(this, "Player " + letter + " wins!");
            restart();
        } else if (turnCounter == 9) {
            JOptionPane.showMessageDialog(this, "Draw");
            restart();
        }
    }

    public void restart() {
        if (JOptionPane.showConfirmDialog(this, "Do you want to play again") == JOptionPane.YES_OPTION) {
            turnCounter = 0;
            botFirst = !botFirst;

            restartButtons();
            checkBot();
        } else {
            dispose();
        }
    }

    public void printBoard(String[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == "") {
                    System.out.print(" ");
                } else {
                    System.out.print(board[i][j]);
                }
            }
            System.out.println();
        }
    }
}