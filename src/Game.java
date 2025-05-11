import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JFrame implements ActionListener, KeyListener {
    private static final char myTurn = 'O';
    private static final char AITurn = 'X';
    private static char turn = AITurn;
    private static boolean win;
    private static int numberOfMove;
    static char[][] arr;
    static private final JPanel panelText = new JPanel();
    static public JLabel labelText = new JLabel("TIC TAK TOE");
    static private final JButton[] buttons = new JButton[9];
    static int number =0;

    Game() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 800);
        this.getContentPane().setBackground(new Color(50, 50, 50));
        this.setLayout(new BorderLayout());
        this.addKeyListener(this);
        labelText.setHorizontalAlignment(JLabel.CENTER);
        labelText.setFont(new Font("Ink Free", Font.BOLD, 75));
        labelText.setForeground(new Color(25, 255, 0));
        labelText.setBackground(new Color(25, 25, 25));
        labelText.setOpaque(true);
        panelText.setLayout(new BorderLayout());
        panelText.setBounds(0, 0, 800, 100);
        panelText.add(labelText);
        this.add(panelText, BorderLayout.NORTH);
        JPanel board = new JPanel();
        board.setLayout(new GridLayout(3, 3));
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            board.add(buttons[i]);
            buttons[i].setFont(new Font("Apple", Font.BOLD, 120));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
        }
        startGame();
        this.add(board);
        this.setVisible(true);
    }

    public static void game(int i) {
        buttons[i].setText(String.valueOf(turn));
        buttons[i].setEnabled(false);
        numberOfMove++;
        arr[i / 3][i % 3] = turn;
        changePlayer();
    }

    private static void changePlayer() {
        turn = (turn == AITurn) ? myTurn : AITurn;
    }

    static void checkWinner() {
        for (int i = 0; i < 3; i++) {
            if (arr[i][0] == arr[i][1] && arr[i][1] == arr[i][2] && arr[i][0] != ' ') {
                win = true;
            }
            if (arr[0][i] == arr[1][i] && arr[1][i] == arr[2][i] && arr[0][i] != ' ') {
                win = true;
            }
        }
        if (arr[0][0] == arr[1][1] && arr[1][1] == arr[2][2] && arr[0][0] != ' ') {
            win = true;
        }
        if (arr[0][2] == arr[1][1] && arr[1][1] == arr[2][0] && arr[0][2] != ' ') {
            win = true;
        }
        winner();
        checkDraw();
    }

    static void winner() {
        if (win) {
            changePlayer();
            if (turn == myTurn) {
                labelText.setText("YOU WIN");
            } else {
                labelText.setText("YOU LOSE");
            }
            for (int i = 0; i < 9; i++) {
                buttons[i].setEnabled(false);
            }
        }
    }

    static void checkDraw() {
        if (numberOfMove == 10 && !win) {
            labelText.setText("GAME DRAW");
            win = true;
        }
    }

    private void startGame() {
        labelText.setText("TIC TAC TOE");
        win = false;
        numberOfMove = 0;
        turn = AITurn;
        arr = new char[][]{
                {' ', ' ', ' '},
                {' ', ' ', ' '},
                {' ', ' ', ' '}
        };
        for (int i = 0; i < 9; i++) {
            buttons[i].setText("");
            buttons[i].setEnabled(true);
        }
        buttons[4].setText(String.valueOf(turn));
        game(4);
        buttons[4].setEnabled(false);
        numberOfMove++;
    }

    private void computer() {
        int bestMove = minimax(arr, 0, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
        game(bestMove);
    }

    private int minimax(char[][] board, int depth, boolean isMaximizing, int alpha, int beta) {
        System.out.println(number++);
        if (checkForWinner(board) == myTurn) {
            return -10 + depth;
        }
        if (checkForWinner(board) == AITurn) {
            return 10 - depth; // AI wins
        }
        if (isBoardFull(board)) {
            return 0; // Draw
        }

        int bestMove = -1;

        if (isMaximizing) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = AITurn;
                        int moveVal = minimax(board, depth + 1, false, alpha, beta);
                        board[i][j] = ' ';
                        if (moveVal > best) {
                            best = moveVal;
                            bestMove = i * 3 + j;
                        }
                        alpha = Math.max(alpha, moveVal);
                        if (beta <= alpha) {
                            break; // Beta cut-off
                        }
                    }
                }
            }
            return bestMove;
        } else { // Player's turn
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = myTurn;
                        int moveVal = minimax(board, depth + 1, true, alpha, beta);
                        board[i][j] = ' ';
                        if (moveVal < best) {
                            best = moveVal;
                            bestMove = i * 3 + j;
                        }
                        beta = Math.min(beta, moveVal);
                        if (beta <= alpha) {
                            break; // Alpha cut-off
                        }
                    }
                }
            }
            return bestMove;
        }
    }

    private char checkForWinner(char[][] board) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != ' ') {
                return board[i][0];
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != ' ') {
                return board[0][i];
            }
        }
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != ' ') {
            return board[0][0];
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != ' ') {
            return board[0][2];
        }
        return ' ';
    }

    private boolean isBoardFull(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 9; i++) {
            if (e.getSource() == buttons[i] && buttons[i].isEnabled()) {
                game(i);
                checkWinner();
                if (turn == AITurn && !win) {
                    computer();
                    checkWinner();
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && win) {
            new Game();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
