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
public class MainThread extends Thread {
	private static final int LOOPTIME = 20; // Nominal (minimum) loop time in ms
	private static final String TAG = MainThread.class.getSimpleName();
    protected boolean running;
    protected SurfaceHolder holder;
    protected MainGamePanel panel;
    private Game game;

    public MainThread(SurfaceHolder h, MainGamePanel p, Game g) {
        this.holder = h;
        this.panel = p;
        this.game = g;
    }
    
    public void setRunning(boolean running) { this.running = running; }

    @Override
    public void run() {
        Canvas canvas;
        while (running) {
            long startTime = SystemClock.uptimeMillis();

            // Update game state and render this state to the screen
            canvas = null;
            try {
                canvas = holder.lockCanvas();
                if(canvas != null) {
	                synchronized (holder) {
	                    this.game.physics();
	                    this.game.drawToCanvas(canvas);
	                    long thisLoopDuration = SystemClock.uptimeMillis()-startTime;
	                    Log.d(TAG,"Loop took " + thisLoopDuration + "ms");
	                    long timeToSleep = LOOPTIME-thisLoopDuration;
	                    if(timeToSleep > 0) {
	                    	try { Thread.sleep(timeToSleep); }
	                    	catch (InterruptedException iex) {/* Do we need to do anything here? */ }
	                    }
	                }
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