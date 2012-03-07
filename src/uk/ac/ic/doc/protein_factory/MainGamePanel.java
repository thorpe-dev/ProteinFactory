package uk.ac.ic.doc.protein_factory;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.util.Log;

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback
{
    private MainThread mainThread;
    private Game game;

    private static final String TAG = MainGamePanel.class.getSimpleName();

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
        if (e.getAction() == MotionEvent.ACTION_DOWN)
        {
        	getClosest(e).actionDown((int)e.getX(),(int)e.getY());
            Log.d(TAG,"Coords: x=" + e.getX() + ",y=" + e.getY());
        }
        if (e.getAction() == MotionEvent.ACTION_MOVE)
        	getClosest(e).move((int)e.getX(),(int)e.getY());
        if (e.getAction() == MotionEvent.ACTION_UP)
        {
            for (RNANucleotide rna : game.getFloatingRNA())
                rna.setTouched(false);
        }
        return true;
    }

    protected RNANucleotide getClosest (MotionEvent e)
    {
        RNANucleotide closest_rna = game.getFloatingRNA().get(0);
        int closest_dist = closest_rna.sqDist((int)e.getX(), (int)e.getY());
        int this_dist;
        for (RNANucleotide rna : game.getFloatingRNA())
        {
            this_dist = rna.sqDist((int)e.getX(), (int)e.getY());
            if (this_dist < closest_dist)
            {
                closest_dist = this_dist;
                closest_rna = rna;
            }
        }

        return closest_rna;
    }


}