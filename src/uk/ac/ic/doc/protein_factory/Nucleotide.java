package uk.ac.ic.doc.protein_factory;

import android.graphics.Bitmap;
import android.graphics.Canvas;


abstract class Nucleotide {

    public int getWidth() {
        return bitmap.getWidth();
    }

    Bitmap bitmap; // The actual bitmap image
    int x;
    int y; // X and Y Co-ordinates of the image
    final Game game;
    char type;
    
    Nucleotide(Game g) { this.game = g;}

    public abstract void wobbleLeft();
    
	public int getX() { return x; }
    public void setX(int x) {this.x = x;}
	public int getY() { return y; }
	public char type() { return type; }
	public boolean type(char t) { return (t == type); }
	
	//public abstract void setBitmap(Game.State state);
	
	public int sqDist(int x, int y) {
		int distX = this.x - x;
        int distY = this.y - y;
        return ((distX * distX) + (distY * distY));
	}

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
    }
}