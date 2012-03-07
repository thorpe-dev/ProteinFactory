package uk.ac.ic.doc.protein_factory;

import android.graphics.Canvas;
import android.view.SurfaceHolder;


/**
 * Created by IntelliJ IDEA.
 * User: Michael
 * Date: 02/03/12
 * Time: 17:05
 * To change this template use File | Settings | File Templates.
 */
public class MainThread extends UIThread {


    public MainThread(SurfaceHolder h, MainGamePanel p) {
        super(h,p);
        this.LOOPTIME = 20;
        this.TAG = MainThread.class.getSimpleName();
    }

    @Override
    protected void method(Canvas canvas)
    {
        this.panel.onDraw(canvas);
    }
}