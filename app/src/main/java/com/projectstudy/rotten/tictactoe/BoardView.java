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


public class BoardView extends View {
    Board board;
    Canvas c;
    Bitmap bmp;
    Path path;
    Paint xPaint;
    Paint oPaint;

    public BoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        xPaint = new Paint();
        xPaint.setAntiAlias(true);
        xPaint.setColor(Color.RED);

        oPaint = new Paint();
        oPaint.setAntiAlias(true);
        oPaint.setColor(Color.BLUE);
    }

    public void setBoard(Board someBoard) {
        board = someBoard;
    }

    public void drawBoard() {

    }

    public void drawPlayerTile(char playerTile) {
        System.out.println("drawPlayerTile method is called");

        bmp = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
        c = new Canvas(bmp);
        path = new Path();

        //c.setImageBitmap(bmp);

        c.drawLine(10, 10, 20, 20, xPaint);
        c.drawLine(20, 20, 10, 10, xPaint);

        //c.setImageBitmap(bmp);
    }

    // sets all private variables of BoardView class to default values
    public void resetView() {
        c.drawColor(Color.WHITE);
    }
}
