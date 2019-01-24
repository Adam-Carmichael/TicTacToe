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
        int i, y;
        int marginLeft;
        int marginTop;
        for (i = 0; i < 3; ++i) {
            marginLeft = i * 380;

            for (y = 0; y < 3; ++y) {
                final Button someButton = new Button(this);
                //someButton.setBackgroundColor(Color.GREEN);
                someButton.setBackgroundColor(Color.TRANSPARENT);
                someButton.setWidth(320);
                someButton.setHeight(320);

                RelativeLayout rl = findViewById(R.id.content_board);
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                marginTop = 563 + y * 380;
                lp.setMargins(marginLeft, marginTop, 0, 0);

                butArr[i][y] = someButton;
                rl.addView(someButton, lp);
            }
        }

        // define the event listeners of the buttons created
        for (i = 0; i < 3; ++i) {
            for (y = 0; y < 3; ++y) {
                butArr[i][y].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // make button invisible, update board class,
                        // and draw the player tile on the board at given position
                        butArr[i][y].setVisibility(View.INVISIBLE);
                        // TODO: fix this instance of setPlayerTile method
                        board.setPlayerTile(i, y);
                        System.out.println("Printing values of rowCol:" + i + " " + y);
                        System.out.println("Printing value of charTile in Board class:" + board.getTile(i, y));
                        board.setCpuTurn(true);
                        boardView.invalidate();
                    }
                });
            }
        }

        board.setButArr(butArr);
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
            board.resetBoard();
            boardView.invalidate();
            choosePlayer();
        }

        return super.onOptionsItemSelected(item);
    }
}
