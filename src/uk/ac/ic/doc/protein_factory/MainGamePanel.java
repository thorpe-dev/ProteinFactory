package uk.ac.ic.doc.protein_factory;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.util.Log;

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback
{
    private static final String TAG = MainGamePanel.class.getSimpleName();
	
    private MainThread mainThread;
    private Game game;
    
    public MainGamePanel(Context c)
    {
        super(c);
        
        game = new Game(c, this);
        mainThread = new MainThread(getHolder(),this, game);

        getHolder().addCallback(this);
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder h, int format, int width, int height)
    {

    }

    @Override
    public void surfaceCreated(SurfaceHolder h)
    {
        mainThread.setRunning(true);
        mainThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder h)
    {
        Log.d(TAG,"Surface being destroyed");
        
        mainThread.setRunning(false);
        ((Activity)getContext()).finish();

        // Not Reached?
        boolean retry = true;
        while (retry)
        {
            try
            {
                mainThread.join();
                retry = false;
            }
            catch (InterruptedException e)
            {
                // try shutting the thread down again
            }
        }
        Log.d(TAG,"Thread has been shut down cleanly");
    }

    @Override
    public boolean onTouchEvent(MotionEvent e)
    {
    	game.touch(e);
        return true;
    }
}