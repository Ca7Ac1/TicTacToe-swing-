import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class TicTacToe extends JFrame
    implements ActionListener {

    JFrame game;

    JButton buttons[][] = new JButton[3][3];
    JButton start;
    
    ImageIcon X;
    ImageIcon O;
    ImageIcon ltr;

    int turnCounter;

    String letter;
    String board[][] = new String[3][3];
    boolean win;

    int BOARD_WIDTH  = 800;
    int BOARD_LENGTH = 800;

    public TicTacToe() {
        game = new JFrame("Tic Tac Toe");

        start = new JButton("Start");

        letter = "";

        win = false;
        
        X = new ImageIcon("Xshape.png");
        O = new ImageIcon("Oshape.png");
        ltr = new ImageIcon();

        initializeGame();
        
        initializeButtons();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setTurn();

        displayLetter(e);

        win = checkWin();

        finish();
    }

    public void initializeGame() {
        game.setSize(BOARD_WIDTH, BOARD_LENGTH);
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setLayout(new GridLayout(3, 3));
        game.setLocationRelativeTo(null);
        game.setResizable(false);
        game.setVisible(true);
    }

    public void initializeButtons() {
        for (int i = 0; i < 3; i++) {
            for (int k = 0; k < 3; k++) {    
                board[i][k] = "";
                
                buttons[i][k] = new JButton();
                game.add(buttons[i][k]);

                buttons[i][k].setEnabled(true);
                buttons[i][k].setIcon(null);
                buttons[i][k].addActionListener(this);
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
        for (int i = 0; i < 3; i++) {
            for (int k = 0; k < 3; k++) {
              if (e.getSource() == buttons[i][k]) {
                  board[i][k] = letter;
                
                  buttons[i][k].setIcon(ltr);
                  buttons[i][k].setDisabledIcon(ltr);
                  buttons[i][k].setEnabled(false);
              }  
            }
        }
    }

    public boolean checkWin() {
        String line = null;

        for (int i = 0; i < 3; i ++) {
            line = board[0][i] + board[1][i] + board[2][i];

            if (line.equals("XXX") || line.equals("OOO")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i ++) {
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

    public void finish() {
        if (win) {
            JOptionPane.showMessageDialog(null, "Player " + letter + " wins!");
            restart();
        } else if (turnCounter == 9) {
            JOptionPane.showMessageDialog(null, "Draw");
            restart();
        }
    }
    
    public void restart() {
        if (JOptionPane.showConfirmDialog(null, "Do you want to play again") == JOptionPane.YES_OPTION) {
            game.dispose();
            game = null;
            game = new JFrame("Tic Tac Toe");
            initializeGame();
            initializeButtons();
            turnCounter = 0;
        } else {
            game.dispose();
        }
    }
}