package uk.ac.ic.doc.protein_factory;

import java.lang.reflect.Field;
import java.util.Random;

import android.graphics.BitmapFactory;
import android.util.Log;

public class RNANucleotide extends Nucleotide {
    private static final String TAG = RNANucleotide.class.getSimpleName();

    private boolean touched = false;
    private DNANucleotide snappedTo = null;
    
	public RNANucleotide(Game g) {
		super(g);
        this.x = g.displayWidth() - g.getGen().nextInt(2 * g.displayWidth()/3);
        this.y = g.getGen().nextInt(200) + 120;
        this.type = randomType(g.getGen());
        this.bitmap = BitmapFactory.decodeResource(g.getResources(), generateNucleotide(R.drawable.class, this.type, randomColor(g.getGen())));
	}
    
    public RNANucleotide(Game g, char type)
    {
        super(g);
        this.x = g.displayWidth() - g.getGen().nextInt(2 * g.displayWidth()/3);
        this.y = g.getGen().nextInt(200) + 120;
        this.type = type;
        this.bitmap = BitmapFactory.decodeResource(g.getResources(),generateNucleotide(R.drawable.class,type, randomColor(g.getGen())));
    }
	
	public void wobbleLeft() {
		if(snappedTo != null) {
			x--;
		}
		else {
			x += game.gen.nextInt(3) - 2; // Bias to move left
			y += game.gen.nextInt(3) - 1;
		}
	}

	public boolean touched() { return touched; }
	public void setTouched(boolean touched) { this.touched = touched; }
	
	public void snap(DNANucleotide dna)
    {
		this.snappedTo = dna;
		// Set correct bitmap
		// Set DNAs correct bitmap
		// Set DNA as snappedTo
		// Update score
	}
	
    char randomType(Random gen)
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
            Log.e(TAG,"Exception message is: " + e.getMessage());
            return R.drawable.a_green;

        }
    }
}
