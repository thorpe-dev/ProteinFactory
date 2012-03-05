package uk.ac.ic.doc.protein_factory;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.util.Log;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: Michael
 * Date: 02/03/12
 * Time: 16:15
 * To change this template use File | Settings | File Templates.
 */
public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback
{
    private MainThread thread;

    private DNA[] dnaArray;
    private Header header;
    


    private static final String TAG = MainGamePanel.class.getSimpleName();
    public MainGamePanel(Context c)
    {
        super(c);

        Random gen = new Random();

        dnaArray = new DNA[15];

        for (int i = 0; i < dnaArray.length; i++)
        {
            dnaArray[i] = new DNA(BitmapFactory.decodeResource(getResources(), uk.ac.ic.doc.protein_factory.R.drawable.helix),gen.nextInt(200) + 50,gen.nextInt(350) + 100);
        }
        header = new Header(BitmapFactory.decodeResource(getResources(), uk.ac.ic.doc.protein_factory.R.drawable.header ),0,0);

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
            for (int i = 0; i < dnaArray.length; i++)
            {
                dnaArray[i].actionDown((int)e.getX(),(int)e.getY());
            }

            if (e.getY() > getHeight() - 50)
            {
                thread.setRunning(false);
                ((Activity)getContext()).finish();
            }
            else
            {
                Log.d(TAG,"Coords: x=" + e.getX() + ",y=" + e.getY());
            }
        }
        if (e.getAction() == MotionEvent.ACTION_MOVE)
        {
            Random generator = new Random();
            for (int i = 0; i < dnaArray.length; i++)
            {
                if (dnaArray[i].isTouched())
                {
                    dnaArray[i].setX((int)e.getX() + generator.nextInt(3) - 1);
                    dnaArray[i].setY((int)e.getY() + generator.nextInt(3) - 1);
                }
            }
        }
        if (e.getAction() == MotionEvent.ACTION_UP)
        {
            for (int i = 0; i < dnaArray.length; i++)
            {
                if (dnaArray[i].isTouched())
                {
                    dnaArray[i].setTouched(false);
                }
            }
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        Random generator = new Random();
        canvas.drawColor(Color.BLACK);
        header.draw(canvas);
        for (int i = 0; i < dnaArray.length; i++)
        {
            if (!dnaArray[i].isTouched())
            {
                dnaArray[i].setX(dnaArray[i].getX() + generator.nextInt(3) - 1);
                dnaArray[i].setY(dnaArray[i].getY() + generator.nextInt(3) - 1);

            }
            dnaArray[i].draw(canvas);
        }
    }
}