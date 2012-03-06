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

    // Some flags to hold game state

    private static final String TAG = ShiftThread.class.getSimpleName();

    public void setRunning(boolean running) {this.running = running;}

    public ShiftThread(SurfaceHolder h, MainGamePanel p) {
        super(h,p);
        this.LOOPTIME = 100;
    }


    @Override
    protected void method(Canvas canvas)
    {
        this.panel.onShift(canvas);
    }
}