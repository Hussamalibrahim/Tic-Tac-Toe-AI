# ❎⭕ Ultimate Tic-Tac-Toe AI

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)

![Swing](https://img.shields.io/badge/GUI-Java_Swing-007396)

![AI](https://img.shields.io/badge/AI-Minimax_Algorithm-blueviolet)

A challenging Tic-Tac-Toe game featuring an unbeatable AI opponent using the Minimax algorithm with Alpha-Beta pruning.

![Game Screenshot](demo.gif)

## Features

- 🤖 **Unbeatable AI** using Minimax algorithm
- ⚡ **Alpha-Beta pruning** for optimal performance
- 🎮 **Interactive GUI** with Java Swing
- 🔄 **Quick restart** with SPACE key
- 🏆 **Win/draw detection** with visual feedback


# AI Implementation
```
private int minimax(char[][] board, int depth, boolean isMaximizing, int alpha, int beta) {
    if (checkForWinner(board) == myTurn) return -10 + depth;
    if (checkForWinner(board) == AITurn) return 10 - depth;
    if (isBoardFull(board)) return 0;

    if (isMaximizing) {
        int best = Integer.MIN_VALUE;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = AITurn;
                    int moveVal = minimax(board, depth + 1, false, alpha, beta);
                    board[i][j] = ' ';
                    best = Math.max(best, moveVal);
                    alpha = Math.max(alpha, best);
                    if (beta <= alpha) break; // Beta cut-off
                }
            }
        }
        return best;
    }
}
```

# Controls

Action	Key/Click

Make move	Click on empty cell

Restart game	SPACE key

Exit game	Close window

# Customization
Modify these constants in Game.java:

```
private static final char myTurn = 'O';  // Player symbol
private static final char AITurn = 'X'; // AI symbol
this.setSize(800, 800);                // Window size
```
® Created by Hussam Alibrahim
