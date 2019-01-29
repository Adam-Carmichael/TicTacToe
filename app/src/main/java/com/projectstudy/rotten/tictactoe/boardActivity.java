package com.projectstudy.rotten.tictactoe;

import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.PopupWindow;
import android.view.LayoutInflater;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

// Main class that creates buttons and a pop-up window
// initializes objects for game functionality
public class boardActivity extends AppCompatActivity {
    private Board board;
    private BoardView boardView;
    private final Button[][] butArr = new Button[3][3];

    // Main method to be called on program startup
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // display view with toolbar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // initialize objects
        board = new Board();
        boardView = (BoardView) findViewById(R.id.visibleBoard);
        boardView.setBoard(board);
        boardView.setMainActivity(this);

        // create a pop-up window
        choosePlayer();
    }

    // Method that handles popup window asking for user to choose to play as the X or O tile user
    public void choosePlayer() {
        // inflates layout of popup window
        LayoutInflater inflater = (LayoutInflater)
            getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.choose_player_popup, null);

        // initialize popup window and buttons to be in popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = false;
        final PopupWindow playerPopup = new PopupWindow(popupView, width, height, focusable);
        Button setX = popupView.findViewById(R.id.setX);
        Button setO = popupView.findViewById(R.id.setO);

        // ensures popup is not shown before layout content is loaded
        findViewById(R.id.content_board).post(new Runnable() {
           public void run() {
               playerPopup.showAtLocation(findViewById(R.id.content_board), Gravity.CENTER, 10, 10);
           }
        });

        // behavior of X button on click
        setX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // set playerTile to X in board class then close pop-up and start game
                board.setPlayerTile('X');
                board.setCpuTile('O');
                playerPopup.dismiss();
                createButtonGrid();
            }
        });

        // behavior of O button on click
        setO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // set playerTile to O in board class then close pop-up and start game
                board.setPlayerTile('O');
                board.setCpuTile('X');
                playerPopup.dismiss();
                createButtonGrid();
            }
        });
    }

    public void createButtonGrid() {
        // dynamically create 9 transparent tile buttons
        // buttons will be positioned on top of the white space inbetween
        // the black lines of the board
        int i, j;
        int marginLeft;
        int marginTop;
        for (i = 0; i < 3; ++i) {
            marginTop = 563 + i * 380;
            final int row = i;

            for (j = 0; j < 3; ++j) {
                final Button someButton = new Button(this);
                someButton.setBackgroundColor(Color.TRANSPARENT);
                someButton.setWidth(320);
                someButton.setHeight(320);

                RelativeLayout rl = findViewById(R.id.content_board);
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                marginLeft = j * 380;
                lp.setMargins(marginLeft, marginTop, 0, 0);

                final int col = j;
                someButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // make button invisible, update board class,
                        // and draw the player tile on the board at given position
                        butArr[row][col].setVisibility(View.INVISIBLE);
                        board.setPlayerTile(row, col);
                        board.setCpuTurn(true);
                        boardView.invalidate();
                    }
                });

                butArr[i][j] = someButton;
                rl.addView(someButton, lp);
            }
        }

        board.setButArr(butArr);
    }

    public void gameEndedPopup() {
        // inflates layout of popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.game_over_popup, null);

        // initialize popup window and buttons to be in popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = false;
        final PopupWindow gameEndedPopup = new PopupWindow(popupView, width, height, focusable);
        TextView playerText = (TextView) popupView.findViewById(R.id.playerWin);
        TextView cpuText = (TextView) popupView.findViewById(R.id.cpuWin);

        char winner = board.checkWin();
        if (winner == board.getPlayerTile()) {
            playerText.setVisibility(View.VISIBLE);
        }

        if (winner == board.getCpuTile()) {
            cpuText.setVisibility(View.VISIBLE);
        }

        // ensures popup is not shown before layout content is loaded
        findViewById(R.id.content_board).post(new Runnable() {
            public void run() {
                gameEndedPopup.showAtLocation(findViewById(R.id.content_board), Gravity.CENTER, 10, 10);
            }
        });
    }

    // Populates the toolbar with the Start New Game and Quit text buttons
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Determines actions performed once a menu item is selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_quit) {
            System.exit(0);
        }
        else if (id == R.id.action_newGame) {
            // reset board class, clear X and O images, prompt pop-up window asking user to choose a tile
            // TODO: fix crashing issue here
            board.resetBoard();
            boardView.invalidate();
            choosePlayer();
        }

        return super.onOptionsItemSelected(item);
    }
}
