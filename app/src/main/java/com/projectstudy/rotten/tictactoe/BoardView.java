package com.projectstudy.rotten.tictactoe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class BoardView extends View {
    Board board;
    Canvas c;
    Bitmap bmp;
    Path path;
    Paint xPaint;
    Paint oPaint;

    public BoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setBoard(Board someBoard) {
        board = someBoard;
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
                final Button someButton = new Button(this.getContext());
                someButton.setId(id);
                someButton.setBackgroundColor(Color.GREEN);
                // someButton.setBackgroundColor(Color.TRANSPARENT);
                someButton.setWidth(320);
                someButton.setHeight(320);

                RelativeLayout rl = findViewById(R.id.content_board);
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                marginTop = 563 + y * 380;
                lp.setMargins(marginLeft, marginTop, 0, 0);

                someButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // end player turn, make button invisible, update board class,
                        // and draw the player tile on the board at given position
                        someButton.setVisibility(View.INVISIBLE);
                        board.setTile(board.getPlayerTile(), rowCol[0], rowCol[1]);
                        drawPlayerTile(board.getPlayerTile());
                    }
                });

                rl.addView(someButton, lp);
            }
        }
    }

    public void drawPlayerTile(char playerTile) {
        System.out.println("drawPlayerTile method is called");

        bmp = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
        c = new Canvas(bmp);
        path = new Path();

        xPaint = new Paint();
        xPaint.setAntiAlias(true);
        xPaint.setColor(Color.RED);

        oPaint = new Paint();
        oPaint.setAntiAlias(true);
        oPaint.setColor(Color.BLUE);

        c.setImageBitmap(bmp);

        c.drawLine(10, 10, 20, 20, xPaint);
        c.drawLine(20, 20, 10, 10, xPaint);

        c.setImageBitmap(bmp);
    }
}
