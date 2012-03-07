package uk.ac.ic.doc.protein_factory;

import android.graphics.Bitmap;
import android.graphics.Canvas;


public abstract class Nucleotide {

    protected Bitmap bitmap; // The actual bitmap image
    protected int x;
    protected int y; // X and Y Co-ordinates of the image

//    public Nucleotide (Context c, Random gen) {
//        this.bitmap = BitmapFactory.decodeResource(c.getResources(), R.drawable.helix);
//        this.x = gen.nextInt(350) + 50;
//        this.y = gen.nextInt(200) + 100;
//    }

    // Default constructor
    public Nucleotide() {}
    

    public Bitmap getBitmap() { return bitmap; }

    public void setBitmap(Bitmap b) { this.bitmap = b; }

    public int getX() { return x; }

    public void setX(int x) { this.x = x;}

    public int getY() { return y; }

    public void setY(int y) {this.y = y;}

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
    }
}