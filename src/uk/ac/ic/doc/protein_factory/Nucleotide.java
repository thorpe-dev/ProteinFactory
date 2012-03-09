package uk.ac.ic.doc.protein_factory;

import java.lang.reflect.Field;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;


abstract class Nucleotide {
	private static final String TAG = Nucleotide.class.getSimpleName();

    Bitmap bitmap; // The actual bitmap image
    int x;
    int y; // X and Y Co-ordinates of the image
    final Game game;
    final char type;
    protected boolean attached = false;
    
    Nucleotide(Game g, char type) {
    	this.game = g;
    	this.type = Character.toUpperCase(type);
    }

    public abstract void wobbleLeft();
    protected abstract String partial_bitmap_filename();
    
	public int getX() { return x; }
    public void setX(int x) {this.x = x;}
	public int getY() { return y; }
	public char type() { return type; }
	public boolean attached() { return attached; }
	
    public int getWidth() {
        return bitmap.getWidth();
    }
	
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
    
    protected void setColour(String colour) {
    	this.bitmap = BitmapFactory.decodeResource(game.getResources(),generateNucleotide(colour));
    }
    
    protected int generateNucleotide(String color)
    {
        try
        {
            final Field field = R.drawable.class.getDeclaredField(Character.toLowerCase(this.type) + this.partial_bitmap_filename() + color);
            return field.getInt(null);
        }
        catch (Exception e) 
        { 
            Log.e(TAG,"Exception message is: " + e.getMessage());
            return R.drawable.dna_helix_icon;
        }
    }
    
    // Has this Nucleotide gone off-screen to the left?
    public boolean offScreen() {
    	return ((x + this.getWidth()/2) < 0);
    }
}