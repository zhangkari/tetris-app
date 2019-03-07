package game.engine.utils;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundManager implements Runnable {

    private Context context;
    private List<Integer> playList;
    private boolean isRun = true;

    private MediaPlayer player;
    private static SoundManager soundMgr;

    public SoundManager(Context context) {
        this.context = context;
        player = new MediaPlayer();
        playList = new LinkedList<>();

        Thread t = new Thread(this);
        t.start();
    }

    public static SoundManager getInstance(Context context) {
        if (soundMgr == null) {
            soundMgr = new SoundManager(context);
        }
        return soundMgr;
    }

    public void free() {
        isRun = false;
        if (soundMgr != null) {
            soundMgr = null;
            player.stop();
            player.release();
            player = null;
        }
    }

    public void play(int id) {
        playList.add(id);
    }

    @Override
    public void run() {
        while (isRun) {
            for (int i = 0; i < playList.size(); ++i) {
                player.reset();
                player = MediaPlayer.create(context, playList.get(i));
                try {
                    try {
                        player.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
                player.start();
                playList.remove(i);
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
