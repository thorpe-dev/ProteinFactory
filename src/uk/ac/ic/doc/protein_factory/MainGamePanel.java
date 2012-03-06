package uk.ac.ic.doc.protein_factory;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.util.Log;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback
{
    private MainThread thread;
    private List<RNANucleotide> rnaNucleotides = new LinkedList<RNANucleotide>(); 
    private static final String TAG = MainGamePanel.class.getSimpleName();
    private static final int rnaCount = 20;
    
    public MainGamePanel(Context c)
    {
        super(c);

        Random gen = new Random();

        for (int i = 0; i < rnaCount; i++)
            rnaNucleotides.add(new RNANucleotide(c, gen));

        getHolder().addCallback(this);
        thread = new MainThread(getHolder(),this);
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder h, int format, int width, int height)
    {

    }

    @Override
    public void surfaceCreated(SurfaceHolder h)
    {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder h)
    {
        Log.d(TAG,"Surface being destroyed");

        thread.setRunning(false);
        ((Activity)getContext()).finish();
        
        // Not Reached?
        boolean retry = true;
        while (retry)
        {
            try
            {
                thread.join();
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
            for (RNANucleotide rna : rnaNucleotides)
                rna.actionDown((int)e.getX(),(int)e.getY());

                Log.d(TAG,"Coords: x=" + e.getX() + ",y=" + e.getY());
        }
        if (e.getAction() == MotionEvent.ACTION_MOVE)
        {
            Random generator = new Random();
            for (RNANucleotide rna : rnaNucleotides)
            {
                if (rna.isTouched())
                {
                    rna.setX((int)e.getX() + generator.nextInt(3) - 1);
                    rna.setY((int)e.getY() + generator.nextInt(3) - 1);
                }
            }
        }
        if (e.getAction() == MotionEvent.ACTION_UP)
        {
            for (RNANucleotide rna : rnaNucleotides)
            	rna.setTouched(false);
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        Random generator = new Random();
        canvas.drawColor(Color.GREEN);
        for (RNANucleotide rna : rnaNucleotides)
        {
/*            if (!rna.isInHeader())
            {
                if (rna.getX() < (header.getX() - header.getBitmap().getWidth())
                        || rna.getY() < (header.getY() - header.getBitmap().getHeight()))
                {
                    rna.setInHeader(true);
                }
                else */if (!rna.isTouched())
                {
                    rna.setX(rna.getX() + generator.nextInt(3) - 1);
                    rna.setY(rna.getY() + generator.nextInt(3) - 1);
                    rna.draw(canvas);
                }
                else
                {
                    rna.draw(canvas);
                }
//            }
        }
    }
}