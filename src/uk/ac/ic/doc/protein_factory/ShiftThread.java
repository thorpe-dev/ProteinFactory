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
public class ShiftThread extends UIThread {

    public ShiftThread(SurfaceHolder h, MainGamePanel p) {
        super(h,p);
        this.LOOPTIME = 100;
        this.TAG = ShiftThread.class.getSimpleName();
    }


    @Override
    protected void method(Canvas canvas)
    {
        this.panel.onShift(canvas);
    }

    @Override
    protected void sleepMethod (long startTime) throws InterruptedException
    {
        Log.d(TAG,"Loop took " + (SystemClock.uptimeMillis()-startTime) + "ms, was supposed to take " + LOOPTIME);
        long timeToSleep = this.LOOPTIME-(SystemClock.uptimeMillis()-startTime);
        if(timeToSleep > 0)
            Thread.sleep(timeToSleep);
    }
}