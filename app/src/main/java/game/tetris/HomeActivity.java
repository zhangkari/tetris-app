package game.tetris;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.minmin.kari.tetris.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViewById(R.id.tv_puzzle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoPuzzle();
            }
        });

        findViewById(R.id.tv_tetris).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoTetris();
            }
        });
    }

    private void gotoPuzzle() {
        startActivity(new Intent(this, PuzzleActivity.class));
    }

    private void gotoTetris() {
        startActivity(new Intent(this, TetrisActivity.class));
    }
}
