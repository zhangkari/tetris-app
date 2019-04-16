package game.tetris.data;

import java.util.ArrayList;
import java.util.List;

import game.engine.drawable.KShapeData;

public class SpriteData {

    private final List<List<KShapeData>> mAllShapes;

    private static int[] data100 = {
            1, 1, 0, 0,
            0, 1, 1, 0,
            0, 0, 0, 0,
            0, 0, 0, 0
    };

    private static int[] data101 = {
            0, 1, 0, 0,
            1, 1, 0, 0,
            1, 0, 0, 0,
            0, 0, 0, 0
    };

    private static int[] data110 = {
            0, 1, 1, 0,
            1, 1, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0
    };

    private static int[] data111 = {
            1, 0, 0, 0,
            1, 1, 0, 0,
            0, 1, 0, 0,
            0, 0, 0, 0
    };

    private static int[] data200 = {
            1, 1, 1, 1,
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0
    };

    private static int[] data201 = {
            1, 0, 0, 0,
            1, 0, 0, 0,
            1, 0, 0, 0,
            1, 0, 0, 0,
    };

    private static int[] data300 = {
            1, 1, 0, 0,
            1, 1, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0,
    };

    private static int[] data400 = {
            1, 0, 0, 0,
            1, 1, 0, 0,
            1, 0, 0, 0,
            0, 0, 0, 0
    };

    private static int[] data401 = {
            1, 1, 1, 0,
            0, 1, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0,
    };

    private static int[] data402 = {
            0, 1, 0, 0,
            1, 1, 0, 0,
            0, 1, 0, 0,
            0, 0, 0, 0
    };

    private static int[] data403 = {
            0, 1, 0, 0,
            1, 1, 1, 0,
            0, 0, 0, 0,
            0, 0, 0, 0
    };

    private static int[] data500 = {
            1, 1, 0, 0,
            0, 1, 0, 0,
            0, 1, 0, 0,
            0, 0, 0, 0
    };

    private static int[] data501 = {
            0, 0, 1, 0,
            1, 1, 1, 0,
            0, 0, 0, 0,
            0, 0, 0, 0
    };

    private static int[] data502 = {
            1, 0, 0, 0,
            1, 0, 0, 0,
            1, 1, 0, 0,
            0, 0, 0, 0
    };

    private static int[] data503 = {
            1, 1, 1, 0,
            0, 0, 1, 0,
            0, 0, 0, 0,
            0, 0, 0, 0
    };

    private static int[] data510 = {
            1, 1, 0, 0,
            1, 0, 0, 0,
            1, 0, 0, 0,
            0, 0, 0, 0
    };

    private static int[] data511 = {
            1, 1, 1, 0,
            0, 0, 1, 0,
            0, 0, 0, 0,
            0, 0, 0, 0
    };

    private static int[] data512 = {
            0, 1, 0, 0,
            0, 1, 0, 0,
            1, 1, 0, 0,
            0, 0, 0, 0
    };

    private static int[] data513 = {
            1, 0, 0, 0,
            1, 1, 1, 0,
            0, 0, 0, 0,
            0, 0, 0, 0
    };

    public SpriteData() {
        mAllShapes = new ArrayList<>();
        mAllShapes.add(synthesizeShapeData(data100, data101));
        mAllShapes.add(synthesizeShapeData(data101, data100));
        mAllShapes.add(synthesizeShapeData(data110, data111));
        mAllShapes.add(synthesizeShapeData(data111, data110));

        mAllShapes.add(synthesizeShapeData(data200, data201));
        mAllShapes.add(synthesizeShapeData(data201, data200));

        mAllShapes.add(synthesizeShapeData(data300));

        mAllShapes.add(synthesizeShapeData(data400, data401, data402, data403));
        mAllShapes.add(synthesizeShapeData(data401, data402, data403, data400));
        mAllShapes.add(synthesizeShapeData(data402, data403, data400, data401));
        mAllShapes.add(synthesizeShapeData(data403, data400, data401, data402));

        mAllShapes.add(synthesizeShapeData(data500, data501, data502, data503));
        mAllShapes.add(synthesizeShapeData(data501, data502, data503, data500));
        mAllShapes.add(synthesizeShapeData(data502, data503, data500, data501));
        mAllShapes.add(synthesizeShapeData(data503, data500, data501, data502));

        mAllShapes.add(synthesizeShapeData(data510, data511, data512, data513));
        mAllShapes.add(synthesizeShapeData(data511, data512, data513, data510));
        mAllShapes.add(synthesizeShapeData(data512, data513, data510, data511));
        mAllShapes.add(synthesizeShapeData(data513, data510, data511, data512));
    }

    private List<KShapeData> synthesizeShapeData(int[] firstFrame, int[]... nextFrames) {
        List<KShapeData> list = new ArrayList<>(1 + nextFrames.length);
        KShapeData first = new KShapeData(4, 4);
        first.setData(firstFrame);
        list.add(first);

        for (int[] frame : nextFrames) {
            KShapeData data = new KShapeData(4, 4);
            data.setData(frame);
            list.add(data);
        }

        return list;
    }

    public List<List<KShapeData>> getAllShapes() {
        return mAllShapes;
    }
}