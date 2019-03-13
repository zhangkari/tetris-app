package game.tetris.data;

import java.util.ArrayList;
import java.util.List;

import game.engine.drawable.KShapeData;

public class SpriteData {

    private static int[] data1 = {
            1, 1, 0, 0,
            0, 1, 1, 0,
            0, 0, 0, 0,
            0, 0, 0, 0
    };

    private static int[] data2 = {
            0, 1, 0, 0,
            1, 1, 0, 0,
            1, 0, 0, 0,
            0, 0, 0, 0
    };

    public List<KShapeData> getSpriteData() {
        List<KShapeData> list = new ArrayList<>();
        KShapeData d1 = new KShapeData(4, 4);
        d1.setData(data1);
        list.add(d1);

        KShapeData d2 = new KShapeData(4, 4);
        d2.setData(data2);
        list.add(d2);

        return list;
    }

}