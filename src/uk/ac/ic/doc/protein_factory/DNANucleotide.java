package uk.ac.ic.doc.protein_factory;

import java.lang.reflect.Field;
import java.util.Random;

import android.graphics.BitmapFactory;
import android.util.Log;

public class DNANucleotide extends Nucleotide {

    private static final String TAG = RNANucleotide.class.getSimpleName();

    private boolean snapped = false;

	public DNANucleotide(Game g, char c, int i) {
        super(g);
        this.type = c;
        this.bitmap = BitmapFactory.decodeResource(g.getResources(), generateNucleotide(R.drawable.class, this.type, randomColor(g.getGen())));
        this.y = this.bitmap.getHeight() / 2;
        this.x = this.bitmap.getWidth() * i + this.bitmap.getWidth() / 2;
    }

	
    public void wobbleLeft() {
    	x--;
    }
    
    public boolean snapped() { return this.snapped; }


// --Commented out by Inspection START (08/03/12 16:34):
//    protected char randomType(Random gen)
//    {
//        switch (gen.nextInt(4))
//        {
//            case 0:
//                return 'a';
//            case 1:
//                return 'c';
//            case 2:
//                return 'g';
//            default:
//                return 't';
//        }
//    }
// --Commented out by Inspection STOP (08/03/12 16:34)
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

    int generateNucleotide(Class<?> c, char type, String color)
    {
        try
        {
            final Field field = c.getDeclaredField(type + "_backbone_" + color);
            return field.getInt(null);
        }
        catch (Exception e) 
        { 
            Log.e(TAG,"Exception message is: " + e.getMessage());
            return R.drawable.a_backbone_green;

        }
    }
}
