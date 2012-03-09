package uk.ac.ic.doc.protein_factory;

import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;


class MainThread extends Thread {
	private static final int LOOPTIME = 20; // Nominal (minimum) loop time in ms
	private static final String TAG = MainThread.class.getSimpleName();
    private boolean running;
    private final SurfaceHolder holder;
    private final Game game;

    public MainThread(SurfaceHolder h, Game g) {
        this.holder = h;
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
	                	if(this.game.gameOver()) {
	                		game.drawScores(canvas);
	                		this.running = false;
	                	}
	                	else {
		                    this.game.physics();
		                    this.game.drawToCanvas(canvas);
		                    long thisLoopDuration = SystemClock.uptimeMillis()-startTime;
		                    Log.v(TAG,"Loop took " + thisLoopDuration + "ms");
		                    long timeToSleep = LOOPTIME-thisLoopDuration;
		                    if(timeToSleep > 0) {
		                    	try { Thread.sleep(timeToSleep); }
		                    	catch (InterruptedException iex) {/* Do we need to do anything here? */ }
		                    }
	                	}
	                }
                }
            }
            finally {
                if (canvas != null)
                    holder.unlockCanvasAndPost(canvas);
            }
        }
        Log.d(TAG,"Game over");
    }
}