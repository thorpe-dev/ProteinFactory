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
public class DNA {

    private Bitmap bitmap; // The actual bitmap image
    private int x;
    private int y; // X and Y Co-ordinates of the image
    private boolean touched; // if the dna has been touched

    public boolean isInHeader() {
        return inHeader;
    }

    public void setInHeader(boolean inHeader) {
        this.inHeader = inHeader;
    }

    private boolean inHeader;

    public DNA (Bitmap b, int x, int y)
    {
        this.bitmap = b;
        this.x = x;
        this.y = y;
        this.inHeader = false;
        this.touched = false;
    }

    public Bitmap getBitmap() { return bitmap; }

    public void setBitmap(Bitmap b) { this.bitmap = b; }

    public int getX() { return x; }

    public void setX(int x) { this.x = x;}

    public int getY() { return y; }

    public void setY(int y) {this.y = y;}

    public boolean isTouched() { return this.touched;}

    public void setTouched(boolean touched) {this.touched = touched;}

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
    }

    public void actionDown(int eventX, int eventY)
    {
        if ((eventX >= (x - bitmap.getWidth() / 2 )) && (eventX <= (x + bitmap.getHeight() / 2)) &&
                ((eventY >= (y - bitmap.getWidth() / 2 )) && (eventY <= (y + bitmap.getHeight() / 2))))
        {
            setTouched(true);
        }
        else
        {
            setTouched(false);
        }
    }
}