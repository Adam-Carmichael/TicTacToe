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
        xPaint.setStrokeWidth(10);
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

    // check board for location of tiles then draw them
    // additionally, call the method to determine where cpu will draw
    // and draw that tile, ending cpu's turn afterwards
    // check if game has ended inbetween player and cpu drawing
    private void drawBoard(Canvas canvas) {
        if (!board.getGameEnded()) {
            int i, j;
            float topLeftX, topLeftY, botRightX, botRightY;
            float botLeftX, botLeftY, topRightX, topRightY;
            float cenX, cenY, radius;
            for (i = 0; i < 3; ++i) {
                for (j = 0; j < 3; ++j) {
                    if (board.getTile(i, j) == 'X') {
                        topLeftX = j * 380;
                        topLeftY = 563 + i * 380;
                        botRightX = topLeftX + 320;
                        botRightY = topLeftY + 320;
                        canvas.drawLine(topLeftX, topLeftY, botRightX, botRightY, xPaint);

                        botLeftX = topLeftX;
                        botLeftY = topLeftY + 320;
                        topRightX = topLeftX + 320;
                        topRightY = topLeftY;
                        canvas.drawLine(botLeftX, botLeftY, topRightX, topRightY, xPaint);
                    } else if (board.getTile(i, j) == 'O') {
                        cenX = j * 380 + 160;
                        cenY = 563 + i * 380 + 160;
                        radius = 160;

                        canvas.drawCircle(cenX, cenY, radius, oPaint);
                    }
                }
            }

            // TODO: check win condition

            if (board.getCpuTurn()) {
                int[] coordArr;
                coordArr = board.getCoordCpuRandom();

                if (coordArr == null) {
                    board.setGameEnded(true);
                    board.setCpuTurn(false);
                    return;
                }

                int row, col;
                row = coordArr[0];
                col = coordArr[1];
                board.setCpuTile(row, col);

                if (board.getTile(row, col) == 'X') {
                    topLeftX = col * 380;
                    topLeftY = 563 + row * 380;
                    botRightX = topLeftX + 320;
                    botRightY = topLeftY + 320;
                    canvas.drawLine(topLeftX, topLeftY, botRightX, botRightY, xPaint);

                    botLeftX = topLeftX;
                    botLeftY = topLeftY + 320;
                    topRightX = topLeftX + 320;
                    topRightY = topLeftY;
                    canvas.drawLine(botLeftX, botLeftY, topRightX, topRightY, xPaint);
                } else if (board.getTile(row, col) == 'O') {
                    cenX = col * 380 + 160;
                    cenY = 563 + row * 380 + 160;
                    radius = 160;

                    canvas.drawCircle(cenX, cenY, radius, oPaint);
                }

                board.setButInvis(row, col);
                board.setCpuTurn(false);
            }

            // TODO: check win condition
        }
        else if (board.getGameEnded()) {
            activity.gameEndedPopup();
        }
    }
}
