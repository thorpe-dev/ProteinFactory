package uk.ac.ic.doc.protein_factory;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by IntelliJ IDEA.
 * User: Michael
 * Date: 04/03/12
 * Time: 18:55
 * To change this template use File | Settings | File Templates.
 */
public class Header {

    private Bitmap bitmap; // The actual bitmap image
    private int x;
    private int y; // X and Y Co-ordinates of the image

    public Header(Bitmap b, int x, int y)
    {
        this.bitmap = b;
        this.x = x;
        this.y = y;
    }

    public Bitmap getBitmap() { return bitmap; }

    public void setBitmap(Bitmap b) { this.bitmap = b; }

    public int getX() { return x; }

    public void setX(int x) { this.x = x;}

    public int getY() { return y; }

    public void setY(int y) {this.y = y;}

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(bitmap, 0, 0, null);
    }
}