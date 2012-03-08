package uk.ac.ic.doc.protein_factory;

import java.lang.reflect.Field;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;


abstract class Nucleotide {

    public int getWidth() {
        return bitmap.getWidth();
    }

    Bitmap bitmap; // The actual bitmap image
    int x;
    int y; // X and Y Co-ordinates of the image
    final Game game;
    char type;
    final String PARTIAL_BITMAP_FILENAME;
    final String TAG;
    
    Nucleotide(Game g, String pbf, String tag) {
    	this.game = g;
    	this.PARTIAL_BITMAP_FILENAME = pbf;
    	this.TAG = tag;
    }

    public abstract void wobbleLeft();
    
	public int getX() { return x; }
    public void setX(int x) {this.x = x;}
	public int getY() { return y; }
	public char type() { return type; }
	public boolean type(char t) { return (t == type); }
	
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
    
    protected void setBitmap(String colour) {
    	this.bitmap = BitmapFactory.decodeResource(game.getResources(),generateNucleotide(colour));
    }
    
    protected int generateNucleotide(String color)
    {
        try
        {
            final Field field = R.drawable.class.getDeclaredField(this.type + this.PARTIAL_BITMAP_FILENAME + color);
            return field.getInt(null);
        }
        catch (Exception e) 
        { 
            Log.e(this.TAG,"Exception message is: " + e.getMessage());
            return R.drawable.dna_helix_icon;
        }
    }
}