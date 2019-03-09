package game.tetris.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Storages {

    private String fileName = "Tetris_Setting";
    private Context context;

    private static Storages mgr = null;

    public Storages(Context context) {
        this.context = context;
    }

    public static Storages getInstance(Context context) {
        if (mgr == null) {
            mgr = new Storages(context);
        }
        return mgr;
    }

    public boolean isPlayMusic() {
        boolean ret = true;

        SharedPreferences setting = context.getSharedPreferences(fileName, 0);
        ret = setting.getBoolean("music_on", true);
        return ret;
    }

    public boolean isHeartOn() {
        boolean ret = true;
        SharedPreferences setting = context.getSharedPreferences(fileName, 0);
        ret = setting.getBoolean("heart_mode", true);
        return ret;
    }

    public void saveMusicMode(boolean status) {
        SharedPreferences setting = context.getSharedPreferences(fileName, 0);
        Editor edit = setting.edit();
        edit.putBoolean("music_on", status);
        edit.commit();
    }

    public void saveHeartMode(boolean status) {
        SharedPreferences setting = context.getSharedPreferences(fileName, 0);
        Editor edit = setting.edit();
        edit.putBoolean("heart_mode", status);
        edit.commit();
    }

    public int getHeightScore() {
        int ret = 0;
        SharedPreferences setting = context.getSharedPreferences(fileName, 0);
        ret = setting.getInt("score_record", 0);
        return ret;
    }

    public void saveHeighScore(int score) {
        int ret = this.getHeightScore();

        if (score > ret) {
            SharedPreferences setting = context.getSharedPreferences(fileName, 0);
            Editor edit = setting.edit();
            edit.putInt("score_record", score);
            edit.commit();
        }
    }
}
