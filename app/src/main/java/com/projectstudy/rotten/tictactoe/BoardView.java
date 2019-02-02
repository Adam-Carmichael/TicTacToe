package com.projectstudy.rotten.tictactoe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;


public class BoardView extends View {
    private Board board;
    private boardActivity activity;
    private Paint gridPaint, xPaint, oPaint;
    private int width, height;
    private Button[][] butArr = new Button[3][3];
    private final int GRID_STROKE_WIDTH = 50;
    private final int X_STROKE_WIDTH = 10;

    public BoardView(Context context, @Nullable AttributeSet attrs) {
        // passes context and attrs variables to constructor of View class
        // can maybe call context from View class? not sure what the use/purpose of this is
        super(context, attrs);

        gridPaint = new Paint();
        gridPaint.setColor(Color.BLACK);
        gridPaint.setStrokeWidth(GRID_STROKE_WIDTH);

        xPaint = new Paint();
        xPaint.setAntiAlias(true);
        xPaint.setStrokeWidth(X_STROKE_WIDTH);
        xPaint.setColor(Color.RED);

        oPaint = new Paint();
        oPaint.setAntiAlias(true);
        oPaint.setColor(Color.BLUE);
    }

    public void setBoard(Board someBoard) {
        board = someBoard;
    }
    public void setMainActivity(boardActivity someActivity) { activity = someActivity; }

    // TODO: implement another method that determines the location of a user touch event and uses those coordinates to draw
    public void createButtonGrid() {
        // dynamically create 9 transparent tile buttons
        // buttons will be positioned on top of the white space inbetween
        // the black lines of the board
        int i, j;
        int marginLeft, marginTop;
        for (i = 0; i < 3; ++i) {
            final int row = i;

            for (j = 0; j < 3; ++j) {
                final Button someButton = new Button(activity);
                someButton.setBackgroundColor(Color.TRANSPARENT);

                // if a tile is in the middle column then its width is reduced (because
                // it is surrounded on its left and right sides by grid lines)
                if (j == 1) {
                    someButton.setWidth(width / 3 - GRID_STROKE_WIDTH);
                } else {
                    someButton.setWidth(width / 3 - GRID_STROKE_WIDTH / 2);
                }

                // if a tile is in the middle row then its height is reduced (because
                // it is surrounded on its top and bottom sides by grid lines)
                if (i == 1) {
                    someButton.setHeight(height / 3 - GRID_STROKE_WIDTH);
                } else {
                    someButton.setHeight(height / 3 - GRID_STROKE_WIDTH / 2);
                }

                RelativeLayout rl = activity.findViewById(R.id.content_board);
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                // if a tile is on the first column then its left margin is 0
                if (j == 0) {
                    marginLeft = 0;
                } else {
                    marginLeft = width / 3 * j + GRID_STROKE_WIDTH / 2;
                }

                // if a tile is on the first row then its top margin is 0
                if (i == 0) {
                    marginTop = 0;
                } else {
                    marginTop = height / 3 * i + GRID_STROKE_WIDTH / 2;
                }

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
                        invalidate();
                    }
                });

                butArr[i][j] = someButton;
                rl.addView(someButton, lp);
            }
        }

        board.setButArr(butArr);
    }

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

    private void drawGrid(Canvas canvas) {
        // vertical lines
        canvas.drawLine(width / 3, 0, width / 3, height, gridPaint);
        canvas.drawLine(width * 2/3, 0, width * 2/3, height, gridPaint);

        // horizontal lines
        canvas.drawLine(0, height / 3, width, height / 3, gridPaint);
        canvas.drawLine(0, height * 2/3, width, height * 2/3, gridPaint);
    }

    // check board for location of tiles then draw them
    // additionally, call the method to determine where cpu will draw
    // and draw that tile, ending cpu's turn afterwards
    // check if game has ended inbetween player and cpu drawing
    // TODO: draw X and O dynamically
    private void drawBoard(Canvas canvas) {
        /**
         * if (j == 1) {
            someButton.setWidth(width / 3 - GRID_STROKE_WIDTH);
        } else {
            someButton.setWidth(width / 3 - GRID_STROKE_WIDTH / 2);
        }
        if (i == 1) {
            someButton.setHeight(height / 3 - GRID_STROKE_WIDTH);
        } else {
            someButton.setHeight(height / 3 - GRID_STROKE_WIDTH / 2);
        }

        if (j == 0) {
            marginLeft = 0;
        } else {
            marginLeft = width / 3 * j + GRID_STROKE_WIDTH / 2;
        }
        if (i == 0) {
            marginTop = 0;
        } else {
            marginTop = height / 3 * i + GRID_STROKE_WIDTH / 2;
        }**/

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

            char gameWon = board.checkWin();
            if (gameWon == 'X' || gameWon == 'O') {
                board.setGameEnded(true);
                activity.gameEndedPopup();
                return;
            }

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

            gameWon = board.checkWin();
            if (gameWon == 'X' || gameWon == 'O') {
                board.setGameEnded(true);
                activity.gameEndedPopup();
                return;
            }
        }
    }
}
