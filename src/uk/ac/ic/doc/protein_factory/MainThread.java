package uk.ac.ic.doc.protein_factory;

import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;


/**
 * Created by IntelliJ IDEA.
 * User: Michael
 * Date: 02/03/12
 * Time: 17:05
 * To change this template use File | Settings | File Templates.
 */
public class MainThread extends UIThread {


    public MainThread(SurfaceHolder h, MainGamePanel p) {
        super(h,p);
        this.LOOPTIME = 20;
        this.TAG = MainThread.class.getSimpleName();
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
                    this.panel.onDraw(canvas);
                    this.panel.onShift(canvas);

                    try {
                        Log.d(TAG,"Loop took " + (SystemClock.uptimeMillis()-startTime) + "ms");
                        long timeToSleep = this.LOOPTIME-(SystemClock.uptimeMillis()-startTime);
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
}