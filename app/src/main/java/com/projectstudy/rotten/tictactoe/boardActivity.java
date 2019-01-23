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

// class that displays and creates buttons, handles game logic
public class boardActivity extends AppCompatActivity {
    private Board board;
    private BoardView boardView;
    private GameLogic gameLogic;

    // Main method to be called on program startup
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        board = new Board();
        boardView = (BoardView) findViewById(R.id.visibleBoard);
        gameLogic = new GameLogic();
        boardView.setBoard(board);

        boardView.drawBoard();
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
                gameLogic.setPlayerTile('X');
                gameLogic.setCpuTile('O');
                playerPopup.dismiss();
                createButtonGrid();
            }
        });

        // behavior of O button on click
        setO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // set playerTile to O in board class then close pop-up and start game
                gameLogic.setPlayerTile('O');
                gameLogic.setCpuTile('X');
                playerPopup.dismiss();
                createButtonGrid();
            }
        });
    }

    public void createButtonGrid() {
        // dynamically create 9 transparent tile buttons
        // buttons will be positioned on top of the white space inbetween
        // the black lines of the board
        int i, y, id;
        int marginLeft;
        int marginTop;
        final int[] rowCol = new int[2];    // rowCol[0] == row and rowCol[1] == col
        for (i = 0, id = 0; i < 3; ++i) {
            rowCol[0] = i;
            marginLeft = i * 380;

            for (y = 0; y < 3; ++y, ++id) {
                rowCol[1] = y;
                final Button someButton = new Button(this);
                someButton.setId(id);
                someButton.setBackgroundColor(Color.GREEN);
                // someButton.setBackgroundColor(Color.TRANSPARENT);
                someButton.setWidth(320);
                someButton.setHeight(320);

                RelativeLayout rl = findViewById(R.id.content_board);
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                System.out.println(rl);

                marginTop = 563 + y * 380;
                lp.setMargins(marginLeft, marginTop, 0, 0);

                someButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // end player turn, make button invisible, update board class,
                        // and draw the player tile on the board at given position
                        someButton.setVisibility(View.INVISIBLE);
                        board.setTile(gameLogic.getPlayerTile(), rowCol[0], rowCol[1]);
                        boardView.drawPlayerTile(gameLogic.getPlayerTile());
                    }
                });

                rl.addView(someButton, lp);
            }
        }
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
            // reset all classes, prompt pop-up window asking user to choose a tile
            board.resetBoard();
            boardView.resetView();
            gameLogic.resetLogic();
            choosePlayer();
        }

        return super.onOptionsItemSelected(item);
    }
}
