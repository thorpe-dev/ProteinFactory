package uk.ac.ic.doc.protein_factory;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.util.Log;


/**
 * Created by IntelliJ IDEA.
 * User: Michael
 * Date: 02/03/12
 * Time: 17:05
 * To change this template use File | Settings | File Templates.
 */
public class MainThread extends Thread {

    // Some flags to hold game state

    private boolean running;
    private SurfaceHolder holder;
    private MainGamePanel panel;
    private static final String TAG = MainThread.class.getSimpleName();

    public void setRunning(boolean running) {this.running = running;}

    public MainThread(SurfaceHolder h, MainGamePanel p)
    {
        super();
        this.holder = h;
        this.panel = p;
    }

    @Override
    public void run()
    {
        Log.d(TAG,"Starting MainThread");
        Canvas canvas;
        long count = 0;
        while (running)
        {
            // Update game state and render this state to the screen
            Log.d(TAG,"Counts = " + count);
            canvas = null;
            count++;
            try
            {
                canvas = this.holder.lockCanvas();
                synchronized (holder)
                {
                    this.panel.onDraw(canvas);

                    try
                    {
                        Thread.sleep(1);
                    }
                    catch (InterruptedException iex) { }
                }
            }
            finally
            {
                if (canvas != null)
                {
                    holder.unlockCanvasAndPost(canvas);
                }
            }
        }
        Log.d(TAG,"Game loop exceeded limits, tick count " + count + "\n");
    }
}