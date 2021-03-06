package com.rohankadkol.tictactoe7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private ImageView mIv00;
    private ImageView mIv01;
    private ImageView mIv02;
    private ImageView mIv10;
    private ImageView mIv11;
    private ImageView mIv12;
    private ImageView mIv20;
    private ImageView mIv21;
    private ImageView mIv22;

    private enum Team {LSU, ALABAMA, EMPTY}

    private Team mTurn = Team.LSU;

    private HashMap<Integer, ImageView> mIdTileMap = new HashMap<>();
    private HashMap<Integer, Integer[]> mIdIndexMap = new HashMap<>();

    private Team[][] board = {
            {Team.EMPTY, Team.EMPTY, Team.EMPTY},
            {Team.EMPTY, Team.EMPTY, Team.EMPTY},
            {Team.EMPTY, Team.EMPTY, Team.EMPTY}
    };

    private boolean isBoardActive = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIv00 = findViewById(R.id.iv_00);
        mIv01 = findViewById(R.id.iv_01);
        mIv02 = findViewById(R.id.iv_02);
        mIv10 = findViewById(R.id.iv_10);
        mIv11 = findViewById(R.id.iv_11);
        mIv12 = findViewById(R.id.iv_12);
        mIv20 = findViewById(R.id.iv_20);
        mIv21 = findViewById(R.id.iv_21);
        mIv22 = findViewById(R.id.iv_22);

        mIdTileMap.put(R.id.iv_00, mIv00);
        mIdTileMap.put(R.id.iv_01, mIv01);
        mIdTileMap.put(R.id.iv_02, mIv02);
        mIdTileMap.put(R.id.iv_10, mIv10);
        mIdTileMap.put(R.id.iv_11, mIv11);
        mIdTileMap.put(R.id.iv_12, mIv12);
        mIdTileMap.put(R.id.iv_20, mIv20);
        mIdTileMap.put(R.id.iv_21, mIv21);
        mIdTileMap.put(R.id.iv_22, mIv22);

        mIdIndexMap.put(R.id.iv_00, new Integer[]{0, 0});
        mIdIndexMap.put(R.id.iv_01, new Integer[]{0, 1});
        mIdIndexMap.put(R.id.iv_02, new Integer[]{0, 2});
        mIdIndexMap.put(R.id.iv_10, new Integer[]{1, 0});
        mIdIndexMap.put(R.id.iv_11, new Integer[]{1, 1});
        mIdIndexMap.put(R.id.iv_12, new Integer[]{1, 2});
        mIdIndexMap.put(R.id.iv_20, new Integer[]{2, 0});
        mIdIndexMap.put(R.id.iv_21, new Integer[]{2, 1});
        mIdIndexMap.put(R.id.iv_22, new Integer[]{2, 2});
    }

    public void onTileClicked(View view) {
        int id = view.getId();
        Integer[] indices = mIdIndexMap.get(id);
        if (!isBoardActive || board[indices[0]][indices[1]] != Team.EMPTY) {
            return;
        }

        int image = mTurn == Team.LSU ? R.drawable.lsu_logo : R.drawable.alabama_logo;
        ImageView imageView = mIdTileMap.get(id);
        imageView.setImageResource(image);

        board[indices[0]][indices[1]] = mTurn;

        checkWon(id);

        mTurn = mTurn == Team.LSU ? Team.ALABAMA : Team.LSU;
    }

    private void checkWon(int tileId) {
        switch (tileId) {
            case R.id.iv_00:
                isBoardActive = !(horizontal1() || diagonal1() || vertical1());
                break;
            case R.id.iv_01:
                isBoardActive = !(horizontal1() || vertical2());
                break;
            case R.id.iv_02:
                isBoardActive = !(horizontal1() || diagonal2() || vertical3());
                break;
            case R.id.iv_10:
                isBoardActive = !(horizontal2() || vertical1());
                break;
            case R.id.iv_11:
                isBoardActive = !(horizontal2() ||  diagonal1() || vertical2() || diagonal2());
                break;
            case R.id.iv_12:
                isBoardActive = !(vertical3() || horizontal2());
                break;
            case R.id.iv_20:
                isBoardActive = !(vertical1() || diagonal2() || horizontal3());
                break;
            case R.id.iv_21:
                isBoardActive = !(vertical2() || horizontal3());
                break;
            case R.id.iv_22:
                isBoardActive = !(vertical3() || diagonal1() || horizontal3());
                break;
        }
    }

    private boolean horizontal1() {
        if (areThreeEqual(board[0][0], board[0][1], board[0][2])) {
            won(new Integer[]{R.id.iv_00, R.id.iv_01, R.id.iv_02});
            return true;
        }
        return false;
    }

    private boolean horizontal2() {
        if (areThreeEqual(board[1][0], board[1][1], board[1][2])) {
            won(new Integer[]{R.id.iv_10, R.id.iv_11, R.id.iv_12});
            return true;
        }
        return false;
    }

    private boolean horizontal3() {
        if (areThreeEqual(board[2][0], board[2][1], board[2][2])) {
            won(new Integer[]{R.id.iv_20, R.id.iv_21, R.id.iv_22});
            return true;
        }
        return false;
    }

    private boolean vertical1() {
        if (areThreeEqual(board[0][0], board[1][0], board[2][0])) {
            won(new Integer[]{R.id.iv_00, R.id.iv_10, R.id.iv_20});
            return true;
        }
        return false;
    }

    private boolean vertical2() {
        if (areThreeEqual(board[0][1], board[1][1], board[2][1])) {
            won(new Integer[]{R.id.iv_01, R.id.iv_11, R.id.iv_21});
            return true;
        }
        return false;
    }

    private boolean vertical3() {
        if (areThreeEqual(board[0][2], board[1][2], board[2][2])) {
            won(new Integer[]{R.id.iv_02, R.id.iv_12, R.id.iv_22});
            return true;
        }
        return false;
    }

    private boolean diagonal1() {
        if (areThreeEqual(board[0][0], board[1][1], board[2][2])) {
            won(new Integer[]{R.id.iv_00, R.id.iv_11, R.id.iv_22});
            return true;
        }
        return false;
    }

    private boolean diagonal2() {
        if (areThreeEqual(board[0][2], board[1][1], board[2][0])) {
            won(new Integer[]{R.id.iv_02, R.id.iv_11, R.id.iv_20});
            return true;
        }
        return false;
    }

    private void won(Integer[] winingIds) {
        int[] imageViewIds = new int[]{R.id.iv_00, R.id.iv_01, R.id.iv_02, R.id.iv_10, R.id.iv_11, R.id.iv_12, R.id.iv_20, R.id.iv_21, R.id.iv_22};
        for (int imageId : imageViewIds) {
            if (imageId != winingIds[0] && imageId != winingIds[1] && imageId != winingIds[2]) {
                mIdTileMap.get(imageId).setAlpha((float) 0.4);
            }
        }
    }

    private <T> boolean areThreeEqual(T a, T b, T c) {
        return a == b && b == c;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_reset) {
            resetApp();
        }
        return super.onOptionsItemSelected(item);
    }

    private void resetApp() {
        isBoardActive = true;
        mTurn = Team.LSU;
        board = new Team[][] {
                {Team.EMPTY, Team.EMPTY, Team.EMPTY},
                {Team.EMPTY, Team.EMPTY, Team.EMPTY},
                {Team.EMPTY, Team.EMPTY, Team.EMPTY}
        };
        int[] imageViewIds = new int[]{R.id.iv_00, R.id.iv_01, R.id.iv_02, R.id.iv_10, R.id.iv_11, R.id.iv_12, R.id.iv_20, R.id.iv_21, R.id.iv_22};
        for (int imageId : imageViewIds) {
            mIdTileMap.get(imageId).setImageResource(R.drawable.blank_transparent_square);
            mIdTileMap.get(imageId).setAlpha((float) 1);
        }
    }
}
