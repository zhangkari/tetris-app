import org.junit.Assert;
import org.junit.Test;

import game.engine.drawable.KShapeData;

public class KShapeDataTest {
    @Test
    public void testGetOccupiedRows() {
        int[] data1 = {
                1, 1, 0, 0,
                0, 1, 1, 0,
                0, 0, 0, 0,
                0, 0, 0, 0
        };
        KShapeData d1 = new KShapeData(4, 4);
        d1.setData(data1);
        Assert.assertEquals(d1.getOccupiedCols(), 3);
        Assert.assertEquals(d1.getOccupiedRows(), 2);

        int[] data2 = {
                0, 1, 0, 0,
                1, 1, 0, 0,
                1, 0, 0, 0,
                0, 0, 0, 0
        };
        KShapeData d2 = new KShapeData(4, 4);
        d2.setData(data2);
        Assert.assertEquals(d2.getOccupiedCols(), 2);
        Assert.assertEquals(d2.getOccupiedRows(), 3);

        int[] data3 = {
                1, 0, 0, 0,
                1, 1, 0, 0,
                0, 1, 0, 0,
                0, 0, 0, 0
        };
        KShapeData d3 = new KShapeData(4, 4);
        d3.setData(data3);
        Assert.assertEquals(d3.getOccupiedCols(), 2);
        Assert.assertEquals(d3.getOccupiedRows(), 3);
    }
}
