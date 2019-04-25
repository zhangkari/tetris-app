package game.tetris.utils;

public interface Timer {
    /**
     * @see Timer#schedule(int, int, Object, OnTickListener),
     * the same with shedule(0, interval, null, listener)
     */
    int schedule(int interval, OnTickListener listener);

    /**
     * Submit a task in timer
     *
     * @param delay    execute delay at the first time
     * @param interval the interval time between next tick
     * @param argument the user incoming argument, can retrieve in {@link OnTickListener#onTick(Object)}
     * @param listener callback when timer expired
     * @return id  if failed return -1, else return >= 0, which can be called by {@link Timer#cancel(int id}
     */
    int schedule(int delay, int interval, Object argument, OnTickListener listener);

    /**
     * Cancel the specified task
     *
     * @param id task id
     */
    void cancel(int id);

    /**
     * Cancel the all task
     */
    void cancel();

    void destroy();

    interface OnTickListener {
        void onTick(Object argument);
    }
}
