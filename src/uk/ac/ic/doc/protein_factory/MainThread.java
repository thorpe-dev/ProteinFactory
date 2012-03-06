package uk.ac.ic.doc.protein_factory;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.util.Log;
import android.os.SystemClock;


/**
 * Created by IntelliJ IDEA.
 * User: Michael
 * Date: 02/03/12
 * Time: 17:05
 * To change this template use File | Settings | File Templates.
 */
public class MainThread extends UIThread {


    private static final String TAG = MainThread.class.getSimpleName();

    public MainThread(SurfaceHolder h, MainGamePanel p) {
        super(h,p);
        this.LOOPTIME = 20;
    }

    @Override
    protected void method(Canvas canvas)
    {
        this.panel.onDraw(canvas);
    }
}