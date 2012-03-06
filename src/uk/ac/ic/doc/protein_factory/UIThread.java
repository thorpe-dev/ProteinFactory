package uk.ac.ic.doc.protein_factory;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.util.Log;
import android.os.SystemClock;

/**
 * Created by IntelliJ IDEA.
 * User: Michael
 * Date: 06/03/12
 * Time: 20:46
 * To change this template use File | Settings | File Templates.
 */
public abstract class UIThread extends Thread{


    // Some flags to hold game state
    protected boolean running;
    protected SurfaceHolder holder;
    protected MainGamePanel panel;
    protected long LOOPTIME;
    private static final String TAG = UIThread.class.getSimpleName();

    public void setRunning(boolean running) { this.running = running; }

    public UIThread(SurfaceHolder h, MainGamePanel p)
    {
        this.holder = h;
        this.panel = p;
    }

    @Override
    public void run() {
        Log.d(TAG,"Starting ShiftThread");
        Canvas canvas;
        while (running) {
            long startTime = SystemClock.uptimeMillis();

            // Update game state and render this state to the screen
            canvas = null;
            try {
                canvas = this.holder.lockCanvas();
                synchronized (holder) {
                    method(canvas);

                    try {
                        Log.d(TAG,"Loop took " + (SystemClock.uptimeMillis()-startTime) + "ms");
                        long timeToSleep = LOOPTIME-(SystemClock.uptimeMillis()-startTime);
                        if(timeToSleep > 0)
                            Thread.sleep(timeToSleep);
                    }
                    catch (InterruptedException iex) { }
                }
            }
            finally {
                if (canvas != null)
                    holder.unlockCanvasAndPost(canvas);
            }
        }
        Log.d(TAG,"Game loop exceeded limits");
    }

    protected void method(Canvas canvas) {    }
}
