package uk.ac.ic.doc.protein_factory;

import java.lang.reflect.Field;
import java.util.Random;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;

public class DNANucleotide extends Nucleotide {

    private static final String TAG = RNANucleotide.class.getSimpleName();
    private int default_ = R.drawable.a_backbone_green;
    private boolean snapped = false;

	public DNANucleotide(Context c, int i, Game g) {
		super(g);
        this.bitmap = BitmapFactory.decodeResource(c.getResources(), generateNucleotide(R.drawable.class, randomType(g.gen), randomColor(g.gen)));
        this.y = this.bitmap.getHeight() / 2;
        this.x = this.bitmap.getWidth() * i + this.bitmap.getWidth() / 2;
    }
	
    public void wobbleLeft() {
    	x--;
    }
    
    public boolean snapped() { return this.snapped; }

    protected char randomType(Random gen)
    {
        switch (gen.nextInt(4))
        {
            case 0:
            	this.type = 'A';
                return 'a';
            case 1:
            	this.type = 'C';
                return 'c';
            case 2:
            	this.type = 'G';
                return 'g';
            default:
            	this.type = 'T';
                return 't';
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
            final Field field = c.getDeclaredField(type + "_backbone_" + color);
            return field.getInt(null);
        }
        catch (Exception e) 
        { 
            Log.e(TAG,"Exception message is: " + e.getMessage()); 
            return default_;
        }
    }
}
