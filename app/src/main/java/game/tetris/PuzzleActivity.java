package game.tetris;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.minmin.kari.tetris.R;

import game.engine.drawable.KShapeData;
import game.tetris.widget.MatrixView;

public class PuzzleActivity extends AppCompatActivity {

    private MatrixView holeView;
    private MatrixView leftView;
    private MatrixView centerView;
    private MatrixView rightView;

    private KShapeData holeData;
    private KShapeData leftData;
    private KShapeData centerData;
    private KShapeData rightData;

    /*
     * @param bundle
     */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_puzzle);

        holeView = findViewById(R.id.hole);
        leftView = findViewById(R.id.left);
        centerView = findViewById(R.id.center);
        rightView = findViewById(R.id.right);

        initViews();
    }

    private void initViews() {
        holeData = new KShapeData(8, 8);
        holeData.setData(
                1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 0, 1, 1, 1, 1, 1,
                1, 0, 0, 0, 0, 0, 1, 1,
                1, 0, 0, 0, 1, 0, 1, 1,
                1, 1, 0, 0, 1, 1, 1, 1,
                1, 1, 1, 0, 0, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1
        );
        holeView.setShapeData(holeData);


        leftData = new KShapeData(4, 4);
        leftView.setColor(Color.parseColor("#ffaabb"));
        leftData.setData(
                0, 1, 0, 0,
                1, 1, 0, 0,
                1, 0, 0, 0,
                0, 0, 0, 0);
        leftView.setShapeData(leftData);

        centerData = new KShapeData(4, 4);
        centerView.setColor(Color.parseColor("#aaffbb"));
        centerData.setData(
                0, 1, 0, 0,
                0, 1, 0, 0,
                1, 0, 1, 0,
                0, 0, 0, 0);
        centerView.setShapeData(centerData);

        rightData = new KShapeData(4, 4);
        rightView.setColor(Color.parseColor("#ffbb77"));
        rightData.setData(
                1, 0, 0, 0,
                1, 1, 1, 0,
                0, 0, 0, 0,
                0, 0, 0, 0);
        rightView.setShapeData(rightData);
    }
}