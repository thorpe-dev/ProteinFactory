package uk.ac.ic.doc.protein_factory;

import android.graphics.Bitmap;
import android.graphics.Canvas;


public abstract class Nucleotide {

    protected Bitmap bitmap; // The actual bitmap image
    protected int x;
    protected int y; // X and Y Co-ordinates of the image
    protected Game game;

//    public Nucleotide (Context c, Random gen) {
//        this.bitmap = BitmapFactory.decodeResource(c.getResources(), R.drawable.helix);
//        this.x = gen.nextInt(350) + 50;
//        this.y = gen.nextInt(200) + 100;
//    }

    // Default constructor
    public Nucleotide(Game g) { this.game = g;}

    abstract public void wobbleLeft();
    
	public int getX() { return x; }
	public int getY() { return y; }
	
	public int sqDist(int x, int y) {
		int distX = this.x - x;
        int distY = this.y - y;
        return ((distX * distX) + (distY * distY));
	}

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
    }
}