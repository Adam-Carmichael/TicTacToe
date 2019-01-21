package com.projectstudy.rotten.tictactoe;

import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ImageView;
import android.view.LayoutInflater;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

// class that displays and creates buttons, handles game logic
public class boardActivity extends AppCompatActivity {
    private Board board;
    private BoardView boardView;
    private boolean playerChosen = false;

    // Main method to be called on program startup
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        board = new Board();
        boardView = new BoardView();
        boardView.setBoard();

        choosePlayer();

        while (playerChosen == false) {

        }

        playerChosen = false;
        boardView.createButtonGrid();
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
                playerChosen = true;
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
                playerChosen = true;
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
            // reset board and boardView elements and prompt pop-up to choose player
            board.resetBoard();
            board.setPlayerTile('0');
            board.setCpuTile('0');
            // missing: clear X and O images
            choosePlayer();
        }

        return super.onOptionsItemSelected(item);
    }
}
