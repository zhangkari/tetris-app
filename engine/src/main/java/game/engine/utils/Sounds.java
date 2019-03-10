package game.engine.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.SparseIntArray;

public class Sounds {
    private static final Sounds sInstance = new Sounds();
    private SoundPool soundPool;
    private SparseIntArray soundIds;

    private Sounds() {
        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        soundIds = new SparseIntArray(8);
    }

    public static Sounds getInstance() {
        return sInstance;
    }

    /**
     * @param context the application context
     * @param resId   the resource ID
     * @param loop    loop mode (0 = no loop, -1 = loop forever, 2 = loop twice)
     * @return non-zero streamID if successful, zero if failed
     */
    public int play(Context context, int resId, int loop/**/) {
        int id = soundIds.get(resId, 0);
        if (id == 0) {
            id = soundPool.load(context, resId, 0);
            if (id == 0) {
                return 0;
            }
            soundIds.put(resId, id);
        }
        return soundPool.play(id, 1, 1, 0, loop, 1);
    }

    public void destroy() {
        for (int i = 0; i < soundIds.size(); i++) {
            int soundId = soundIds.valueAt(i);
            soundPool.unload(soundId);
        }
        soundIds.clear();
        soundPool.release();
    }
}
