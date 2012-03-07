package uk.ac.ic.doc.protein_factory;

import java.lang.reflect.Field;
import java.util.Random;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;

public class RNANucleotide extends Nucleotide {
    private boolean touched; // if the dna has been touched
    private static final String TAG = RNANucleotide.class.getSimpleName();
    private int default_ = R.drawable.a_green;
    
	public RNANucleotide(Context c, Random gen) {
        this.x = gen.nextInt(350) + 50;
        this.y = gen.nextInt(200) + 100;
        try {
        	this.bitmap = BitmapFactory.decodeResource(c.getResources(), generateNucleotide(R.drawable.class, randomType(gen), randomColor(gen)));
        }
        catch(Exception e) {
        	Log.e(TAG,"Exception message is: " + e.getMessage());
        }
	}
	
	public void wobble(Random gen) {
        x += gen.nextInt(3) - 2; // Bias to move left
        y += gen.nextInt(3) - 1;
	}

	public void move(int x, int y) {
		this.x  = x;
		this.y = y;
	}
	
	public int sqDist(int x, int y) {
		int distX = this.x - x;
        int distY = this.y - y;
        return ((distX * distX) + (distY * distY));
	}
	
    protected char randomType(Random gen)
    {
        switch (gen.nextInt(4))
        {
            case 0:
                return 'a';
            case 1:
                return 'c';
            case 2:
                return 'g';
            default:
                return 'u';
        }
    }
    protected String randomColor(Random gen)
    {
        switch (gen.nextInt(4))
        {
            case 0:
                return "green";
            case 1:
                return "grey";
            case 2:
                return "orange";
            default:
                return "red";
        }

    }

    protected int generateNucleotide(Class<?> c, char type, String color)
    {
        try
        {
            final Field field = c.getDeclaredField(type + "_" + color);
            return field.getInt(null);
        }
        catch (Exception e) 
        { 
            Log.d(TAG,"Exception message is: " + e.getMessage());
            return default_;
        }
    }

    public boolean isTouched() { return this.touched;}

    public void setTouched(boolean touched) {this.touched = touched;}

    public void actionDown(int eventX, int eventY) {
        if ((eventX >= (x - bitmap.getWidth() / 2 )) && (eventX <= (x + bitmap.getHeight() / 2)) &&
                ((eventY >= (y - bitmap.getWidth() / 2 )) && (eventY <= (y + bitmap.getHeight() / 2))))
            setTouched(true);
        else
            setTouched(false);
    }
}
