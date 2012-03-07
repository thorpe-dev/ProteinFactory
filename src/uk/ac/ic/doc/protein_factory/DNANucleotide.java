package uk.ac.ic.doc.protein_factory;

import java.lang.reflect.Field;
import java.util.Random;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;

public class DNANucleotide extends Nucleotide {

    private static final String TAG = RNANucleotide.class.getSimpleName();
    private int default_ = R.drawable.a_backbone_green;

	public DNANucleotide(Context c, Random gen,int i) {
        this.bitmap = BitmapFactory.decodeResource(c.getResources(), generateNucleotide(R.drawable.class, randomType(gen), randomColor(gen)));
        this.y = this.bitmap.getHeight() / 2;
        this.x = this.bitmap.getWidth() * i + this.bitmap.getWidth() / 2;
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
