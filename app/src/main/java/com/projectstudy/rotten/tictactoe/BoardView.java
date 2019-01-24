package com.projectstudy.rotten.tictactoe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class BoardView extends View {
    private Board board;
    private boardActivity activity;
    private Paint gridPaint, xPaint, oPaint;
    private int width, height;

    public BoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        gridPaint = new Paint();
        gridPaint.setColor(Color.BLACK);
        gridPaint.setStrokeWidth(50);

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
    public void setMainActivity(boardActivity someActivity) { activity = someActivity; }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        height = View.MeasureSpec.getSize(heightMeasureSpec);
        width = View.MeasureSpec.getSize(widthMeasureSpec);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawGrid(canvas);
        drawBoard(canvas);
    }

    // TODO: dynamically draw grid
    private void drawGrid(Canvas canvas) {
        // vertical lines
        canvas.drawLine(348, 563, 348, 1643, gridPaint);
        canvas.drawLine(728, 563, 728, 1643, gridPaint);

        // horizontal lines
        canvas.drawLine(0, 912, 1080, 912, gridPaint);
        canvas.drawLine(0, 1292, 1080, 1292, gridPaint);
    }

    private void drawBoard(Canvas canvas) {
        // marginLeft = i * 380;
        // marginTop = 563 + y * 380;

        int i, y;
        float topLeftX, topLeftY, botRightX, botRightY;
        float botLeftX, botLeftY, topRightX, topRightY;
        for(i = 0; i < 3; ++i) {
            for(y = 0; y < 3; ++y) {
                if (board.getTile(i, y) == 'X') {
                    topLeftX = i * 380;
                    topLeftY = 563 + y * 380;
                    botRightX = topLeftX + 320;
                    botRightY = topLeftY + 320;
                    canvas.drawLine(topLeftX, topLeftY, botRightX, botRightY, xPaint);

                    botLeftX = topLeftX;
                    botLeftY = topLeftY + 320;
                    topRightX = topLeftX + 320;
                    topRightY = topLeftY;
                    canvas.drawLine(botLeftX, botLeftY, topRightX, topRightY, xPaint);
                }
                else if (board.getTile(i, y) == 'O') {
                    //canvas.drawCircle();
                }
                else { continue; }
            }
        }

        if (board.getCpuTurn()) {
            // TODO: draw CPU tile, update board class with setCpuTile method
            board.setCpuTurn(false);
        }
    }
}
